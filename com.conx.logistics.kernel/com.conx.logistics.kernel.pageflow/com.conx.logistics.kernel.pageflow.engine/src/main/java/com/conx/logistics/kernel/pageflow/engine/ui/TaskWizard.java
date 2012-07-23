package com.conx.logistics.kernel.pageflow.engine.ui;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.pageflow.engine.PageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.PageFlowSessionImpl;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedEventHandler;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedListener;
import com.conx.logistics.kernel.pageflow.event.PageFlowPageChangedEvent;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window.Notification;

public class TaskWizard extends Wizard implements ITaskWizard, IPageFlowPageChangedEventHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final long serialVersionUID = 8417208260717324494L;
	
	private IPageFlowSession session;
	private PageFlowEngineImpl engine;

	private Feature onCompletionCompletionFeature;

	private IPresenter<?, ? extends EventBus> onCompletionCompletionViewPresenter;
	
	private final Set<IPageFlowPageChangedListener> pageFlowPageChangedListenerCache = Collections
			.synchronizedSet(new HashSet<IPageFlowPageChangedListener>());
	
	private boolean nextButtonBlocked = false;
	private boolean backButtonBlocked = false;

	private boolean processPageFlowPageChangedEvents;
	
	public TaskWizard(IPageFlowSession session) {
		this.engine = engine;
		this.session = session;
		this.onCompletionCompletionFeature = onCompletionCompletionFeature;
		this.onCompletionCompletionViewPresenter = onCompletionCompletionViewPresenter;
		
		getNextButton().setImmediate(true);
		getBackButton().setImmediate(true);
		
		
		//Init steps
		if (session.getPages() != null) {
			for (PageFlowPage page : session.getPages()) {
				page.initialize(session.getConXEntityManagerfactory(), 
							    session.getJTAGlobalTransactionManager(), 
						        (IPageFlowPageChangedEventHandler)this,
						        (ITaskWizard)this);
				addStep(page);
			}
		}		
	}

	public IPageFlowSession getSession() {
		return session;
	}

	public void setSession(PageFlowSessionImpl session) {
		this.session = session;
	}

	@Override
	public Component getComponent() {
		return this;
	}
	
	public WizardStep getCurrentStep()
	{
		return currentStep;
	}
	
	public void setCurrentStep(WizardStep step)
	{
		currentStep = step;
	}	
	
	@Override
    public void addStep(WizardStep step) {
		if (getSteps().size() == 0)//First page
			((PageFlowPage)step).setOnStartState(getProperties());
        super.addStep(step);
    }

	@Override
	public Map<String, Object> getProperties() {
		//Map<String,Object> props = new HashMap<String, Object>();
		//props.put("session",session);
		//props.putAll(session.getProcessVars());
		return session.getProcessVars();
	}

	@Override
	public Feature getOnCompletionFeature() {
		return session.getOnCompletionFeature();
	}

	@Override
	public void onNext(PageFlowPage currentPage, Map<String, Object> taskOutParams) {
		try {
			engine.executeTaskWizard(this, taskOutParams);
//			getProperties().
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPrevious(PageFlowPage currentPage, Map<String, Object> state) {
		// TODO Implement previous
	}
	
	@Override
	public void next() {
		getNextButton().setEnabled(false);
		getBackButton().setEnabled(false);
		getCancelButton().setEnabled(false);
		try {
			completeCurrentTaskAndAdvanceToNext();
		} catch (Exception e) {
			e.printStackTrace();
			getWindow().showNotification("There was an unexpected error on the next page", "</br>Try to continue again. If the problem persists, contact your Administrator", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		super.next();
		getNextButton().setEnabled(!nextButtonBlocked);
		getBackButton().setEnabled(!backButtonBlocked);
		getCancelButton().setEnabled(false);
	}
	
	@Override
	public void back() {
		super.back();
	}
	
	@Override
    public void finish() {
		try {
			completeCurrentTaskAndAdvanceToNext();
		} catch (Exception e) {
			e.printStackTrace();
			getWindow().showNotification("There was an unexpected error on the next page", "</br>Try to continue again. If the problem persists, contact your Administrator", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
    	IMainApplication mainApp = this.engine.getMainApp();
    	if (Validator.isNotNull(mainApp))
    	{
    		com.conx.logistics.mdm.domain.application.Application parentApp = onCompletionCompletionFeature.getParentApplication();
    		
    		IApplicationViewContribution avc = mainApp.getApplicationContributionByCode(parentApp.getCode());

			String viewCode = onCompletionCompletionFeature.getCode();
			IViewContribution vc = this.engine.getMainApp().getViewContributionByCode(viewCode);
			if (avc != null)
			{
				Method featureViewerMethod = null;
				Class<?> featureViewerMethodHandler = null;
				try {
/*					IPresenterFactory pf = this.engine.getMainApp().getPresenterFactory();
					IPresenter<?, ? extends EventBus> viewPresenter = pf.createPresenter(avc.getPresenterClass((Application)mainApp));
					EventBus buss = (EventBus)viewPresenter.getEventBus();*/
					
				    Method  openFeatureViewMethod = onCompletionCompletionViewPresenter.getClass().getMethod("onOpenFeatureView", Feature.class);
				    Object[] args = new Object[1];
				    args[0] = onCompletionCompletionFeature;
				    openFeatureViewMethod.invoke(onCompletionCompletionViewPresenter, args);
				    /*
				    for (Method event : events) {
				      //logger.info("Event method: {} - handlers: ", event.getName());
				      org.vaadin.mvp.eventbus.annotation.Event ea = event.getAnnotation(org.vaadin.mvp.eventbus.annotation.Event.class);
				      if (ea != null)
				      {
					      for (Class<?> handler : ea.handlers()) {
					    	  featureViewerMethodHandler = handler;
					      }
				      }
				    }	
				    */				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}    				
    	}
    	
    	super.finish();
    }

	private void completeCurrentTaskAndAdvanceToNext() throws Exception {
		PageFlowPage currentPage, nextPage;
		Map<String, Object> params = null;
		currentPage = (PageFlowPage) currentStep;
		if (currentPage.isExecuted()) {
			engine.updateProcessInstanceVariables(this, currentPage.getOnCompleteState());
		} else {
			// Complete current task and get input variables for the next task
			try {
				params = currentPage.getOnCompleteState(); // Completes current task with
				params = engine.executeTaskWizard(this, params).getProperties();
			} catch (Exception e) {
				getWindow().showNotification("Could not complete this task", "", Notification.TYPE_ERROR_MESSAGE);
				// TODO Exception Handing
				e.printStackTrace();
				return;
			}
			// Start the next task (if it exists) with input variables from previous task
			int index = steps.indexOf(currentStep);
			if ((index + 1) < steps.size()) {
				nextPage = (PageFlowPage) steps.get(index + 1);
				nextPage.setOnStartState(params);
			}
		}
	}
	
	public boolean currentStepIsLastStep()
	{
		return isLastStep(currentStep);
	}

	@Override
	public void registerForPageFlowPageChanged(
			IPageFlowPageChangedListener listener) {
		pageFlowPageChangedListenerCache.add(listener);
	}

	@Override
	public void unregisterForPageFlowPageChanged(
			IPageFlowPageChangedListener listener) {
		pageFlowPageChangedListenerCache.remove(listener);
	}

	@Override
	public void enablePageFlowPageChangedEventHandling(boolean enable) {
		this.processPageFlowPageChangedEvents = enable;
	}

	@Override
	public void fireOnPageFlowChanged(PageFlowPageChangedEvent event) {
		for (IPageFlowPageChangedListener listener : pageFlowPageChangedListenerCache) {
			listener.onPageChanged(event);
		}
	}

	@Override
	public void setNextEnabled(boolean isEnabled) {
		getNextButton().setEnabled(isEnabled);
		nextButtonBlocked = !isEnabled;
	}

	@Override
	public boolean isNextEnabled() {
		return !nextButtonBlocked;
	}

	@Override
	public void setBackEnabled(boolean isEnabled) {
		getBackButton().setEnabled(isEnabled);
		backButtonBlocked = !isEnabled;
	}

	@Override
	public boolean isBackEnabled() {
		return !backButtonBlocked;
	}

	public void onPagesChanged() {
		//Remove steps
		for (WizardStep step_ : steps)
		{
			removeStep(step_);
		}
		
		//Add pages from path
		if (session.getPages() != null) {
			for (PageFlowPage page : session.getPages()) {
				page.initialize(session.getConXEntityManagerfactory(), 
							    session.getJTAGlobalTransactionManager(), 
						        (IPageFlowPageChangedEventHandler)this,
						        (ITaskWizard)this);
				addStep(page);
			}
		}	
	}
}
