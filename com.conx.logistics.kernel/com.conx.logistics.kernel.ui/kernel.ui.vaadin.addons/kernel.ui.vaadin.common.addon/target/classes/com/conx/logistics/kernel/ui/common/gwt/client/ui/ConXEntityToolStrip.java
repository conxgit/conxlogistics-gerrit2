package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

public class ConXEntityToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = -8851084906476092083L;
	
	private HorizontalLayout leftButtonStrip;
	private ConXEntityToolStripButton newButton;
	private ConXEntityToolStripButton editButton;
	private ConXEntityToolStripButton saveButton;
	private ConXEntityToolStripButton deleteButton;
	
	public ConXEntityToolStrip() {
		setWidth("100%");
		setHeight("40px");
		setStyleName("conx-entity-toolstrip");
		initialize();
	}

	private void initialize() {
		leftButtonStrip = new HorizontalLayout();
		leftButtonStrip.setHeight("28px");
		leftButtonStrip.setSpacing(true);
		
		newButton = new ConXEntityToolStripButton("toolstrip/img/new.png");
		newButton.setStyleName("conx-entity-toolstrip-button");
		newButton.setHeight("28px");
		leftButtonStrip.addComponent(newButton);
		
		editButton = new ConXEntityToolStripButton("toolstrip/img/edit.png");
		editButton.setIcon(new ThemeResource("toolstrip/img/edit.png"));
		editButton.setStyleName("conx-entity-toolstrip-button");
		editButton.setHeight("28px");
		leftButtonStrip.addComponent(editButton);
		
		saveButton = new ConXEntityToolStripButton("toolstrip/img/save.png");
		saveButton.setIcon(new ThemeResource("toolstrip/img/save.png"));
		saveButton.setStyleName("conx-entity-toolstrip-button");
		saveButton.setHeight("28px");
		leftButtonStrip.addComponent(saveButton);
		
		deleteButton = new ConXEntityToolStripButton("toolstrip/img/delete.png");
		deleteButton.setIcon(new ThemeResource("toolstrip/img/delete.png"));
		deleteButton.setStyleName("conx-entity-toolstrip-button");
		deleteButton.setHeight("28px");
		leftButtonStrip.addComponent(deleteButton);
		
		addComponent(leftButtonStrip);
		setComponentAlignment(leftButtonStrip, Alignment.MIDDLE_LEFT);
	}
}
