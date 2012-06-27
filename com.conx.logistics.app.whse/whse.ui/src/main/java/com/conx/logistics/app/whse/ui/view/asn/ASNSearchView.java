package com.conx.logistics.app.whse.ui.view.asn;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public class ASNSearchView extends VerticalLayout implements
		IASNSearchView, IUiBindable {
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
	Button createASN;

	@UiField
	Button removeASN;

	/**
	 * Detail
	 */
	@UiField
	VerticalLayout detailLayout;

	@UiField
	Form taskDefForm;
	
	@UiField
	Button editASN;	

	@UiField
	Button saveASN;

	@UiField
	Button cancelEditASN;

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
	public Form getASNForm() {
		return taskDefForm;
	}

	@Override
	public Button getCreateASN() {
		return createASN;
	}
	
	@Override
	public HorizontalLayout getButtonLayout() {
		return buttonLayout;
	}

	@Override
	public Button getRemoveASN() {
		return removeASN;
	}

	@Override
	public Button getSaveASN() {
		return saveASN;
	}

	@Override
	public Button getCancelEditASN() {
		return cancelEditASN;
	}

	@Override
	public Button getEditASN() {
		return editASN;
	}
}
