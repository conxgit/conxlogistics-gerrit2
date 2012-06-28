package com.conx.logistics.kernel.pageflow.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jbpm.task.Task;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.IPageFlowListener;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.vaadin.ui.Component;

public class PageFlowSessionImpl implements IPageFlowSession, IPageFlowListener {
	private static final int WAIT_DELAY = 1000;
	
	private Map<String, IPageFlowPage> pages;
	private List<IPageFlowPage> orderedPageList;
	private ProcessInstanceRef bpmInstance;
	private TaskWizard wizard;
	private IBPMService bpmService;
	private List<org.jbpm.workflow.core.node.HumanTaskNode> tasks;
	private Task currentTask;
	private EntityManagerFactory emf;

	private Map<String, Object> processVars;

	private String userId;
	
	public PageFlowSessionImpl(ProcessInstanceRef processInstance, String userId, Map<String,IPageFlowPage> pageList, IBPMService bpmService, EntityManagerFactory emf) {
		this.bpmService = bpmService;
		this.bpmInstance = processInstance;
		this.userId = userId;
		this.tasks = this.bpmService.getProcessHumanTaskNodes(processInstance.getDefinitionId());
		//tasks.get(0).get
		//registerPages(pageList);
		this.pages = pageList;
		orderedPageList = orderPagesPerOrderedHumanTasks(this.tasks);
		this.emf = emf;
		try {
			createWizard();
			currentTask = waitForNextTask();
			//Map<String, Object> vars = bpmService.getProcessInstanceVariables(processInstance.getId());
			//processVars = bpmService.findVariableInstances(Long.valueOf(processInstance.getId()));
			//Object res = this.bpmService.getTaskContentObject(currentTask);
			//String asnId = (String)res;
			//Set<String> varNames = vars.keySet();
			bpmService.nominate(currentTask.getId(), userId);
			//bpmService.az(currentTask.getId(), userId);
			processVars = bpmService.getProcessInstanceVariables(processInstance.getId());
			Object res = this.bpmService.getTaskContentObject(currentTask);
			processVars.put("Content", res);
			//processVars.put(key, value)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private List<IPageFlowPage> orderPagesPerOrderedHumanTasks(
			List<org.jbpm.workflow.core.node.HumanTaskNode> htNodes) {
		
		List<IPageFlowPage> pageList = new ArrayList<IPageFlowPage>();
		IPageFlowPage pg;
		for (org.jbpm.workflow.core.node.HumanTaskNode htNode : htNodes)
		{
			pg = this.pages.get(htNode.getName());
			pageList.add(pg);
		}
		
		return pageList;
	}

	private Task waitForNextTask() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		int count = 0;
		Thread.sleep(WAIT_DELAY);
		while (count < 10) {
			tasks = bpmService.getCreatedTasksByProcessId(Long.parseLong(bpmInstance.getId()));
			if (tasks.size() == 0) {
				try {
					Thread.sleep(WAIT_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			count++;
		}
		if (count == 10) {
			throw new Exception("Timed out");
		}
		return tasks.get(0);
	}

	@Override
	public IBPMProcessInstance getBPMProcessInstance() {
		return null;
	}

	@Override
	public Collection<IPageFlowPage> getPages() {
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

	private void createWizard() throws Exception {
		wizard = new TaskWizard(this);
		wizard.setSizeFull();
		if (orderedPageList != null) {
			for (IPageFlowPage page : orderedPageList) {
				page.initialize(emf);
				page.getContent();
				page.addListener(this);
				if (orderedPageList.get(0).equals(page)) {
					page.setProcessState(new HashMap<String, Object>());
				}
				wizard.addStep(page);
			}
		}
	}

	public TaskWizard getWizard() {
		return wizard;
	}


	public Task getCurrentTask() {
		return currentTask;
	}

	public Map<String, Object> getProcessVars() {
		return processVars;
	}
	
	public void executeNext(UserTransaction ut, Map<String, Object> params) throws Exception {
		 //1. Complete the current task first
		 try
		 {
			 ut.begin();
			 bpmService.completeTask(currentTask.getId(), params, userId);
			 ut.commit();
		 }
		 catch(Exception e)
		 {
			 ut.rollback();
			 throw e;
		 }	
		 
		 //2. Get the next task after Proc resume
		 try
		 {
			 ut.begin();
			 currentTask = waitForNextTask();
			 ut.commit();
		 }
		 catch(Exception e)
		 {
			 ut.rollback();
			 throw e;
		 }			 

		 //3. If the next task exists, nominate and start it
		 if (Validator.isNotNull(currentTask))
		 {
			 //3.1 Nominate the current user for this task
			 try
			 {
				 ut.begin();
				 bpmService.nominate(currentTask.getId(), userId);
				 ut.commit();
			 }
			 catch(Exception e)
			 {
				 ut.rollback();
				 throw e;
			 }	
		 
			//3.2 Start the task
			 try
			 {
				 ut.begin();
				 bpmService.startTask(currentTask.getId(), userId);
				 ut.commit();
			 }
			 catch(Exception e)
			 {
				 ut.rollback();
				 throw e;
			 }		
		 }
		
	}

	private IPageFlowPage getNextPage(IPageFlowPage currentPage) {
		for (int i = 0; i < orderedPageList.size(); i++) {
			if (orderedPageList.get(i).equals(currentPage)) {
				if (i != orderedPageList.size() - 1) {
					return orderedPageList.get(i + 1);
				}
			}
		}
		return null;
	}

	@Override
	public void onNext(IPageFlowPage currentPage, Map<String, Object> state) {
		IPageFlowPage next = getNextPage(currentPage);
		if (next != null) {
			next.setProcessState(state);
		} else {
			// Process is over
		}
	}


	@Override
	public void onPrevious(IPageFlowPage currentPage, Map<String, Object> state) {
		// TODO Auto-generated method stub
	}	

}
