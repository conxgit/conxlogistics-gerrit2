package com.conx.logistics.kernel.ui.common.mvp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.PresenterFactory;
import org.vaadin.mvp.uibinder.UiBinderException;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.ui.common.data.container.EntityTypeContainerFactory;
import com.conx.logistics.kernel.ui.common.ui.menu.app.AppMenuEntry;
import com.conx.logistics.kernel.ui.service.IUIContributionManager;
import com.conx.logistics.kernel.ui.service.contribution.IActionContribution;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;

public class MainMVPApplication extends Application implements IMainApplication {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/** Per application (session) event bus manager */
	private EventBusManager ebm = new EventBusManager();
	/** Per application presenter factory */
	// create an instance of a default presenter factory
	private PresenterFactory presenterFactory = new PresenterFactory(ebm, getLocale());

	/** Main presenter */
	private IPresenter<?, ? extends EventBus> mainPresenter;
	private IUIContributionManager uiContributionManager;

	private IApplicationDAOService applicationDAOService;
	
	private boolean appServiceInititialized = false;

	private PlatformTransactionManager kernelSystemTransManager;

	private EntityManagerFactory kernelSystemEntityManagerFactory;	
	
	private IPageFlowManager pageFlowEngine;
	
	/** UI contributions management*/
	private final Map<String,IApplicationViewContribution> appContributions = Collections
			.synchronizedMap(new HashMap<String,IApplicationViewContribution>());
	private final Map<String,IViewContribution> viewContributions = Collections
			.synchronizedMap(new HashMap<String,IViewContribution>());	
	private final Map<String,IActionContribution> actionContributions = Collections
			.synchronizedMap(new HashMap<String,IActionContribution>());	
	
	private volatile boolean initialized = false;

	private MainEventBus mainEventBus;

	private EntityTypeContainerFactory entityTypeContainerFactory;


	@Override
	public void init() {
		try 
		{
			setTheme("sampler-reindeer");

			// request an instance of MainPresenter
			mainPresenter = this.presenterFactory.createPresenter(MainPresenter.class);
			mainEventBus = (MainEventBus) mainPresenter.getEventBus();
			mainEventBus.start(this);
			
			//By default, add workspace
			IApplicationViewContribution ac = appContributions.get("KERNEL.WORKSPACE");
			if (Validator.isNotNull(ac))
			{
				Class<? extends BasePresenter<?, ? extends EventBus>> acClass = ac.getPresenterClass(this);
				//IPresenter acPresenter = this.presenterFactory.createPresenter(acClass);
				//StartableApplicationEventBus acSB = (StartableApplicationEventBus)acPresenter.getEventBus();
				//acSB.start(this);
				mainEventBus.openApplication(acClass,ac.getName(),ac.getIcon(),false);
			}
			
			initialized = true;
			//AppMenuEntry[] entries = createAppMenuEntries();
			//mainEventBus.updateAppContributions(entries);		
			

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

	}

	public IPresenterFactory getPresenterFactory() {
		return this.presenterFactory;
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
			logger.error("bindViewContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindActionContribution(IActionContribution actionContribution,
			Map properties) {
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
	
	public void bindApplicationContribution(IApplicationViewContribution appContribution,
			Map properties) throws UiBinderException {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("bindApplicationContribution("+code+")");
			appContributions.put(code,appContribution);
			if (initialized) {
				AppMenuEntry entry = createAppMenuEntry(appContribution);
				mainEventBus.addAppContribution(entry);
			}
		}
		else
		{
			logger.error("bindApplicationContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindApplicationContribution(
			IApplicationViewContribution appContribution, Map properties) throws UiBinderException {
		String code = (String)properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code))
		{
			logger.info("unbindApplicationContribution("+code+")");
			appContributions.remove(code);
			if (initialized) {
				AppMenuEntry entry = createAppMenuEntry(appContribution);
				mainEventBus.removeAppContribution(entry);
			}
		}
		else
		{
			logger.error("unbindApplicationContribution has no code associated with it. Deregistration failed.");
		}
	}	
	
	private AppMenuEntry createAppMenuEntry(IApplicationViewContribution avc) throws UiBinderException {
		return new AppMenuEntry(avc.getCode(),avc.getName(), avc.getIcon(), avc.getApplicationComponent(this));
	}
	
	public AppMenuEntry[] createAppMenuEntries() throws UiBinderException {
		if (appContributions.isEmpty())
			return new AppMenuEntry[]{};
		else
		{
			ArrayList<AppMenuEntry> entries = new ArrayList<AppMenuEntry>();
			for (IApplicationViewContribution ac : appContributions.values())
			{
				entries.add(new AppMenuEntry(ac.getCode(),ac.getName(), ac.getIcon(), ac.getApplicationComponent(this)));
			}
			return entries.toArray(new AppMenuEntry[]{});
		}
	}

	
	public void bindApplicationDAOService(
			IApplicationDAOService applicationDAOService, Map properties) {
		logger.debug("bindApplicationDAOService()");
		this.applicationDAOService = applicationDAOService;
		if (!appServiceInititialized)
			initAppService();
	}

	private void initAppService() {
		if (Validator.isNotNull(this.kernelSystemTransManager) && Validator.isNotNull(this.applicationDAOService))
		{
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			// explicitly setting the transaction name is something that can only be done programmatically
			def.setName("sendFlightScheduleUpdate");
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = this.kernelSystemTransManager.getTransaction(def);			
			try {
				this.applicationDAOService.provideControlPanelApplication();
				this.kernelSystemTransManager.commit(status);	
			} 
			catch (Exception e) 
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error(stacktrace);
				
				this.kernelSystemTransManager.rollback(status);
			}
			appServiceInititialized = true;
		}
	}

	public void unbindApplicationDAOService(
			IApplicationDAOService applicationDAOService, Map properties) {
		logger.debug("unbindApplicationDAOService()");
		this.applicationDAOService = null;
		appServiceInititialized = false;
	}
	
	public void bindKernelSystemTransManager(
			PlatformTransactionManager kernelSystemTransManager, Map properties) {
		logger.debug("bindKernelSystemTransManager()");
		this.kernelSystemTransManager = kernelSystemTransManager;
		if (!appServiceInititialized)
			initAppService();		
	}
	
	
	public void unbindPageFlowEngine(
			IPageFlowManager pageflowEngine, Map properties) {
		logger.debug("unbindPageFlowEngine()");
		this.pageFlowEngine  = null;
	}
	
	public void bindPageFlowEngine(
			IPageFlowManager pageflowEngine, Map properties) {
		logger.debug("bindPageFlowEngine()");
		this.pageFlowEngine  = pageflowEngine;
		this.pageFlowEngine.setMainApplication(this);
	}

	public void unbindKernelSystemTransManager(
			PlatformTransactionManager kernelSystemTransManager, Map properties) {
		logger.debug("unbindKernelSystemTransManager()");
		this.kernelSystemTransManager = null;
		appServiceInititialized = false;
	}	
	
	public void bindKernelSystemEntityManagerFactory(
			EntityManagerFactory kernelSystemEntityManagerFactory, Map properties) {
		logger.debug("bindKernelSystemEntityManagerFactory()");
		this.entityTypeContainerFactory  = new EntityTypeContainerFactory(kernelSystemEntityManagerFactory.createEntityManager());
		this.kernelSystemEntityManagerFactory = kernelSystemEntityManagerFactory;
	}

	public void unbindKernelSystemEntityManagerFactory(
			PlatformTransactionManager kernelSystemTransManager, Map properties) {
		logger.debug("unbindKernelSystemEntityManagerFactory()");
		this.entityTypeContainerFactory  = null;
		this.kernelSystemEntityManagerFactory = null;
	}		


	public IUIContributionManager getUiContributionManager() {
		return uiContributionManager;
	}	
	
	public EntityManagerFactory getKernelSystemEntityManagerFactory() {
		return kernelSystemEntityManagerFactory;
	}
	

	public EntityTypeContainerFactory getEntityTypeContainerFactory() {
		return entityTypeContainerFactory;
	}

	public EventBusManager getEeventBusManager() {
		return ebm;
	}
	
	public EventBus createEventBuss(Class eventBusClass, IPresenter presenter)
	{
		return ebm.register(eventBusClass, presenter);
	}
	
	public IViewContribution getViewContributionByCode(String code)
	{
		return viewContributions.get(code);
	}
	
	public IActionContribution getActionContributionByCode(String code)
	{
		return actionContributions.get(code);
	}
	
	public IApplicationViewContribution getApplicationContributionByCode(String code)
	{
		return appContributions.get(code);
	}	
}
