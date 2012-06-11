package com.conx.logistics.kernel.system.ui.contribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.FactoryPresenter;

import com.conx.logistics.kernel.system.ui.ModuleEditorPresenter;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class ModuleEditorViewContributionImpl implements IViewContribution {
	
	protected final static String VIEWCODE = "KERNEL.SYSTEM.MODULES.BASICVIEW";

	protected Logger logger = LoggerFactory.getLogger(ModuleEditorViewContributionImpl.class);

	private Component view;

	private Form userForm;

	public String getIcon() {
		return "icons/application_view_columns.png";
	}

	public String getName() {
		return "User Profile Basic View";
	}

	public Component getView(Application application) {
		if (view == null) {
			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.setMargin(true);

		    this.userForm = new Form();
		    User u = new User();
		    u.setUserName("newuser");
		    u.setFirstName("First name");
		    u.setLastName("Last name");
		    BeanItem<User> beanItem = new BeanItem<User>(u);
		    this.userForm.setItemDataSource(beanItem);

			verticalLayout.addComponent(this.userForm);
			view = verticalLayout;

			/*
			synchronized (this) {
				if (personManager != null) {
					refreshTable();
				}
			}
			*/

		}
		return view;
	}

	@Override
	public String getCode() {
		return VIEWCODE;
	}

	@Override
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass(
			Application application) {
		return ModuleEditorPresenter.class;
	}

	
}
