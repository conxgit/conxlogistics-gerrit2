package com.conx.logistics.kernel.system.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.StartableApplicationEventBus;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.ui.Field.ValueChangeEvent;

public interface ControlPanelEventBus extends StartableApplicationEventBus {
  @Event(handlers = { ControlPanelPresenter.class })
  public void start(MainMVPApplication app);
	  
  @Event(handlers = { ControlPanelPresenter.class })
  public void selectMenu(ValueChangeEvent event);

  @Event(handlers = { ControlPanelPresenter.class })
  public void openModule(Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass);
  
  @Event(handlers = { ControlPanelPresenter.class })
  public void openFeatureView(Feature feature);  
}
