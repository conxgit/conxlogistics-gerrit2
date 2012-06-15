package com.conx.logistics.kernel.pageflow.services;

import java.util.Collection;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.vaadin.ui.Component;

public interface IPageFlowSession {
	public IBPMProcessInstance getBPMProcessInstance();
	public Collection<IPageFlowPage> getPages();
	public Component getWizardComponent();
	public void nextPage();
	public void previousPage();
	public void abort();
}