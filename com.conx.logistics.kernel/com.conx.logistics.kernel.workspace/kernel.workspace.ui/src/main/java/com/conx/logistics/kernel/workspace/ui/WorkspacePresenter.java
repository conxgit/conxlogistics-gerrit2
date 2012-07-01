package com.conx.logistics.kernel.workspace.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.MainPresenter;
import com.conx.logistics.kernel.ui.common.mvp.view.feature.FeatureView;
import com.conx.logistics.kernel.workspace.ui.navigation.WorkspaceNavigationPresenter;
import com.conx.logistics.kernel.workspace.ui.navigation.view.IWorkspaceNavigationView;
import com.conx.logistics.kernel.workspace.ui.view.IWorkspaceView;
import com.conx.logistics.kernel.workspace.ui.view.WorkspaceView;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Presenter(view = WorkspaceView.class)
public class WorkspacePresenter extends BasePresenter<IWorkspaceView, WorkspaceEventBus>{

  private MainMVPApplication application;
  private FeatureView fv;
  
  private WorkspaceNavigationPresenter navPresenter;
  
  private IPresenter<?, ?extends EventBus> contentPresenter;
  

  public void onStart(MainMVPApplication app) {
    // keep a reference to the application instance
    this.application = app;
    
    // set the applications main windows (the view)
    //this.application.setMainWindow((Window) this.view);
    
    // load the nav presenter
    IPresenterFactory pf = application.getPresenterFactory();
    this.navPresenter = (WorkspaceNavigationPresenter) pf.createPresenter(WorkspaceNavigationPresenter.class);
    IWorkspaceNavigationView navView = this.navPresenter.getView();
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
