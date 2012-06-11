package com.conx.logistics.kernel.pageflow.services;

import java.util.List;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.vaadin.ui.Component;

public interface IPageFlowSession {
	public IBPMProcessInstance getBPMProcessInstance();
	public List<IPageFlowPage> getPages();
	public Component getWizardComponent();
}
