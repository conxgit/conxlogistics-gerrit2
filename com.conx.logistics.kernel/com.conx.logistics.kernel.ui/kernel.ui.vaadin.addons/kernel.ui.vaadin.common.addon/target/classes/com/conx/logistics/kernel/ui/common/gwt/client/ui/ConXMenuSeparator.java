package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;

public class ConXMenuSeparator extends Embedded {
	private static final long serialVersionUID = 7253491502117068821L;

	public ConXMenuSeparator() {
		setIcon(new ThemeResource("icons/conx/conx-header-separator.png"));
		setHeight("16px");
		setWidth("1px");
		addStyleName("conx-header-separator");
	}
}
