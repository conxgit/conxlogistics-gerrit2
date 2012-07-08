package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;

public interface ITaskWizard extends IPageFlowListener {
	public Component getComponent();
	public Feature getOnCompletionFeature();
	public Map<String,Object> getProperties();
	public void setNextEnabled(boolean isEnabled);
	public boolean isNextEnabled();
	public void setBackEnabled(boolean isEnabled);
	public boolean isBackEnabled();
}
