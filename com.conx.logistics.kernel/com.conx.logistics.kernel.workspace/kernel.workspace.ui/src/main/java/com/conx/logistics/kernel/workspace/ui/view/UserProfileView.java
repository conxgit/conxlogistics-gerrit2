package com.conx.logistics.kernel.workspace.ui.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class UserProfileView extends VerticalLayout implements IUserProfileView, IUiBindable {
  @UiField
  Form userForm;

  @Override
  public Form getUserForm() {
    return userForm;
  }
}
