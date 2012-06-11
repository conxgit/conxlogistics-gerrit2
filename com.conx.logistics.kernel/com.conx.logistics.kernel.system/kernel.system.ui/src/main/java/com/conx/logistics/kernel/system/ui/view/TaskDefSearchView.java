package com.conx.logistics.kernel.system.ui.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public class TaskDefSearchView extends VerticalLayout implements
		ITaskDefSearchView, IUiBindable {
	@UiField
	VerticalLayout mainLayout;

	@UiField
	VerticalSplitPanel splitLayout;

	/**
	 * Grid
	 */
	@UiField
	VerticalLayout gridLayout;

	@UiField
	Table grid;
	
	@UiField
	HorizontalLayout buttonLayout;

	@UiField
	Button createTaskDef;

	@UiField
	Button removeTaskDef;

	/**
	 * Detail
	 */
	@UiField
	VerticalLayout detailLayout;

	@UiField
	Form taskDefForm;
	
	@UiField
	Button editTaskDef;	

	@UiField
	Button saveTaskDef;

	@UiField
	Button cancelEditTaskDef;

	@Override
	public void setDetailComponent(Component component) {
		detailLayout.removeAllComponents();
		detailLayout.addComponent(component);
	}

	@Override
	public VerticalSplitPanel getSplitLayout() {
		return splitLayout;
	}

	@Override
	public Table getSearchGrid() {
		return grid;
	}

	@Override
	public VerticalLayout getMainLayout() {
		return mainLayout;
	}
	
	@Override
	public VerticalLayout getGridLayout() {
		return gridLayout;
	}

	@Override
	public VerticalLayout getDetailLayout() {
		return detailLayout;
	}

	@Override
	public Form getTaskDefForm() {
		return taskDefForm;
	}

	@Override
	public Button getCreateTaskDef() {
		return createTaskDef;
	}
	
	@Override
	public HorizontalLayout getButtonLayout() {
		return buttonLayout;
	}

	@Override
	public Button getRemoveTaskDef() {
		return removeTaskDef;
	}

	@Override
	public Button getSaveTaskDef() {
		return saveTaskDef;
	}

	@Override
	public Button getCancelEditTaskDef() {
		return cancelEditTaskDef;
	}

	@Override
	public Button getEditTaskDef() {
		return editTaskDef;
	}
}
