package com.conx.logistics.kernel.ui.common.mvp;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.ui.menu.app.AppMenuEntry;
import com.conx.logistics.kernel.ui.service.contribution.IApplicationViewContribution;

public interface MainEventBus extends EventBus {

  @Event(handlers = { MainPresenter.class })
  public void start(MainMVPApplication app);

  @Event(handlers = { MainPresenter.class })
  public void updateAppContributions(AppMenuEntry[] appMenuEntries);
  
  @Event(handlers = { MainPresenter.class })
  public void addAppContribution(AppMenuEntry appMenuEntry);  
  
  @Event(handlers = { MainPresenter.class })
  public void removeAppContribution(AppMenuEntry appMenuEntry);   
  
  @Event(handlers = { MainPresenter.class })
  public void openModule(Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass);
  
  
  @Event(handlers = { MainPresenter.class })
  public void openApplication(Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass,String name,String iconPath,boolean closable);
}
