package com.conx.logistics.kernel.pageflow.engine.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.vaadin.teemu.wizards.Wizard;

import com.conx.logistics.kernel.pageflow.engine.PageFlowSessionImpl;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;

public class TaskWizard extends Wizard implements ITaskWizard{
	private PageFlowSessionImpl session;
	
	public TaskWizard(PageFlowSessionImpl session) {
		this.session = session;
	}

	public PageFlowSessionImpl getSession() {
		return session;
	}

	public void setSession(PageFlowSessionImpl session) {
		this.session = session;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("session",session);
		props.putAll(session.getProcessVars());
		return props;
	}

	@Override
	public Feature getOnCompletionFeature() {
		// TODO Auto-generated method stub
		return session.getSetOnCompletionFeature();
	}
	
	
}
