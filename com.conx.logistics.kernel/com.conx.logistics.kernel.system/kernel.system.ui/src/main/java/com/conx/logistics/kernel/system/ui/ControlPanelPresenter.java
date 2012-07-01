package com.conx.logistics.kernel.system.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.system.ui.navigation.ControlPanelNavigationEventBus;
import com.conx.logistics.kernel.system.ui.navigation.ControlPanelNavigationPresenter;
import com.conx.logistics.kernel.system.ui.navigation.view.IControlPanelNavigationView;
import com.conx.logistics.kernel.system.ui.view.IControlPanelView;
import com.conx.logistics.kernel.system.ui.view.ControlPanelView;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.view.feature.FeatureView;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Presenter(view = ControlPanelView.class)
public class ControlPanelPresenter extends BasePresenter<IControlPanelView, ControlPanelEventBus>{

  private MainMVPApplication application;
  private FeatureView fv;
  
  private ControlPanelNavigationPresenter navPresenter;
  
  private IPresenter<?, ?extends EventBus> contentPresenter;
  

  public void onStart(MainMVPApplication app) {
    // keep a reference to the application instance
    this.application = app;
    
    // set the applications main windows (the view)
    //this.application.setMainWindow((Window) this.view);
    
    // load the nav presenter
    IPresenterFactory pf = application.getPresenterFactory();
    this.navPresenter = (ControlPanelNavigationPresenter) pf.createPresenter(ControlPanelNavigationPresenter.class);
    
    // start nav presenter
    ControlPanelNavigationEventBus cpneb = (ControlPanelNavigationEventBus)application.createEventBuss(ControlPanelNavigationEventBus.class,this.navPresenter);
    cpneb.start(application);
    
    IControlPanelNavigationView navView = this.navPresenter.getView();
	this.view.setNavigation(navView);
    
    //load fv
	fv = new FeatureView(this.application,this);
    this.view.setContent(this.fv);
  }
  
  public void onOpenModule(Class<? extends BasePresenter<?, ? extends EventBus>> presenter) {
    // load the menu presenter
    IPresenterFactory pf = application.getPresenterFactory();
    this.contentPresenter = pf.createPresenter(presenter);
    this.view.setContent((Component) this.contentPresenter.getView());
  }
  
  public void onOpenFeatureView(Feature feature)
  {
	  fv.setFeature(feature);
  }
  
  public void onShowDialog(Window dialog) {
    this.application.getMainWindow().addWindow(dialog);
  }
  
  @Override
  public void bind() {
    VerticalLayout mainLayout = this.view.getMainLayout();
    HorizontalSplitPanel layoutPanel = this.view.getSplitLayout();
    layoutPanel.setStyleName("main-split");
    layoutPanel.setSizeFull();
    mainLayout.setSizeFull();    
    mainLayout.setExpandRatio(layoutPanel, 1.0f);
    layoutPanel.setSplitPosition(200, HorizontalSplitPanel.UNITS_PIXELS);
  }
}
