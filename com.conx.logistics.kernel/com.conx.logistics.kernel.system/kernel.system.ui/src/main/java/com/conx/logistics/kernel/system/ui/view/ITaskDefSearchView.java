package com.conx.logistics.kernel.system.ui.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public interface ITaskDefSearchView {

	public abstract void setDetailComponent(Component component);

	public abstract Table getSearchGrid();
	
	public abstract VerticalSplitPanel getSplitLayout();

	public abstract VerticalLayout getMainLayout();

	public abstract Button getCreateTaskDef();

	public abstract Button getRemoveTaskDef();

	public abstract Button getSaveTaskDef();

	public abstract Button getCancelEditTaskDef();
	
	public abstract Button getEditTaskDef();

	public abstract  Form getTaskDefForm();

	public abstract  HorizontalLayout getButtonLayout();

	public abstract  VerticalLayout getGridLayout();
	
	public abstract  VerticalLayout getDetailLayout();
}