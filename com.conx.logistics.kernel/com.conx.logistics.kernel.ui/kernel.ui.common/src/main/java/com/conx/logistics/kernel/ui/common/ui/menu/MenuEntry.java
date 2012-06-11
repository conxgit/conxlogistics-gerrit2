package com.conx.logistics.kernel.ui.common.ui.menu;

public abstract class MenuEntry {

	protected String code;
	protected String caption;
	protected String iconPath;
	
	public MenuEntry() {
	}

	public MenuEntry(String caption, String iconPath) {
		this.caption = caption;
		this.iconPath = iconPath;
	}
	
	public MenuEntry(String code, String caption, String iconPath) {
		this(caption,iconPath);
		this.code = code;
	}	

	public String getCaption() {
		return caption;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String toString() {
		return caption;
	}

	public String getCode() {
		return code;
	}
}
