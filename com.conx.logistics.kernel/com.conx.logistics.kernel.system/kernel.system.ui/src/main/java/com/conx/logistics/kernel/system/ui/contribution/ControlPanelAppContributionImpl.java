package com.conx.logistics.kernel.system.ui.contribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.FactoryPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.uibinder.UiBinderException;

import com.conx.logistics.kernel.system.ui.ControlPanelEventBus;
import com.conx.logistics.kernel.system.ui.ControlPanelPresenter;
import com.conx.logistics.kernel.ui.common.mvp.MainEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class ControlPanelAppContributionImpl implements IApplicationViewContribution {
	
	public final static String VIEWCODE = "KERNEL.SYSTEM.CONTROLPANEL";

	protected Logger logger = LoggerFactory.getLogger(ControlPanelAppContributionImpl.class);

	private Component view;

	private Form userForm;

	public String getIcon() {
		return "custom/img/admin.png";
	}

	public String getName() {
		return "Control Panel";
	}


	@Override
	public String getCode() {
		return VIEWCODE;
	}

	@Override
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass(
			Application application) {
		return ControlPanelPresenter.class;
	}

	@Override
	public Component getApplicationComponent(Application app) throws UiBinderException {
		IPresenterFactory pf = ((MainMVPApplication)app).getPresenterFactory();
		ControlPanelPresenter cpp =  (ControlPanelPresenter)pf.createPresenter(ControlPanelPresenter.class);
		ControlPanelEventBus eb = (ControlPanelEventBus) cpp.getEventBus();
		eb.start((MainMVPApplication)app);
		return (Component)cpp.getView();
	}
}
