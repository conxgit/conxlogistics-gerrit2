package com.conx.logistics.kernel.pageflow.services;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.springframework.transaction.PlatformTransactionManager;

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
	public void start();
	public Map<String, Object> getProcessVars();
	public EntityManagerFactory getConXEntityManagerfactory();
	public PlatformTransactionManager getJTAGlobalTransactionManager();
	
	
	public void completeProcess(UserTransaction userTransaction, Object data) throws Exception;
	public boolean executeNext(UserTransaction userTransaction, Object data) throws Exception;
	public Map<String, Object> updateProcessInstanceVariables(
			UserTransaction userTransaction, Map<String, Object> varsToUpdate) throws Exception;
	
}