package com.conx.logistics.kernel.pageflow.engine;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.vaadin.teemu.wizards.Wizard;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.vaadin.ui.Component;

public class PageFlowSessionImpl implements IPageFlowSession {
//	private static final int WAIT_DELAY = 500;
	
//	private Map<String, IPageFlowPage> pages;
//	private ProcessInstanceRef bpmInstance;
	private Wizard wizard;
//	private IBPMService bpmService;
//	private List<HumanTaskNode> tasks;
//	private HumanTaskNode currentTaskNode;
	private List<IPageFlowPage> pageList;
	private EntityManagerFactory emf;
	
	public PageFlowSessionImpl(ProcessInstanceRef processInstance, String userId, List<IPageFlowPage> pageList, IBPMService bpmService, EntityManagerFactory emf) {
//		this.bpmService = bpmService;
//		this.bpmInstance = processInstance;
//		this.tasks = bpmService.getProcessHumanTaskNodes(processInstance.getDefinitionId());
//		tasks.get(0).get
//		registerPages(pageList);
		this.emf = emf;
		this.pageList = pageList;
		try {
			createWizard();
//			waitForNextTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	private TaskSummary waitForNextTask() throws Exception {
		List<TaskSummary> tasks = new ArrayList<TaskSummary>();
		int count = 0;
		while (count < 10) {
			tasks = bpmService.getReadyTasksByProcessId(Long.parseLong(bpmInstance.getId()));
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
	} */

	@Override
	public IBPMProcessInstance getBPMProcessInstance() {
		return null;
	}

	@Override
	public Collection<IPageFlowPage> getPages() {
		return pageList;
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
	/*
	private void registerPages(List<IPageFlowPage> pageList) {
		pages = new HashMap<String, IPageFlowPage>();
		for (IPageFlowPage page : pageList) {
			pages.put(page.getTaskName(), page);
		}
	}
	*/
	private void createWizard() throws Exception {
		wizard = new Wizard();
		wizard.setSizeFull();
		if (pageList != null) {
			for (IPageFlowPage page : pageList) {
				page.setEntityManagerFactory(emf);
				page.getContent();
				wizard.addStep(page);
			}
		}
	}
}
