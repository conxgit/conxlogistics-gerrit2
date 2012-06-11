package com.conx.logistics.kernel.pageflow.engine;

import java.util.List;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class PageFlowSessionImpl implements IPageFlowSession {
	private List<IPageFlowPage> pages;
	private IBPMProcessInstance bpmProcess;
	private TabSheet wizard;
	
	public PageFlowSessionImpl(List<IPageFlowPage> pages, IBPMProcessInstance bpmProcess) {
		this.pages = pages;
		this.bpmProcess = bpmProcess;
		wizard = new TabSheet();
		wizard.setSizeFull();
		for (IPageFlowPage pfp : pages) {
			wizard.addTab(pfp.getPageComponent(), pfp.getTitle());
		}
	}

	@Override
	public IBPMProcessInstance getBPMProcessInstance() {
		return bpmProcess;
	}

	@Override
	public List<IPageFlowPage> getPages() {
		return pages;
	}

	@Override
	public Component getWizardComponent() {
		return wizard;
	}

}
