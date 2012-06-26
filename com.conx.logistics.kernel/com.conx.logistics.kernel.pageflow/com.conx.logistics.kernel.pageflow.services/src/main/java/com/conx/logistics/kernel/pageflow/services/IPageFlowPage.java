package com.conx.logistics.kernel.pageflow.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public abstract class IPageFlowPage implements WizardStep {
	public static final String PROCESS_ID = "PROCESS_ID"; // Process Id in BPMN
	public static final String TASK_NAME = "TASK_NAME"; // Task Name in BPMN
	
	private VerticalLayout canvas;
	private Set<IPageFlowListener> listeners = new HashSet<IPageFlowListener>();
	
	public abstract String getTaskName();
	public abstract void initialize(EntityManagerFactory emf);
	public abstract Map<String, Object> getProcessState();
	public abstract void setProcessState(Map<String, Object> state);
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
		for (IPageFlowListener listener : listeners) {
			listener.onPrevious(this, getProcessState());
		}
		return true;
	}
	
	@Override
	public boolean onAdvance() {
		for (IPageFlowListener listener : listeners) {
			listener.onNext(this, getProcessState());
		}
		return true;
	}
	
	public void addListener(IPageFlowListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IPageFlowListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof IPageFlowPage) {
			return getTaskName().equals(((IPageFlowPage)o).getTaskName()); 
		}
		return false;
	}
}
