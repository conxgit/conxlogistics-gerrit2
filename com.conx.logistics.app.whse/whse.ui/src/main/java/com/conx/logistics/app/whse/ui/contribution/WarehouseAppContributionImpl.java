package com.conx.logistics.app.whse.ui.contribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.FactoryPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.uibinder.UiBinderException;


import com.conx.logistics.app.whse.ui.WarehouseEventBus;
import com.conx.logistics.app.whse.ui.WarehousePresenter;
import com.conx.logistics.kernel.ui.common.mvp.MainEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class WarehouseAppContributionImpl implements IApplicationViewContribution {
	
	public final static String VIEWCODE = "WHSE";

	protected Logger logger = LoggerFactory.getLogger(WarehouseAppContributionImpl.class);

	private Component view;

	private Form userForm;

	public String getIcon() {
		return "custom/img/download_box.png";
	}

	public String getName() {
		return "Warehouse";
	}


	@Override
	public String getCode() {
		return VIEWCODE;
	}

	@Override
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass(
			Application application) {
		return WarehousePresenter.class;
	}

	@Override
	public Component getApplicationComponent(Application app) throws UiBinderException {
		IPresenterFactory pf = ((MainMVPApplication)app).getPresenterFactory();
		WarehousePresenter cpp =  (WarehousePresenter)pf.createPresenter(WarehousePresenter.class);
		WarehouseEventBus eb = (WarehouseEventBus) cpp.getEventBus();
		eb.start((MainMVPApplication)app);
		return (Component)cpp.getView();
	}
}
