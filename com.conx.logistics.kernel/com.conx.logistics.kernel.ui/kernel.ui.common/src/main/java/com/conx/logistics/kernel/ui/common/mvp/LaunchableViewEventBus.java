package com.conx.logistics.kernel.ui.common.mvp;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;

public interface LaunchableViewEventBus extends EventBus {
  @Event(handlers = { MainPresenter.class })
  public void launch(MainMVPApplication app);
}
