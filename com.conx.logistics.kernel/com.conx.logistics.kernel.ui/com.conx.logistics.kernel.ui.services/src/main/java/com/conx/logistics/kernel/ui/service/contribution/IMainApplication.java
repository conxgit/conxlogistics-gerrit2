package com.conx.logistics.kernel.ui.service.contribution;

import org.vaadin.mvp.presenter.IPresenterFactory;

import com.conx.logistics.kernel.ui.service.IUIContributionManager;

public interface IMainApplication {
	public IUIContributionManager getUiContributionManager();
	public IPresenterFactory getPresenterFactory();
	public IViewContribution getViewContributionByCode(String code);
	public IApplicationViewContribution getApplicationContributionByCode(String code);
}
