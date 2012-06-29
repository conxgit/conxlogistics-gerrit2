package com.conx.logistics.kernel.pageflow.engine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.mdm.domain.application.Feature;
import com.conx.logistics.mdm.domain.task.TaskDefinition;

public class PageFlowEngineImpl implements IPageFlowManager {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IBPMService bpmService;
	private EntityManagerFactory conxlogisticsEMF;
	private PlatformTransactionManager globalTransactionManager;
	private List<IPageFlowSession> sessions;
	
	private JndiTemplate jndiTemplate;
	
	public PageFlowEngineImpl() {
		this.sessions = new ArrayList<IPageFlowSession>();
	}
	
	public void setJndiTemplate(JndiTemplate jndiTemplate) {
		this.jndiTemplate = jndiTemplate;
	}
	
	@SuppressWarnings("unused")
	public void setBpmService(IBPMService bpmService) {
		try {
			this.bpmService = bpmService;
			//bpmService.startNewProcess("","");
			System.out.println("Test");
		} 
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
		}	
		catch (Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
		}		
	}
	
	public void start() {
		try 
		{
			ProcessInstanceRef pi = bpmService.newInstance("defaultPackage.goat1");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public void stop() {
	}
	
	/** EntityManagerFactories */
	private final Map<String, Map<String,IPageFlowPage>> pageCache = Collections
			.synchronizedMap(new HashMap<String, Map<String,IPageFlowPage>>());	
	
	private Map<String,IPageFlowPage> getPages(String processId) {
		return pageCache.get(processId);
	}

	@Override
	public IPageFlowSession closePageFlowSession(String userid,
			Long pageFlowSessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPageFlowSession resumePageFlowSession(String userid,
			Long pageFlowSessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerPageFlowPage(
			IPageFlowPage page, Map<String, Object> properties) {
		String processId = (String)properties.get(IPageFlowPage.PROCESS_ID);
		String taskName = (String)properties.get(IPageFlowPage.TASK_NAME);
		logger.debug("registerPageFlowPage("+processId+","+taskName+")");		
		Map<String,IPageFlowPage> map = this.pageCache.get(processId);
		if (map == null) {
			map = new HashMap<String,IPageFlowPage>();
			pageCache.put(processId, map);
		} 
		map.put(taskName,page);	
	}

	public void unregisterPageFlowPage(
			IPageFlowPage page, Map<String, Object> properties) {
		String processId = (String)properties.get(IPageFlowPage.PROCESS_ID);
		logger.debug("unregisterPageFlowPage("+processId+")");	
		Map<String,IPageFlowPage> map = this.pageCache.get(processId);
		String taskName = (String)properties.get(IPageFlowPage.TASK_NAME);
		if (map != null) {
			map.remove(taskName);
		}
	}

	@Override
	public IPageFlowSession startPageFlowSession(String userId, TaskDefinition td) {
//		ProcessInstanceRef pi = bpmService.newInstance(td.getProcessId());
		//IPageFlowSession session = new PageFlowSessionImpl(null, userId, getPages(td.getProcessId()), this.bpmService, conxlogisticsEMF);
		//sessions.add(session);
		return null;
	}

	public EntityManagerFactory getConxlogisticsEMF() {
		return conxlogisticsEMF;
	}

	public void setConxlogisticsEMF(EntityManagerFactory conxlogisticsEMF) {
		this.conxlogisticsEMF = conxlogisticsEMF;
	}

	public PlatformTransactionManager getGlobalTransactionManager() {
		return globalTransactionManager;
	}

	public void setGlobalTransactionManager(
			PlatformTransactionManager globalTransactionManager) {
		this.globalTransactionManager = globalTransactionManager;
	}

	@Override
	public ITaskWizard createTaskWizard(Map<String, Object> properties) throws Exception {
		ProcessInstanceRef pi = null;
		PageFlowSessionImpl session = null;
		
		 String processId = (String)properties.get("processId");
		 String userId = (String)properties.get("userId");
		 Feature   onCompletionCompletionFeature = (Feature)properties.get("onCompletionFeature");
		
		 Context ctx = jndiTemplate.getContext();
		 UserTransaction ut = (UserTransaction)ctx.lookup( "java:comp/UserTransaction" );

		 try
		 {
			 ut.begin();

			 pi = bpmService.newInstance(processId);
				
			 ut.commit();
		 }
		 catch(Exception e)
		 {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);				 
			 ut.rollback();
			 throw e;
		 }
		 
		 try
		 {
			 ut.begin();
			 
			 Map<String,IPageFlowPage> pageList = pageCache.get(processId);
			 session = new PageFlowSessionImpl(pi, userId, pageList, this.bpmService, conxlogisticsEMF, onCompletionCompletionFeature);
			 sessions.add(session);	
			 
			 ut.commit();
		 }
		 catch(Exception e)
		 {
			 ut.rollback();
			 throw e;
		 }		 
		 
		 try
		 {
			 ut.begin();
			 
			 session.start();
			 
			 ut.commit();
		 }
		 catch(Exception e)
		 {
			 ut.rollback();
			 throw e;
		 }			 
		/*
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.globalTransactionManager.getTransaction(def);
		try {
			String processId = (String)properties.get("processId");
			String userId = (String)properties.get("userId");
			

			pi = bpmService.newInstance(processId);

			this.globalTransactionManager.commit(status);
	
			Map<String,IPageFlowPage> pageList = pageCache.get(processId);
			
			session = new PageFlowSessionImpl(pi, userId, pageList, this.bpmService, conxlogisticsEMF);
			sessions.add(session);
			
			//TaskWizard tw = new TaskWizard(session);
		}
		catch (Exception ex) {
			this.globalTransactionManager.rollback(status);
			throw ex;
		}
		*/		
		
		return session.getWizard();
	}	
	
	@Override
	public ITaskWizard executeTaskWizard(ITaskWizard tw, Object data) throws Exception {
		 Context ctx = jndiTemplate.getContext();
		 UserTransaction ut = (UserTransaction)ctx.lookup( "java:comp/UserTransaction" );
		((TaskWizard)tw).getSession().executeNext(ut,data);
		return tw;
	}
}