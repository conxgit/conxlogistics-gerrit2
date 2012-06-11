package com.conx.logistics.kernel.ui.manager.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.ui.service.IUIContributionManager;
import com.conx.logistics.kernel.ui.service.contribution.IActionContribution;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;
import com.vaadin.ui.Component;

public class UIContributionManagerImpl implements IUIContributionManager {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	

	private final Map<String,IApplicationViewContribution> appContributions = Collections
			.synchronizedMap(new HashMap<String,IApplicationViewContribution>());	
	private final Map<String,IViewContribution> viewContributions = Collections
			.synchronizedMap(new HashMap<String,IViewContribution>());
	private final Map<String,IActionContribution> actionContributions = Collections
			.synchronizedMap(new HashMap<String,IActionContribution>());
	
	private volatile boolean initialized = false;

	private IApplicationDAOService applicationDAOService;

	public void bindViewContribution(IViewContribution viewContribution,
			Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("bindViewContribution("+code+")");
			viewContributions.put(code,viewContribution);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("bindViewContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindViewContribution(IViewContribution viewContribution,
			Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("unbindViewContribution("+code+")");
			viewContributions.remove(code);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("unbindViewContribution has no code associated with it. Deregistration failed.");
		}
	}

	public void bindActionContribution(IActionContribution actionContribution,
			Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("bindActionContribution("+code+")");
			actionContributions.put(code,actionContribution);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("bindActionContribution has no code associated with it. Registration failed.");
		}
	}


	public void unbindActionContribution(
			IActionContribution actionContribution, Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("unbindActionContribution("+code+")");
			actionContributions.remove(code);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("unbindActionContribution has no code associated with it. Deregistration failed.");
		}
	}

	public void bindApplicationContribution(IApplicationViewContribution appContribution,
			Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("bindApplicationContribution("+code+")");
			appContributions.put(code,appContribution);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("bindApplicationContribution has no code associated with it. Registration failed.");
		}
	}


	public void unbindApplicationContribution(
			IApplicationViewContribution appContribution, Map properties) {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("unbindApplicationContribution("+code+")");
			appContributions.remove(code);
			if (initialized) {
				/*
				tabSheet.addTab(viewContribution.getView(this), viewContribution
						.getName(), new ThemeResource(viewContribution.getIcon()));
				*/
			}
		}
		else
		{
			logger.error("unbindApplicationContribution has no code associated with it. Deregistration failed.");
		}
	}	
	
	public void bindApplicationDAOService(
			IApplicationDAOService applicationDAOService, Map properties) {
		logger.debug("bindApplicationDAOService()");
		this.applicationDAOService = applicationDAOService;
		try {
			//this.applicationDAOService.provideControlPanelApplication();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unbindApplicationDAOService(
			IApplicationDAOService applicationDAOService, Map properties) {
		logger.debug("unbindApplicationDAOService()");
		//this.applicationDAOService.provideControlPanelApplication();
		this.applicationDAOService = null;
	}	

	@Override
	public IApplicationViewContribution getApplicationContributionByCode(
			Application application, String code) {
		this.applicationDAOService.provideControlPanelApplication();
		IApplicationViewContribution ac = (IApplicationViewContribution)appContributions.get(code);
		return ac;
	}
	
	@Override
	public IViewContribution getViewContributionByCode(Application application,String code) {
		IViewContribution vc = (IViewContribution)viewContributions.get(code);
		return vc;
	}

	@Override
	public IApplicationViewContribution[] getCurrentApplicationContributions() {
		return appContributions.values().toArray(new IApplicationViewContribution[]{});
	}
}
