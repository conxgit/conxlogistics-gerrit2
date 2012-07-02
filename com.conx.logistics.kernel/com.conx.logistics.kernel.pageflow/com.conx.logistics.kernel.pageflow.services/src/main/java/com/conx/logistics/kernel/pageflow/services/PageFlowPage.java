package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.vaadin.teemu.wizards.WizardStep;
import org.springframework.transaction.PlatformTransactionManager;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public abstract class PageFlowPage implements WizardStep {
	public static final String PROCESS_ID = "PROCESS_ID"; // Process Id in BPMN
	public static final String TASK_NAME = "TASK_NAME"; // Task Name in BPMN
	
	private VerticalLayout canvas;
	
	public abstract String getTaskName();
	public abstract void initialize(EntityManagerFactory emf, PlatformTransactionManager ptm);
	public abstract void setOnStartState(Map<String, Object> params);
	public abstract Map<String, Object> getOnCompleteState();
	
	@Override
	public abstract String getCaption();
	
	public void setCanvas(VerticalLayout canvas) {
		this.canvas = canvas;
	}
	
	public VerticalLayout getCanvas() {
		return this.canvas;
	}
	
	public void showNotification(String caption, String message) {
		if (canvas != null) {
			if (canvas.getWindow() != null) {
				canvas.getWindow().showNotification(
	                    (String) caption,
	                    (String) message);
			}
		}
	}
	
	public void showErrorNotification(String caption, String message) {
		if (canvas != null) {
			if (canvas.getWindow() != null) {
				canvas.getWindow().showNotification(
	                    (String) caption,
	                    (String) message,
	                    Notification.TYPE_ERROR_MESSAGE);
			}
		}
	}
	
	public void showTrayNotification(String caption, String message) {
		if (canvas != null) {
			if (canvas.getWindow() != null) {
				canvas.getWindow().showNotification(
	                    (String) caption,
	                    (String) message,
	                    Notification.TYPE_TRAY_NOTIFICATION);
			}
		}
	}
	
	@Override
	public Component getContent() {
		return canvas;
	}
	

	@Override
	public boolean onBack() {
		return true;
	}
	
	@Override
	public boolean onAdvance() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PageFlowPage) {
			return getTaskName().equals(((PageFlowPage)o).getTaskName()); 
		}
		return false;
	}
}
