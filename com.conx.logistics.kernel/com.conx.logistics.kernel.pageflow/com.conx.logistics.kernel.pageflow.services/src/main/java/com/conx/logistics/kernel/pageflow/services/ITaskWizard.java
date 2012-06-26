package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import com.vaadin.ui.Component;

public interface ITaskWizard {
	public Component getComponent();
	public Map<String,Object> getProperties();
}
