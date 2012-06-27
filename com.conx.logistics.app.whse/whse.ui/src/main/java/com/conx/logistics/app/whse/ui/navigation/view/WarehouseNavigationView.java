package com.conx.logistics.app.whse.ui.navigation.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class WarehouseNavigationView extends VerticalLayout implements IWarehouseNavigationView, IUiBindable {

  @UiField
  CssLayout mainLayout;

  @Override
  public CssLayout getMainLayout() {
    return this.mainLayout;
  }
  
}
