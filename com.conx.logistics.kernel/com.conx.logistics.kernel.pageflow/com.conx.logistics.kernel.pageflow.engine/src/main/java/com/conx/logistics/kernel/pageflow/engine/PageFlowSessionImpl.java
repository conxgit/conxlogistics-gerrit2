package com.conx.logistics.kernel.pageflow.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jbpm.marshalling.impl.JBPMMessages.ProcessInstance.NodeInstanceContent.HumanTaskNode;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.vaadin.teemu.wizards.Wizard;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.vaadin.ui.Component;

public class PageFlowSessionImpl implements IPageFlowSession {
	private static final int WAIT_DELAY = 500;
	
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
	
	private void showPage() {
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

	@Override
	public void abort() {
	}

	private void createWizard() throws Exception {
		wizard = new TaskWizard(this);
		wizard.setSizeFull();
		if (orderedPageList != null) {
			for (IPageFlowPage page : orderedPageList) {
				page.setEntityManagerFactory(emf);
				page.getContent();
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
	
	public void executeNext() throws Exception {
		bpmService.completeTask(currentTask.getId(), null, userId);
		currentTask = waitForNextTask();
	}	

}
