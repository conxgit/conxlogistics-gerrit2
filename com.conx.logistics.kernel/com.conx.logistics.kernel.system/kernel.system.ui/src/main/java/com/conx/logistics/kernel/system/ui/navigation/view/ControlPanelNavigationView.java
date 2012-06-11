package com.conx.logistics.kernel.system.ui.navigation.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class ControlPanelNavigationView extends VerticalLayout implements IControlPanelNavigationView, IUiBindable {

  @UiField
  CssLayout mainLayout;

  @Override
  public CssLayout getMainLayout() {
    return this.mainLayout;
  }
  
}
