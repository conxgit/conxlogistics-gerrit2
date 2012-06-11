package com.conx.logistics.kernel.pageflow.engine;

import java.util.List;

import org.vaadin.teemu.wizards.Wizard;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.vaadin.ui.Component;

public class PageFlowSessionImpl implements IPageFlowSession {
	private List<IPageFlowPage> pages;
	private Wizard wizard;
	
	public PageFlowSessionImpl(List<IPageFlowPage> pfps) {
		this.pages = pfps;
		wizard = new Wizard();
		wizard.setSizeFull();
		if (pages != null) {
			for (IPageFlowPage page : pages) {
				page.bindSession(this);
				wizard.addStep(page);
			}
		}
	}

	@Override
	public IBPMProcessInstance getBPMProcessInstance() {
		return null;
	}

	@Override
	public List<IPageFlowPage> getPages() {
		return pages;
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
}
