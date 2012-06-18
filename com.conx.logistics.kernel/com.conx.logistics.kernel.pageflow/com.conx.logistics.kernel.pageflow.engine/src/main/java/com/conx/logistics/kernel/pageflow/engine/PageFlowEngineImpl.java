package com.conx.logistics.kernel.pageflow.engine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.mdm.domain.task.TaskDefinition;

public class PageFlowEngineImpl implements IPageFlowManager {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IBPMService bpmService;
	private EntityManagerFactory conxlogisticsEMF;
	private PlatformTransactionManager globalTransactionManager;
	private List<IPageFlowSession> sessions;
	
	public PageFlowEngineImpl() {
		this.sessions = new ArrayList<IPageFlowSession>();
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
	}
	
	public void stop() {
	}
	
	/** EntityManagerFactories */
	private final Map<String, List<IPageFlowPage>> pageCache = Collections
			.synchronizedMap(new HashMap<String, List<IPageFlowPage>>());	
	
	private List<IPageFlowPage> getPages(String processId) {
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
		logger.debug("registerPageFlowPage("+processId+")");		
		List<IPageFlowPage> list = this.pageCache.get(processId);
		if (list == null) {
			list = new ArrayList<IPageFlowPage>();
			list.add(page);
			pageCache.put(processId, list);
		} else {
			list.add(page);
		}
	}

	public void unregisterPageFlowPage(
			IPageFlowPage page, Map<String, Object> properties) {
		String processId = (String)properties.get(IPageFlowPage.PROCESS_ID);
		logger.debug("unregisterPageFlowPage("+processId+")");	
		List<IPageFlowPage> list = this.pageCache.get(processId);
		if (list != null) {
			list.remove(page);
		}
	}

	@Override
	public IPageFlowSession startPageFlowSession(String userId, TaskDefinition td) {
//		ProcessInstanceRef pi = bpmService.newInstance(td.getProcessId());
		IPageFlowSession session = new PageFlowSessionImpl(null, userId, getPages(td.getProcessId()), this.bpmService, conxlogisticsEMF);
		sessions.add(session);
		return session;
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
}