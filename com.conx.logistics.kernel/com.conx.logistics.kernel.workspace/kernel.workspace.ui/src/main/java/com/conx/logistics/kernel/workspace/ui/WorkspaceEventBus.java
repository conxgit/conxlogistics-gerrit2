package com.conx.logistics.kernel.workspace.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.MainPresenter;
import com.conx.logistics.kernel.ui.common.mvp.StartableApplicationEventBus;
import com.conx.logistics.kernel.ui.common.ui.feature.Feature;
import com.vaadin.ui.Field.ValueChangeEvent;

public interface WorkspaceEventBus extends StartableApplicationEventBus {
  @Event(handlers = { WorkspacePresenter.class })
  public void start(MainMVPApplication app);
	  
  @Event(handlers = { WorkspacePresenter.class })
  public void selectMenu(ValueChangeEvent event);

  @Event(handlers = { WorkspacePresenter.class })
  public void openModule(Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass);
  
  @Event(handlers = { WorkspacePresenter.class })
  public void openFeatureView(Feature feature);  
}
