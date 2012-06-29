package com.conx.logistics.kernel.pageflow.services;

import java.util.Collection;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;

public interface IPageFlowSession {
	public IBPMProcessInstance getBPMProcessInstance();
	public Collection<PageFlowPage> getPages();
	public Component getWizardComponent();
	public Feature getOnCompletionFeature();
	public void nextPage();
	public void previousPage();
	public void abort();
}