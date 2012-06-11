package com.conx.logistics.kernel.ui.service.contribution;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.FactoryPresenter;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public interface IViewContribution {
	public Class<? extends BasePresenter<?, ? extends EventBus>>  getPresenterClass(Application application);

	public String getIcon();

	public String getName();
	
	public String getCode();
}
