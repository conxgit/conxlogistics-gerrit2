package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.vaadin.teemu.wizards.WizardStep;
import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedEventHandler;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedListener;
import com.conx.logistics.kernel.pageflow.event.PageFlowPageChangedEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public abstract class PageFlowPage implements WizardStep, IPageFlowPageChangedListener {
	public static final String PROCESS_ID = "PROCESS_ID"; // Process Id in BPMN
	public static final String TASK_NAME = "TASK_NAME"; // Task Name in BPMN
	
	private VerticalLayout canvas;
	private boolean executed;
	
	protected ITaskWizard wizard;
	protected IPageFlowPageChangedEventHandler pfpEventHandler;
	
	public abstract String getTaskName();
	public abstract void initialize(EntityManagerFactory emf, 
			                        PlatformTransactionManager ptm, 
			                        IPageFlowPageChangedEventHandler pfpEventHandler,
			                        ITaskWizard wizard);
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
	
	public void showWarningNotification(String caption, String message) {
		if (canvas != null) {
			if (canvas.getWindow() != null) {
				canvas.getWindow().showNotification(
	                    (String) caption,
	                    (String) message,
	                    Notification.TYPE_WARNING_MESSAGE);
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
	
	public boolean isExecuted() {
		return executed;
	}
	
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
	@Override
	public void onPageChanged(PageFlowPageChangedEvent event) {

	}
}
