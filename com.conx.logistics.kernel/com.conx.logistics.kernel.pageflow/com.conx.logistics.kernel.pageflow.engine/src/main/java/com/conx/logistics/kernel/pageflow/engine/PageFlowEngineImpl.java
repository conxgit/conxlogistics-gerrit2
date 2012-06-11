package com.conx.logistics.kernel.pageflow.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conx.logistics.kernel.bpm.impl.jbpm.core.mock.BPMServiceFactory;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.mdm.domain.task.TaskDefinition;

public class PageFlowEngineImpl implements IPageFlowManager {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IBPMService bpmService;
	private List<IPageFlowSession> sessions;
	
	public PageFlowEngineImpl() {
		this.sessions = new ArrayList<IPageFlowSession>();
		this.bpmService = BPMServiceFactory.getBPMService();
	}
	
	/** EntityManagerFactories */
	private static final Map<String, List<IPageFlowPage>> pageCache = Collections
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
			IPageFlowPage page, Map properties) {
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
			IPageFlowPage page, Map properties) {
		String processId = (String)properties.get(IPageFlowPage.PROCESS_ID);
		logger.debug("unregisterPageFlowPage("+processId+")");	
		List<IPageFlowPage> list = this.pageCache.get(processId);
		if (list != null) {
			list.remove(page);
		}
	}

	@Override
	public IPageFlowSession startPageFlowSession(String userId,
			TaskDefinition td) {
		IPageFlowSession session = new PageFlowSessionImpl(getPages(td.getProcessId()));
		sessions.add(session);
		return session;
	}	
}