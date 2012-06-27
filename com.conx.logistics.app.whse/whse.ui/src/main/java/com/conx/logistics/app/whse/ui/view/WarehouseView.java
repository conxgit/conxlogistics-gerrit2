package com.conx.logistics.app.whse.ui.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public class WarehouseView extends VerticalLayout implements IWarehouseView, IUiBindable {

  @UiField
  VerticalLayout mainLayout;
  
  @UiField
  HorizontalSplitPanel splitLayout;
  
  @Override
  public void setNavigation(Component menu) {
    splitLayout.setFirstComponent(menu);
  }

  @Override
  public void setContent(Component content) {
    splitLayout.setSecondComponent(content);
  }

  @Override
  public HorizontalSplitPanel getSplitLayout() {
    return splitLayout;
  }
  
  @Override
  public VerticalLayout getMainLayout() {
    return mainLayout;
  }
  
}
