package com.conx.logistics.kernel.pageflow.engine.ui;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.pageflow.engine.PageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.PageFlowSessionImpl;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.kernel.ui.service.IUIContributionManager;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.Application;
import com.vaadin.ui.Component;

public class TaskWizard extends Wizard implements ITaskWizard {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final long serialVersionUID = 8417208260717324494L;
	
	private PageFlowSessionImpl session;
	private PageFlowEngineImpl engine;

	private Feature onCompletionCompletionFeature;

	private IPresenter<?, ? extends EventBus> onCompletionCompletionViewPresenter;
	
	public TaskWizard(PageFlowSessionImpl session, PageFlowEngineImpl engine, Feature onCompletionCompletionFeature, IPresenter<?, ? extends EventBus> onCompletionCompletionViewPresenter) {
		this.engine = engine;
		this.session = session;
		this.onCompletionCompletionFeature = onCompletionCompletionFeature;
		this.onCompletionCompletionViewPresenter = onCompletionCompletionViewPresenter;
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
		completeCurrentTaskAndAdvanceToNext();
		super.next();
	}
	
	@Override
    public void finish() {
		completeCurrentTaskAndAdvanceToNext();
		
		
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

	private void completeCurrentTaskAndAdvanceToNext() {
		PageFlowPage currentPage, nextPage;
		Map<String, Object> params = null;
		currentPage = (PageFlowPage) currentStep;
		// Complete current task and get input variables for the next task
		try {
			params = currentPage.getOnCompleteState(); // Completes current task with
			params = engine.executeTaskWizard(this, params).getProperties();
		} catch (Exception e) {
			getWindow().showNotification("Could not complete this task");
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
	
	public boolean currentStepIsLastStep()
	{
		return isLastStep(currentStep);
	}
}
