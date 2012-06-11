package com.conx.logistics.kernel.system.ui;

import org.vaadin.mvp.presenter.FactoryPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.system.ui.view.IModuleEditorView;
import com.conx.logistics.kernel.system.ui.view.ModuleEditorView;
import com.conx.logistics.mdm.domain.application.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

@Presenter(view = ModuleEditorView.class)
public class ModuleEditorPresenter extends FactoryPresenter<IModuleEditorView, ModuleEditorEventBus> {

  private BeanItemContainer<Application> container;
  
  private Window dialog = null;
  private Table moduleTable = null;

  @Override
  public void bind() {
    // configure the form with bean item
    //this.moduleTable = view.getApplicationTable();
    /*
    Application u = new Application();
    u.setApplicationName("newuser");
    u.setFirstName("First name");
    u.setLastName("Last name");
    BeanItem<Application> beanItem = new BeanItem<Application>(u);
    this.userForm.setItemDataSource(beanItem);
    */
  }
  
  public void onLaunch() {
  }  

  public void onSaveApplication() {
    // get the user and add it to the container
    //BeanItem<Application> item = (BeanItem<Application>) this.userForm.getItemDataSource();
    //Application u = item.getBean();
    //this.container.addBean(u);
    
    // close dialog
    //this.closeDialog();
  }
  
  public void onCancelEditApplication() {
    // close dialog only
    this.closeDialog();
  }
  
  private void closeDialog() {
    // dismiss the dialog
    Window applicationWindow = (Window) this.dialog.getParent();
    applicationWindow.removeWindow(this.dialog);
    //this.dialog = null;
    //this.userForm = null;
  }

}
