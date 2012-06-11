package com.conx.logistics.kernel.system.ui;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.common.mvp.LaunchableViewEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.MainPresenter;
import com.vaadin.ui.Window;

public interface ModuleEditorEventBus extends LaunchableViewEventBus {
  @Event(handlers = { ModuleEditorPresenter.class })
  public void launch(MainMVPApplication app);	

  @Event(handlers = { ModuleEditorPresenter.class })
  public void createUser();

  @Event(handlers = { ModuleEditorPresenter.class })
  public void removeUser();

  @Event(handlers = { MainPresenter.class })
  public void showDialog(Window dialog);

  @Event(handlers = { ModuleEditorPresenter.class })
  public void saveUser();
  
  @Event(handlers = { ModuleEditorPresenter.class })
  public void cancelEditUser();

}
