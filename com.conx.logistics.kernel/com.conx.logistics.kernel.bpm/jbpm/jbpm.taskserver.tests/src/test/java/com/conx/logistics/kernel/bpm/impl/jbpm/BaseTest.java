package com.conx.logistics.kernel.bpm.impl.jbpm;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.SendIcal;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactoryService;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactoryService;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactoryService;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.util.ServiceRegistry;
import org.osgi.framework.ServiceReference;

import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service.MockEscalatedDeadlineHandler;
import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service.MockEscalatedDeadlineHandler.Item;
import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service.MockUserInfo;
import com.conx.logistics.testingframework.tools.AbstractDroolsSpringDMTest;

import org.osgi.framework.ServiceReference;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public abstract class BaseTest extends AbstractDroolsSpringDMTest {

	protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    protected EntityManagerFactory emfTaskJPA;

    protected Map<String, User> users;
    protected Map<String, Group> groups;

    protected TaskService taskService;
    protected TaskServiceSession taskSession;

    protected final boolean useJTA = false;
    private PoolingDataSource ds;
    

    protected EntityManagerFactory createEntityManagerFactory() { 
        return emfTaskJPA;
    }
    
    protected void onSetUp() throws Exception {
        ServiceReference kbuilderRef = bundleContext.getServiceReference( KnowledgeBuilderFactoryService.class.getName() );
        Thread.currentThread().setContextClassLoader(  bundleContext.getService( kbuilderRef ).getClass().getClassLoader()  );

        //--
        Properties conf = new Properties();
        conf.setProperty("mail.smtp.host", "localhost");
        conf.setProperty("mail.smtp.port", "2345");
        conf.setProperty("from", "from@domain.com");
        conf.setProperty("replyTo", "replyTo@domain.com");
        conf.setProperty("defaultLanguage", "en-UK");
        SendIcal.initInstance(conf);

        if( useJTA ) { 
            ds = new PoolingDataSource();
            ds.setUniqueName( "jdbc/taskDS" );
            ds.setClassName( "bitronix.tm.resource.jdbc.lrc.LrcXADataSource" );
            ds.setMaxPoolSize( 3 );
            ds.setAllowLocalTransactions( true );
            ds.getDriverProperties().put( "user", "sa" );
            ds.getDriverProperties().put( "password", "sasa" );
            ds.getDriverProperties().put( "url", "jdbc:h2:mem:taskDb" );
            ds.getDriverProperties().put( "driverClassName", "org.h2.Driver" );
            ds.init();
        }
        
        // Use persistence.xml configuration
        emfTaskJPA = createEntityManagerFactory();

        taskService = new TaskService(emfTaskJPA, SystemEventListenerFactory.getSystemEventListener());
        MockUserInfo userInfo = new MockUserInfo();
        taskService.setUserinfo(userInfo);
        users = fillUsersOrGroups("LoadUsers.mvel");
        groups = fillUsersOrGroups("LoadGroups.mvel");
        taskService.addUsersAndGroups(users, groups);
        disableUserGroupCallback();
        
        logger = LoggerFactory.getLogger(getClass());
        
        taskSession = taskService.createSession();
    }

    protected void onTearDown() throws Exception {
        taskSession.dispose();
        emfTaskJPA.close();
        if( useJTA ) { 
            ds.close();
        }        
    }    

    
    public void disableUserGroupCallback() {
        UserGroupCallbackManager.getInstance().setCallback(null);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map fillUsersOrGroups(String mvelFileName) throws Exception { 
        Map<String, Object> vars = new HashMap<String, Object>();
        Reader reader = null;
        Map<String, Object> result = null;
        
        try {
            reader = new InputStreamReader(BaseTest.class.getResourceAsStream(mvelFileName));
            result = (Map<String, Object>) eval(reader, vars);
        } finally {
            if (reader != null) reader.close();
        }
        
        return result;
    }
    
    public static void loadUsersAndGroups(TaskServiceSession taskSession, Map<String, User> users, Map<String, Group> groups) throws Exception {
        for (User user : users.values()) {
            taskSession.addUser(user);
        }

        for (Group group : groups.values()) {
            taskSession.addGroup(group);
        }
    }

    public static Object eval(Reader reader, Map vars) {
        vars.put("now", new Date());
        return TaskService.eval(reader, vars);
    }
    
    public Object eval(String str, Map vars) {
        vars.put("now", new Date());
        return TaskService.eval(str, vars);
    }
    
    protected Map<String, Object> fillVariables() { 
        return fillVariables(users, groups);
    }
    
    public static Map<String, Object> fillVariables(Map<String, User> users, Map<String, Group> groups ) { 
        Map <String, Object> vars = new HashMap<String, Object>();
        vars.put( "users", users );
        vars.put( "groups", groups );        
        vars.put( "now", new Date() );
        return vars;
    }
    
    protected static void testDeadlines(long now, MockEscalatedDeadlineHandler handler) throws Exception { 
        int sleep = 8000;
        handler.wait(3, sleep);

        assertEquals(3, handler.getList().size());

        boolean firstDeadlineMet = false;
        boolean secondDeadlineMet = false;
        boolean thirdDeadlineMet = false;
        for( Item item : handler.getList() ) { 
            long deadlineTime = item.getDeadline().getDate().getTime();
            if( deadlineTime == now + 2000 ) { 
                firstDeadlineMet = true;
            }
            else if( deadlineTime == now + 4000 ) { 
                secondDeadlineMet = true;
            }
            else if( deadlineTime == now + 6000 ) { 
                thirdDeadlineMet = true;
            }
            else { 
                fail( deadlineTime + " is not an expected deadline time [now=" + now + "]" );
            }
        }
        
        assertTrue( "First deadline was not met." , firstDeadlineMet );
        assertTrue( "Second deadline was not met." , secondDeadlineMet );
        assertTrue( "Third deadline was not met." , thirdDeadlineMet );   
        
        // Wait for deadlines to finish
        Thread.sleep(1000);
    }

	public EntityManagerFactory getEmf() {
		return emfTaskJPA;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emfTaskJPA = emf;
	}
}
