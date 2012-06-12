package com.conx.logistics.kernel.bpm.impl.jbpm.taskserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.kernel.bpm.services.IBPMTaskService;

public class HumanTaskServer implements IBPMTaskService {
	private static Logger logger = LoggerFactory
			.getLogger(HumanTaskServer.class);

	private MinaTaskServer minaServer;
	private Thread minaServerThread;
	
	private EntityManagerFactory emfOrgJbpmTask;
	private PlatformTransactionManager globalJTATransManager;

	public void stop() {
		minaServer.stop();
	}

	public void start() {
		
		//-- Submit to repository
		//emfOrgJbpmTask = Persistence.createEntityManagerFactory("org.jbpm.task");
		TaskService taskService = new TaskService(emfOrgJbpmTask,
				SystemEventListenerFactory.getSystemEventListener());
		TaskServiceSession taskSession = taskService.createSession();

		// Add users
		Map vars = new HashMap();
		Reader reader;

		try {
			URL usersURL = HumanTaskServer.class.getClassLoader().getResource("LoadUsers.mvel");
			reader = new FileReader(new File(usersURL.toURI()));

			Map<String, User> users = (Map<String, User>) eval(reader, vars);
			for (User user : users.values()) {
				taskSession.addUser(user);

			}

			URL grpsURL = HumanTaskServer.class.getClassLoader().getResource("LoadGroups.mvel");
			reader = new FileReader(new File(grpsURL.toURI()));			
			
			Map<String, Group> groups = (Map<String, Group>) eval(reader, vars);
			for (Group group : groups.values()) {
				taskSession.addGroup(group);
			}
			
			//kernelSystemBPMTransManager.commit(status);
		} catch (FileNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();	
			logger.error(stacktrace);
			//kernelSystemBPMTransManager.rollback(status);
		} catch (URISyntaxException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();		
			logger.error(stacktrace);
			//kernelSystemBPMTransManager.rollback(status);
		}

		// start server
		minaServer = new MinaTaskServer(taskService);
		minaServerThread = new Thread(minaServer);
		minaServerThread.start();
		taskSession.dispose();

		logger.debug("Task service started correctly !");
		logger.debug("Task service running ...");
	}

	@SuppressWarnings("rawtypes")
	public static Object eval(Reader reader, Map vars) {
		return TaskService.eval(reader, vars);
	}

	public static String readerToString(Reader reader) throws IOException {
		int charValue = 0;
		StringBuffer sb = new StringBuffer(1024);
		while ((charValue = reader.read()) != -1) {
			// result = result + (char) charValue;
			sb.append((char) charValue);
		}
		return sb.toString();
	}

	public void setJbpmTaskEMF(EntityManagerFactory emfOrgJbpmTask) {
		this.emfOrgJbpmTask = emfOrgJbpmTask;
	}

	public void setGlobalJTATransManager(
			PlatformTransactionManager kernelSystemBPMTransManager) {
		this.globalJTATransManager = kernelSystemBPMTransManager;
	}
	
}
