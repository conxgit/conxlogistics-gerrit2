package com.conx.logistics.app.whse.ui.view.asn;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public interface IASNSearchView {

	public abstract void setDetailComponent(Component component);

	public abstract Table getSearchGrid();
	
	public abstract VerticalSplitPanel getSplitLayout();

	public abstract VerticalLayout getMainLayout();

	public abstract Button getCreateASN();

	public abstract Button getRemoveASN();

	public abstract Button getSaveASN();

	public abstract Button getCancelEditASN();
	
	public abstract Button getEditASN();

	public abstract  Form getASNForm();

	public abstract  HorizontalLayout getButtonLayout();

	public abstract  VerticalLayout getGridLayout();
	
	public abstract  VerticalLayout getDetailLayout();
}