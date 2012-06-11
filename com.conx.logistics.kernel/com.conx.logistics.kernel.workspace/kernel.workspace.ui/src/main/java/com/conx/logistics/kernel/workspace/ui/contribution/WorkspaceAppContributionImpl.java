package com.conx.logistics.kernel.workspace.ui.contribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.FactoryPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.conx.logistics.kernel.workspace.ui.WorkspacePresenter;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class WorkspaceAppContributionImpl implements IApplicationViewContribution {
	
	public final static String VIEWCODE = "KERNEL.WORKSPACE";

	protected Logger logger = LoggerFactory.getLogger(WorkspaceAppContributionImpl.class);

	private Component view;

	private Form userForm;

	public String getIcon() {
		return "custom/img/home.png";
	}

	public String getName() {
		return "My Workspace";
	}

	@Override
	public String getCode() {
		return VIEWCODE;
	}

	@Override
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass(
			Application application) {
		return WorkspacePresenter.class;
	}

	@Override
	public Component getApplicationComponent(Application app) {
		IPresenterFactory pf = ((MainMVPApplication)app).getPresenterFactory();
		WorkspacePresenter wsp =  (WorkspacePresenter)pf.createPresenter(WorkspacePresenter.class);
		return (Component)wsp.getView();
	}
}
