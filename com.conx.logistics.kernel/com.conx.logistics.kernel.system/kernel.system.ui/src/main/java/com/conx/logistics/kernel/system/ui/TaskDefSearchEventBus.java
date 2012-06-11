package com.conx.logistics.kernel.system.ui;

import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.ViewFactoryException;

import com.conx.logistics.kernel.ui.common.mvp.LaunchableViewEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.MainPresenter;
import com.conx.logistics.kernel.ui.common.ui.feature.Feature;

public interface TaskDefSearchEventBus extends LaunchableViewEventBus {
	@Event(handlers = { TaskDefSearchPresenter.class })
	public void launch(MainMVPApplication app);

	@Event(handlers = { TaskDefSearchPresenter.class })
	public void openFeatureView(Feature feature);

	@Event(handlers = { TaskDefSearchPresenter.class })
	public void createTaskDef() throws ViewFactoryException;

	@Event(handlers = { TaskDefSearchPresenter.class })
	public void removeTaskDef();

	@Event(handlers = { TaskDefSearchPresenter.class })
	public void saveTaskDef();
	
	@Event(handlers = { TaskDefSearchPresenter.class })
	public void cancelTaskDef();	
	
	@Event(handlers = { TaskDefSearchPresenter.class })
	public void editTaskDef();	
}
