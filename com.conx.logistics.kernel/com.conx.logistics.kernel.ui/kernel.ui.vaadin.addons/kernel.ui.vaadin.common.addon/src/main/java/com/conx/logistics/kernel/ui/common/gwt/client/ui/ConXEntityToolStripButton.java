package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;

public class ConXEntityToolStripButton extends Button {
	private static final long serialVersionUID = -6850572740737479916L;

	public ConXEntityToolStripButton(String iconUrl) {
		setIcon(new ThemeResource(iconUrl));
		setStyleName("conx-entity-toolstrip-button");
		setHeight("28px");
	}
}
