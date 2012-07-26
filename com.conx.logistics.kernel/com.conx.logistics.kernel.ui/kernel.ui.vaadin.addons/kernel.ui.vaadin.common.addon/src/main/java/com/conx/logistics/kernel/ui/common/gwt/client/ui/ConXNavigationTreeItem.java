package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeTable;

public class ConXNavigationTreeItem extends HorizontalLayout {
	private static final long serialVersionUID = -3353990058663004665L;
	
	private Label label;
	private TreeTable parent;
	private Embedded icon;
	private int id;

	public ConXNavigationTreeItem(String caption, String url, TreeTable tree, int itemId) {
		addStyleName("conx-navigation-tree-item");
		setHeight("16px");
		setWidth("60px");
		parent = tree;
		this.id = itemId;
		icon = new Embedded();
		icon.setIcon(new ThemeResource(url));
		icon.setHeight("16px");
		icon.setWidth("16px");
		label = new Label();
		label.addStyleName("conx-navigation-tree-item-text");
		label.setCaption(caption);
		label.setHeight("16px");
		label.setWidth(null);
		addComponent(icon);
		addComponent(label);
		setExpandRatio(label, 1.0f);
		addListener(new LayoutClickListener() {
			private static final long serialVersionUID = -7086421156894810731L;

			public void layoutClick(LayoutClickEvent event) {
				parent.select(id);
			}
		});
	}
	
	public int getId() {
		return id;
	}
}
