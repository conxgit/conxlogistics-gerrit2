package org.jbpm.task.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.drools.SystemEventListener;
import org.jbpm.eventmessaging.EventKeys;
import org.jbpm.task.AccessType;
import org.jbpm.task.AllowedToDelegate;
import org.jbpm.task.Attachment;
import org.jbpm.task.BooleanExpression;
import org.jbpm.task.Comment;
import org.jbpm.task.Content;
import org.jbpm.task.Deadline;
import org.jbpm.task.Deadlines;
import org.jbpm.task.Delegation;
import org.jbpm.task.EmailNotification;
import org.jbpm.task.EmailNotificationHeader;
import org.jbpm.task.Escalation;
import org.jbpm.task.Group;
import org.jbpm.task.I18NText;
import org.jbpm.task.Notification;
import org.jbpm.task.NotificationType;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.PeopleAssignments;
import org.jbpm.task.Reassignment;
import org.jbpm.task.Status;
import org.jbpm.task.StatusChange;
import org.jbpm.task.Task;
import org.jbpm.task.TaskData;
import org.jbpm.task.User;
import org.jbpm.task.UserInfo;
import org.jbpm.task.WorkItemNotification;
import org.jbpm.task.admin.TasksAdmin;
import org.jbpm.task.event.MessagingTaskEventListener;
import org.jbpm.task.event.TaskEventListener;
import org.jbpm.task.event.TaskEventSupport;
import org.jbpm.task.query.DeadlineSummary;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.Allowed;
import org.jbpm.task.service.Command;
import org.jbpm.task.service.CommandName;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.DefaultEscalatedDeadlineHandler;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.task.service.Operation;
import org.jbpm.task.service.OperationCommand;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.persistence.JTATaskSessionFactoryImpl;
import org.jbpm.task.service.persistence.TaskSessionFactory;
import org.mvel2.MVEL;
import org.mvel2.ParserConfiguration;
import org.mvel2.ParserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;

public class JTACustomTaskService extends TaskService {

    private TaskSessionFactory sessionFactory;
    
    private ScheduledThreadPoolExecutor scheduler;
    private EscalatedDeadlineHandler escalatedDeadlineHandler;

    private UserInfo userInfo;

    private TaskEventSupport eventSupport;
    private EventKeys eventKeys;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    /**
     * Listener used for logging
     */
    private SystemEventListener systemEventListener;

    private Map<Operation, List<OperationCommand>> operations;

    /**
     * Constructor in which no EscalatedDeadlineHandler is given. 
     * @param userTransaction 
     * 
     * @param emf the EntityManagerFactory
     * @param systemEventListener the Drools SystemEventListener
     */
    public JTACustomTaskService(
    		TransactionManager tm,
    		JndiTemplate jndiTemplate,
    		UserTransaction userTransaction, EntityManagerFactory emf, SystemEventListener systemEventListener) {
        initialize(jndiTemplate,userTransaction,tm,emf, systemEventListener, null);
    }

    /**
     * Default constructor
     * @param emf
     * @param systemEventListener
     * @param escalationHandler
     */
    public JTACustomTaskService(EntityManagerFactory emf, SystemEventListener systemEventListener, EscalatedDeadlineHandler escalationHandler) {
    }
    
    private void initialize(JndiTemplate jndiTemplate,UserTransaction userTransaction, TransactionManager tm, EntityManagerFactory emf, SystemEventListener systemEventListener, EscalatedDeadlineHandler escalationHandler) {
        this.sessionFactory = new JTATaskSessionFactoryImpl(tm,jndiTemplate,userTransaction,this,emf);
        this.systemEventListener = systemEventListener;
        if (escalationHandler != null) {
            this.escalatedDeadlineHandler = escalationHandler;
        }
        else { 
            this.escalatedDeadlineHandler = new DefaultEscalatedDeadlineHandler();
        }
        initialize();
    }
    
    /**
     * The method in which everything is initialized. 
     * </p>
     * The constructor has been split into two methods in order to use Spring with human-task.
     */
    @Override
	public void initialize() { 
        eventSupport = new TaskEventSupport();
        eventKeys = new EventKeys();
        eventSupport.addEventListener(new MessagingTaskEventListener(eventKeys));
        scheduler = new ScheduledThreadPoolExecutor(3);

        TaskServiceSession session = createSession();
        session.scheduleUnescalatedDeadlines();
        session.dispose();

        Map<String, Object> vars = new HashMap<String, Object>();

        // Search operations-dsl.mvel, if necessary using superclass if TaskService is subclassed
        InputStream is = null;
        for (Class<?> c = getClass(); c != null; c = c.getSuperclass()) {
            is = c.getResourceAsStream("operations-dsl.mvel");
            if (is != null) {
                break;
            }
        }
        if (is == null) {
        	throw new RuntimeException("Unable To initialise TaskService, could not find Operations DSL");
        }
        Reader reader = new InputStreamReader(is);
        try {
            operations = (Map<Operation, List<OperationCommand>>) eval(toString(reader),  vars);
        } catch (IOException e) {
            throw new RuntimeException("Unable To initialise TaskService, could not load Operations DSL");
        }
    }
    
    /**
     * Default constructor needed for Spring
     */
    public JTACustomTaskService() { 
        super();
    }
    
    /**
     * Setter of the {@link SystemEventListener} field for Spring.
     * @param emf an {@link SystemEventListener} instance
     */
    @Override
	public void setSystemEventListener(SystemEventListener systemEventListener) { 
        this.systemEventListener = systemEventListener;
    }

    /**
     * Setter of the {@link TaskSessionFactory} field for Spring.
     * @param emf an {@link TaskSessionFactory} instance
     */
    @Override
	public void setTaskSessionFactory(TaskSessionFactory taskSessionFactory) { 
        this.sessionFactory = taskSessionFactory;
    }

    @Override
	public TaskServiceSession createSession() {
        return sessionFactory.createTaskServiceSession();
    }

    @Override
	public TasksAdmin createTaskAdmin() {
        return sessionFactory.createTaskAdmin();
    }

    public void schedule(ScheduledTaskDeadline deadline,
                         long delay) {
        scheduler.schedule(deadline,
                delay,
                TimeUnit.MILLISECONDS);
    }

    @Override
	public Map<Operation, List<OperationCommand>> getOperations() {
        return operations;
    }

    @Override
	public List<OperationCommand> getCommandsForOperation(Operation operation) {
        return operations.get(operation);
    }

    @Override
	public EventKeys getEventKeys() {
        return eventKeys;
    }

    @Override
	public void addEventListener(final TaskEventListener listener) {
        this.eventSupport.addEventListener(listener);
    }

    @Override
	public void removeEventListener(final TaskEventListener listener) {
        this.eventSupport.removeEventListener(listener);
    }

    @Override
	public List<TaskEventListener> getWorkingMemoryEventListeners() {
        return this.eventSupport.getEventListeners();
    }

    @Override
	public TaskEventSupport getEventSupport() {
        return eventSupport;
    }

    @Override
	public UserInfo getUserinfo() {
        return userInfo;
    }

    @Override
	public void setUserinfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
	public void setEscalatedDeadlineHandler(EscalatedDeadlineHandler escalatedDeadlineHandler) {
        this.escalatedDeadlineHandler = escalatedDeadlineHandler;
    }

    @Override
	public void executeEscalatedDeadline(final long taskId, final long deadlineId) {
        TaskServiceSession session = createSession();
        
        session.executeEscalatedDeadline(escalatedDeadlineHandler, this, taskId, deadlineId);

        session.dispose();
    }

    @Override
	public void addUsersAndGroups(Map<String, User> users, Map<String, Group> groups) {
        TaskServiceSession taskSession = createSession();
        for (User user : users.values()) {
            taskSession.addUser(user);
        }

        for (Group group : groups.values()) {
            taskSession.addGroup(group);
        }
        taskSession.dispose();
    }

    public static String toString(Reader reader) throws IOException {
        int charValue  ;
        StringBuffer sb = new StringBuffer(1024);
        while ((charValue = reader.read()) != -1) {
            sb.append((char) charValue);
        }
        return sb.toString();
    }

    private static Map<String, Class<?>> inputs = new HashMap<String, Class<?>>();

    public static Map<String, Class<?>> getInputs() {
        synchronized (inputs) {
            if (inputs.isEmpty()) {
                // org.jbpm.task
                inputs.put("AccessType", AccessType.class);
                inputs.put("AllowedToDelegate", AllowedToDelegate.class);
                inputs.put("Attachment", Attachment.class);
                inputs.put("BooleanExpression", BooleanExpression.class);
                inputs.put("Comment", Comment.class);
                inputs.put("Content", Content.class);
                inputs.put("Deadline", Deadline.class);
                inputs.put("Deadlines", Deadlines.class);
                inputs.put("Delegation", Delegation.class);
                inputs.put("EmailNotification", EmailNotification.class);
                inputs.put("EmailNotificationHeader", EmailNotificationHeader.class);
                inputs.put("Escalation", Escalation.class);
                inputs.put("Group", Group.class);
                inputs.put("I18NText", I18NText.class);
                inputs.put("Notification", Notification.class);
                inputs.put("NotificationType", NotificationType.class);
                inputs.put("OrganizationalEntity", OrganizationalEntity.class);
                inputs.put("PeopleAssignments", PeopleAssignments.class);
                inputs.put("Reassignment", Reassignment.class);
                inputs.put("Status", Status.class);
                inputs.put("StatusChange", StatusChange.class);
                inputs.put("Task", Task.class);
                inputs.put("TaskData", TaskData.class);
                inputs.put("User", User.class);
                inputs.put("UserInfo", UserInfo.class);
                inputs.put("WorkItemNotification", WorkItemNotification.class);

                // org.jbpm.task.service
                inputs.put("Allowed", Allowed.class);
                inputs.put("Command", Command.class);
                inputs.put("CommandName", CommandName.class);
                inputs.put("ContentData", ContentData.class);
                inputs.put("Operation", Operation.class);
                inputs.put("Operation.Claim", Operation.class);
                inputs.put("OperationCommand", OperationCommand.class);

                // org.drools.task.query
                inputs.put("DeadlineSummary", DeadlineSummary.class);
                inputs.put("TaskSummary", TaskSummary.class);
            }
            return inputs;
        }
    }

    public static Object eval(Reader reader) { 
        try {
            return eval(toString(reader), null);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown",e);
        }
    }
    
    public static Object eval(Reader reader, Map<String, Object> vars) {
        try {
            return eval(toString(reader), vars);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown",e);
        }
    }
    
    public static Object eval(String str, Map<String, Object> vars) {
    	ParserConfiguration pconf = new ParserConfiguration();
    	pconf.addPackageImport("org.jbpm.task");
    	pconf.addPackageImport("org.jbpm.task.service");
    	pconf.addPackageImport("org.jbpm.task.query");
    	pconf.addPackageImport("java.util");
    	
    	for(String entry : getInputs().keySet()){
    		pconf.addImport(entry, getInputs().get(entry));
        }
    	ParserContext context = new ParserContext(pconf);
        Serializable s = MVEL.compileExpression(str.trim(), context);

        if( vars != null ) { 
        return MVEL.executeExpression(s, vars);
    }
        else { 
            return MVEL.executeExpression(s);
        }
    }

    public static class ScheduledTaskDeadline
            implements
            Callable {
        private long taskId;
        private long deadlineId;
        private TaskService service;

        public ScheduledTaskDeadline(long taskId,
                                     long deadlineId,
                                     TaskService service) {
            this.taskId = taskId;
            this.deadlineId = deadlineId;
            this.service = service;
        }

        public long getTaskId() {
            return taskId;
        }

        public long getDeadlineId() {
            return deadlineId;
        }

        @Override
		public Object call() throws Exception {
            try {
                service.executeEscalatedDeadline(taskId,
                        deadlineId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (deadlineId ^ (deadlineId >>> 32));
            result = prime * result + (int) (taskId ^ (taskId >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof ScheduledTaskDeadline)) return false;
            ScheduledTaskDeadline other = (ScheduledTaskDeadline) obj;
            if (deadlineId != other.deadlineId) return false;
            if (taskId != other.taskId) return false;
            return true;
        }

    }
}
