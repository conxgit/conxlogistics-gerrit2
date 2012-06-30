package org.jbpm.task.service.persistence;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.jbpm.task.admin.TasksAdmin;
import org.jbpm.task.admin.TasksAdminImpl;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.JTACustomTaskService;
import org.jbpm.task.service.persistence.TaskPersistenceManager;
import org.jbpm.task.service.persistence.TaskSessionFactory;
import org.springframework.jndi.JndiTemplate;

public class JTATaskSessionFactoryImpl implements TaskSessionFactory {
	private final EntityManagerFactory emf;
	private final JTACustomTaskService taskService;
	private final boolean useJTA;
	private JndiTemplate jndiTemplate;
	private TransactionManager tm;
	private UserTransaction userTransaction;

	public JTATaskSessionFactoryImpl(
			TransactionManager tm,
			JndiTemplate jndiTemplate,
			UserTransaction userTransaction, JTACustomTaskService taskService,
			EntityManagerFactory emf) {
		this.emf = emf;
		this.taskService = taskService;
		this.jndiTemplate = jndiTemplate;
		this.userTransaction = userTransaction;
		this.tm = tm;
		useJTA = useJTATransactions(emf);
	}

	static boolean useJTATransactions(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();

		boolean useJTA = false;
		try {
			em.getTransaction();
		} catch (Exception e) {
			boolean illegalStateExceptionThrown = false;
			Throwable cause = e;
			while (cause != null && !illegalStateExceptionThrown) {
				illegalStateExceptionThrown = (cause instanceof IllegalStateException);
				cause = cause.getCause();
			}
			if (illegalStateExceptionThrown) {
				useJTA = true;
			} else {
				// this resource is not JTA
				throw new RuntimeException(
						"Unable to determine persistence-unit type (JTA/Local)",
						e);
			}
		}

		return useJTA;
	}

	public TaskServiceSession createTaskServiceSession() {
		TaskPersistenceManager tpm;
		if (useJTA) {
			UserTransaction ut = userTransaction;
			/*
			try {
				Context ctx = jndiTemplate.getContext();
				 ut = (UserTransaction)ctx.lookup( "java:comp/UserTransaction" );
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			tpm = new TaskPersistenceManager(emf.createEntityManager(),
					new TaskJTACustomTransactionManager(this.tm,ut));
		} else {
			tpm = new TaskPersistenceManager(emf.createEntityManager());
		}
		return new TaskServiceSession(taskService, tpm);
	}

	public TasksAdmin createTaskAdmin() {
		TaskPersistenceManager tpm;
		if (useJTA) {
			UserTransaction ut = userTransaction;
			/*
			try {
				Context ctx = jndiTemplate.getContext();
				 ut = (UserTransaction)ctx.lookup( "java:comp/UserTransaction" );
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			*/	
			tpm = new TaskPersistenceManager(emf.createEntityManager(),
					new TaskJTACustomTransactionManager(this.tm,ut));
		} else {
			tpm = new TaskPersistenceManager(emf.createEntityManager());
		}
		return new TasksAdminImpl(tpm);
	}
}
