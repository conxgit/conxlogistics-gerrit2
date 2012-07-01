package com.conx.logistics.kernel.ui.common.mvp.view.feature;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.ui.common.ActiveLink;
import com.conx.logistics.kernel.ui.common.mvp.LaunchableViewEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.service.IUIContributionManager;
import com.conx.logistics.kernel.ui.service.contribution.IActionContribution;
import com.conx.logistics.kernel.ui.service.contribution.ITaskActionContribution;
import com.conx.logistics.kernel.ui.service.contribution.IViewContribution;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class FeatureView extends HorizontalLayout implements IFeatureView {

	private static final String MSG_SHOW_SRC = "View Source";

	private VerticalLayout left;

	private HorizontalLayout controls;

	private Label title = new Label("", Label.CONTENT_XHTML);

	private ActiveLink showSrc;

	private HashMap<Feature, Component> exampleCache = new HashMap<Feature, Component>();

	private Feature currentFeature;

	private Window srcWindow;

	private MainMVPApplication app;

	private IPresenter<?, ? extends EventBus> viewPresenter;

	public FeatureView(MainMVPApplication app,IPresenter<?, ? extends EventBus> viewPresenter) {
		this.app = app;
		this.viewPresenter = viewPresenter;

		setWidth("100%");
		setMargin(true);
		setSpacing(true);
		setStyleName("sample-view");

		left = new VerticalLayout();
		left.setWidth("100%");
		left.setSpacing(true);
		left.setMargin(false);
		addComponent(left);
		setExpandRatio(left, 1);

		controls = new HorizontalLayout();
		controls.setWidth("100%");
		controls.setStyleName("feature-controls");

		title.setStyleName("title");
//		controls.addComponent(title);
//		controls.setExpandRatio(title, 1);
	}

	public void setFeature(Feature feature) {
		if (feature != currentFeature) {
			if (Validator.isNotNull(feature.getCode())) {
				currentFeature = feature;
				left.removeAllComponents();

//				left.addComponent(controls);
				title.setValue("<span>" + feature.getName() + "</span>");
				//if (feature.getSinceVersion().isNew()) {
				//	title.addStyleName("new");
				//} else {
				title.addStyleName("new");
				//}

				Component componentFeature = getComponentFor(feature);

				left.addComponent(componentFeature);

				// FIXME Hack for showing Theme demos full size					setSizeFull();
				setSizeFull();
				left.setSizeFull();
				left.setExpandRatio(componentFeature, 1);
				/*
				if (feature.getName().endsWith("Theme")) {
					setSizeFull();
					left.setSizeFull();
					left.setExpandRatio(componentFeature, 1);
				} else {
					setHeight("-1px");
					left.setHeight("-1px");
				}
				*/
				//Class<? extends Feature>[] features = feature.getRelatedFeatures();
			}
		}
	}

	private Component getComponentFor(Feature f) {
		Component ex = null;
		if (f.isTaskFeature())
		{
			IUIContributionManager cm = this.app.getUiContributionManager();
			String viewCode = f.getExternalCode();
			String processId = f.getCode();

			ITaskActionContribution ac = (ITaskActionContribution)app.getActionContributionByCode(viewCode);	
			
			if (   viewCode != null 
				&& processId != null
				&& ac != null)
			{
				Map<String,Object> props = new HashMap<String,Object>();
				props.put("userId","john");
				props.put("processId",processId);
				props.put("onCompletionFeature",f.getOnCompletionFeature());
				props.put("onCompletionViewPresenter",this.viewPresenter);
				
				try
				{
					ex = ac.execute(this.app, props);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else if (Validator.isNotNull(f.getCode())) {
			ex = exampleCache.get(f);
			if (ex == null) {
				IUIContributionManager cm = this.app.getUiContributionManager();
				String viewCode = f.getCode();
				IViewContribution vc = app.getViewContributionByCode(viewCode);
				if (vc != null)
				{
					try {
						IPresenterFactory pf = this.app.getPresenterFactory();
						IPresenter<?, ? extends EventBus> viewPresenter = pf.createPresenter(vc.getPresenterClass(this.app));					
						((LaunchableViewEventBus)viewPresenter.getEventBus()).launch(this.app);
						
						ex = (Component)viewPresenter.getView();
						ex.setSizeFull();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					exampleCache.put(f,ex);
				}
			}

		}
		return ex;
	}

}
