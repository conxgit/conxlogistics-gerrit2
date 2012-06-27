package com.conx.logistics.app.whse.ui.navigation;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;

public interface WarehouseNavigationEventBus extends EventBus {
  @Event(handlers = { WarehouseNavigationPresenter.class })
  public void start(MainMVPApplication app); 
}
