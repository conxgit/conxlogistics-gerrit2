package com.conx.logistics.kernel.ui.common.mvp.view;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

public interface IMainView {
  public abstract VerticalLayout getMainLayout();
  public String getCurrentApplicationTheme();
  public ComboBox getAppSelection();
}