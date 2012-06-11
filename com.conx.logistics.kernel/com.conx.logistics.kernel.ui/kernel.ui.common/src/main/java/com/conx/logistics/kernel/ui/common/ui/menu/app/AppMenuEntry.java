package com.conx.logistics.kernel.ui.common.ui.menu.app;

import com.conx.logistics.kernel.ui.common.ui.menu.MenuEntry;
import com.vaadin.ui.Component;

public class AppMenuEntry extends MenuEntry {
	private Component launchableAppComponent;
	
	public AppMenuEntry(String caption, String iconPath, Component launchableAppComponent)
	{
		super(caption,iconPath);
		this.launchableAppComponent = launchableAppComponent;
	}
	
	public AppMenuEntry(String code, String caption, String iconPath, Component launchableAppComponent)
	{
		super(code,caption,iconPath);
		this.launchableAppComponent = launchableAppComponent;
	}
	

	public Component getLaunchableAppComponent() {
		return launchableAppComponent;
	}
}
