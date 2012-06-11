package com.conx.logistics.kernel.system.ui.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ModuleEditorView extends VerticalLayout implements IModuleEditorView, IUiBindable {
  @UiField
  Table moduleTable;

  @Override
  public Table getModuleTable() {
    return moduleTable;
  }
}
