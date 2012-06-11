package com.conx.logistics.kernel.workspace.ui;

import org.vaadin.mvp.presenter.FactoryPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.workspace.ui.contribution.User;
import com.conx.logistics.kernel.workspace.ui.view.IUserProfileView;
import com.conx.logistics.kernel.workspace.ui.view.UserProfileView;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Form;
import com.vaadin.ui.Window;

@Presenter(view = UserProfileView.class)
public class UserProfilePresenter extends FactoryPresenter<IUserProfileView, UserProfileEventBus> {

  private BeanItemContainer<User> container;
  
  private Window dialog = null;
  private Form userForm = null;

  @Override
  public void bind() {
    // configure the form with bean item
    this.userForm = view.getUserForm();
    User u = new User();
    u.setUserName("newuser");
    u.setFirstName("First name");
    u.setLastName("Last name");
    BeanItem<User> beanItem = new BeanItem<User>(u);
    this.userForm.setItemDataSource(beanItem);
  }
  
  public void onLaunch() {
  }  

  public void onSaveUser() {
    // get the user and add it to the container
    BeanItem<User> item = (BeanItem<User>) this.userForm.getItemDataSource();
    User u = item.getBean();
    this.container.addBean(u);
    
    // close dialog
    this.closeDialog();
  }
  
  public void onCancelEditUser() {
    // close dialog only
    this.closeDialog();
  }
  
  private void closeDialog() {
    // dismiss the dialog
    Window applicationWindow = (Window) this.dialog.getParent();
    applicationWindow.removeWindow(this.dialog);
    this.dialog = null;
    this.userForm = null;
  }

}
