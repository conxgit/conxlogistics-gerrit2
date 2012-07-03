package com.conx.logistics.app.whse.ui.asn;

import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.ViewFactoryException;

import com.conx.logistics.kernel.ui.common.mvp.LaunchableViewEventBus;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.MainPresenter;
import com.conx.logistics.kernel.ui.common.ui.feature.Feature;

public interface ASNSearchEventBus extends LaunchableViewEventBus {
	@Event(handlers = { ASNSearchPresenter.class })
	public void launch(MainMVPApplication app);

	@Event(handlers = { ASNSearchPresenter.class })
	public void openFeatureView(Feature feature);

	@Event(handlers = { ASNSearchPresenter.class })
	public void createTaskDef() throws ViewFactoryException;

	@Event(handlers = { ASNSearchPresenter.class })
	public void removeTaskDef();

	@Event(handlers = { ASNSearchPresenter.class })
	public void saveTaskDef();
	
	@Event(handlers = { ASNSearchPresenter.class })
	public void cancelTaskDef();	
	
	@Event(handlers = { ASNSearchPresenter.class })
	public void editTaskDef();	
	
	@Event(handlers = { ASNSearchPresenter.class })
	public void createASN();		
}
