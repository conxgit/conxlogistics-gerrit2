package com.conx.logistics.app.whse.ui.contribution.asn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

import com.conx.logistics.app.whse.ui.asn.ASNSearchPresenter;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;

public class ASNSearchViewContributionImpl implements IViewContribution {
	
	protected final static String VIEWCODE = "RCVNG.ASN.SEARCH";

	protected Logger logger = LoggerFactory.getLogger(ASNSearchViewContributionImpl.class);

	private Component view;

	private Form userForm;

	@Override
	public String getIcon() {
		return "icons/application_view_columns.png";
	}

	@Override
	public String getName() {
		return "ASN's";
	}

	@Override
	public String getCode() {
		return VIEWCODE;
	}

	@Override
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass(
			Application application) {
		return ASNSearchPresenter.class;
	}

	
}
