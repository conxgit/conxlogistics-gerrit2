package com.conx.logistics.kernel.pageflow.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jboss.bpm.console.client.model.TaskRef;
import org.jboss.bpm.console.client.model.TaskRef.STATE;
import org.jbpm.task.AccessType;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.service.ContentData;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;

public class PageFlowSessionImpl implements IPageFlowSession {
	private static final int WAIT_DELAY = 1000;

	private Map<String, PageFlowPage> pages;
	private List<PageFlowPage> orderedPageList;
	private ProcessInstanceRef processInstance;
	private TaskWizard wizard;
	private IBPMService bpmService;
	private List<org.jbpm.workflow.core.node.HumanTaskNode> tasks;
	private Task currentTask;
	private EntityManagerFactory emf;

	private Map<String, Object> processVars;

	private String userId;

	private Feature onCompletionFeature;

	public PageFlowSessionImpl(ProcessInstanceRef processInstance,
			String userId, Map<String, PageFlowPage> pageList,
			IBPMService bpmService, EntityManagerFactory emf,
			Feature onCompletionFeature) {
		this.onCompletionFeature = onCompletionFeature;
		this.bpmService = bpmService;
		this.processInstance = processInstance;
		this.userId = userId;
		this.tasks = this.bpmService.getProcessHumanTaskNodes(processInstance
				.getDefinitionId());
		// tasks.get(0).get
		// registerPages(pageList);
		this.pages = pageList;
		orderedPageList = orderPagesPerOrderedHumanTasks(this.tasks);
		this.emf = emf;
		try {
			currentTask = waitForNextTask();
			// Map<String, Object> vars =
			// bpmService.getProcessInstanceVariables(processInstance.getId());
			// processVars =
			// bpmService.findVariableInstances(Long.valueOf(processInstance.getId()));
			// Object res = this.bpmService.getTaskContentObject(currentTask);
			// String asnId = (String)res;
			// Set<String> varNames = vars.keySet();
			bpmService.nominate(currentTask.getId(), userId);
			//bpmService.getTaskById(currentTask.getId());
			// bpmService.az(currentTask.getId(), userId);
			processVars = bpmService
					.getProcessInstanceVariables(processInstance.getId());
			Object res = this.bpmService.getTaskContentObject(currentTask);
			processVars.put("Content", res);
			// processVars.put(key, value)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<PageFlowPage> orderPagesPerOrderedHumanTasks(
			List<org.jbpm.workflow.core.node.HumanTaskNode> htNodes) {

		List<PageFlowPage> pageList = new ArrayList<PageFlowPage>();
		PageFlowPage pg;
		for (org.jbpm.workflow.core.node.HumanTaskNode htNode : htNodes) {
			pg = this.pages.get(htNode.getName());
			pageList.add(pg);
		}

		return pageList;
	}
	
	private Task waitForNextTask() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		int count = 0;
		Thread.sleep(1000L);
		while (count < 10) {
			tasks = bpmService.getCreatedTasksByProcessId(Long
					.parseLong(processInstance.getId()));
			if (tasks.size() == 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			count++;
		}
		if (count == 10) {
			throw new Exception("waitForNextTask Timed out");
		}
		return tasks.get(0);
	}
	
	private void waitForTaskCompleteness(UserTransaction ut) throws Exception {
		Task res = null;
		int count = 0;
		while (count < 10) {
			ut.begin();
			res = this.bpmService.getTaskObjectById(currentTask.getId());
			ut.commit();
			if (res.getTaskData().getStatus() != Status.Completed)
			{
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else
				break;
			count++;
		}
		if (count == 10) {
			throw new Exception("waitForTaskCompleteness on Task("+currentTask.getId()+" Timed out");
		}
	}	
	

	private Object waitForContentResult() throws Exception {
		Object res = null;
		int count = 0;
		Thread.sleep(2000);
		while (count < 10) {
			res = this.bpmService.getTaskContentObject(currentTask);
			if (res instanceof Map)
			{
				if (   (((Map<String,Object>)res).size() == 1 && ((Map<String,Object>)res).containsKey("TaskName")))
				{
					try {
						Thread.sleep(WAIT_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else
					break;
			}
			count++;
		}
		if (count == 10) {
			throw new Exception("Content Wait on Task("+currentTask.getId()+" Timed out");
		}

		return res;
	}

	@Override
	public IBPMProcessInstance getBPMProcessInstance() {
		return null;
	}

	@Override
	public Collection<PageFlowPage> getPages() {
		return orderedPageList;
	}

	@Override
	public Component getWizardComponent() {
		return wizard;
	}

	@Override
	public void nextPage() {
	}

	@Override
	public void previousPage() {
	}

	public void start() {
		bpmService.startTask(currentTask.getId(), userId);
	}

	@Override
	public void abort() {
	}

	public TaskWizard getWizard() {
		return wizard;
	}

	public Task getCurrentTask() {
		return currentTask;
	}
	

	public Feature getSetOnCompletionFeature() {
		return onCompletionFeature;
	}

	public Map<String, Object> getProcessVars() {
		return processVars;
	}

	public void executeNext(UserTransaction ut, Object param) throws Exception {
		// 1. Complete the current task first
		try {
			ut.begin();
			if (param instanceof Map) {
				bpmService.completeTask(currentTask.getId(), (Map) param,
						userId);
			} else {
				ContentData contentData = null;
				if (param != null) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream out;
					try {
						out = new ObjectOutputStream(bos);
						out.writeObject(param);
						out.close();
						contentData = new ContentData();
						contentData.setContent(bos.toByteArray());
						contentData.setAccessType(AccessType.Inline);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				bpmService.completeTask(currentTask.getId(), contentData,
						userId);				
			}
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		
		waitForTaskCompleteness(ut);		
		

		// 2. Get the next task after Proc resume
		try {
			ut.begin();
			currentTask = null;
			currentTask = waitForNextTask();
			processVars = bpmService
					.getProcessInstanceVariables(processInstance.getId());
			Object res = this.bpmService.getTaskContentObject(currentTask);
			processVars.put("Content", res);			
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		
		// 3. If the next task exists, nominate and start it
		if (Validator.isNotNull(currentTask)) {
/*			try {
				ut.begin();
				processVars = bpmService
						.getProcessInstanceVariables(processInstance.getId());
				Object res = waitForContentResult();
				processVars.put("Content", res);
				ut.commit();
			} catch (Exception e) {
				ut.rollback();
				throw e;
			}	
			*/
			// 3.1 Nominate the current user for this task
			try {
				ut.begin();
				bpmService.nominate(currentTask.getId(), userId);				
				ut.commit();
			} catch (Exception e) {
				ut.rollback();
				throw e;
			}

			// 3.2 Start the task
			try {
				ut.begin();
				bpmService.startTask(currentTask.getId(), userId);	
				ut.commit();
			} catch (Exception e) {
				ut.rollback();
				throw e;
			}
		}

	}

	private PageFlowPage getNextPage(PageFlowPage currentPage) {
		for (int i = 0; i < orderedPageList.size(); i++) {
			if (orderedPageList.get(i).equals(currentPage)) {
				if (i != orderedPageList.size() - 1) {
					return orderedPageList.get(i + 1);
				}
			}
		}
		return null;
	}
/*
	@Override
	public void onNext(PageFlowPage currentPage, Map<String, Object> state) {
		PageFlowPage next = getNextPage(currentPage);
		if (next != null) {
			next.setProcessState(state);
		} else {
			// Process is over
		}
	}

	@Override
	public void onPrevious(PageFlowPage currentPage, Map<String, Object> state) {
	}
	*/

	public List<PageFlowPage> getOrderedPageList() {
		return orderedPageList;
	}

	public void setOrderedPageList(List<PageFlowPage> orderedPageList) {
		this.orderedPageList = orderedPageList;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Feature getOnCompletionFeature() {
		return onCompletionFeature;
	}

	public void setOnCompletionFeature(Feature onCompletionFeature) {
		this.onCompletionFeature = onCompletionFeature;
	}

	public void completeProcess(UserTransaction ut, Object param) throws Exception {
		// 1. Complete the current task first
		try {
			ut.begin();
			if (param instanceof Map) {
				bpmService.completeTask(currentTask.getId(), (Map) param,
						userId);
			} else {
				ContentData contentData = null;
				if (param != null) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream out;
					try {
						out = new ObjectOutputStream(bos);
						out.writeObject(param);
						out.close();
						contentData = new ContentData();
						contentData.setContent(bos.toByteArray());
						contentData.setAccessType(AccessType.Inline);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				bpmService.completeTask(currentTask.getId(), contentData,
						userId);
			}

			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}

		
		try
		{
			ut.begin();
			Thread.sleep(WAIT_DELAY);
			List<Task> tasks = bpmService.getCreatedTasksByProcessId(Long
						.parseLong(processInstance.getId()));
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		
	}

}
