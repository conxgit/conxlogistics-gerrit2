package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;

public abstract class IPageFlowPage implements WizardStep {
	public static final String PROCESS_ID = "PROCESS_ID"; // Process Id in BPMN
	
	private IPageFlowSession session;
	protected EntityManagerFactory emf;
	
	public void bindSession(IPageFlowSession session) {
		this.session = session;
	}
	
	public abstract String getTaskName();
	
	@Override
	public abstract Component getContent();
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public abstract void setDataContainerMap(Map<String,Container> containerMap);
	
	public abstract Map<String,Container> getDataContainerMap();
	
	@Override
	public abstract String getCaption();
	
	@Override
	public boolean onBack() {
		if (session != null) {
			session.previousPage();
		}
		return true;
	}
	
	@Override
	public boolean onAdvance() {
		if (session != null) {
			session.nextPage();
		}
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof IPageFlowPage) {
			return getTaskName().equals(((IPageFlowPage)o).getTaskName()); 
		}
		return false;
	}
}
