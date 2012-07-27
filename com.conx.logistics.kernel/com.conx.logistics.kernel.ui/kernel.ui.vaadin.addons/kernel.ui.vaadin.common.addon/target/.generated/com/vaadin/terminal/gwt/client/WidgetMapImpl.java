package com.vaadin.terminal.gwt.client;

import com.google.gwt.core.client.GWT;
import java.util.HashMap;
import com.google.gwt.core.client.RunAsyncCallback;

public class WidgetMapImpl extends com.vaadin.terminal.gwt.client.WidgetMap {
  public Class<? extends Paintable> getImplementationByServerSideClassName(String fullyQualifiedName) {
    fullyQualifiedName = fullyQualifiedName.intern();
    if ( fullyQualifiedName == "com.vaadin.ui.CssLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VCssLayout.class); return com.vaadin.terminal.gwt.client.ui.VCssLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.OptionGroup" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VOptionGroup.class); return com.vaadin.terminal.gwt.client.ui.VOptionGroup.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Audio" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VAudio.class); return com.vaadin.terminal.gwt.client.ui.VAudio.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.ListSelect" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VListSelect.class); return com.vaadin.terminal.gwt.client.ui.VListSelect.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Slider" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VSlider.class); return com.vaadin.terminal.gwt.client.ui.VSlider.class;}
    else if ( fullyQualifiedName == "com.conx.logistics.MyComponent" ) { ensureInstantiator(com.conx.logistics.gwt.client.VMyComponent.class); return com.conx.logistics.gwt.client.VMyComponent.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.ProgressIndicator" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VProgressIndicator.class); return com.vaadin.terminal.gwt.client.ui.VProgressIndicator.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.OrderedLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VOrderedLayout.class); return com.vaadin.terminal.gwt.client.ui.VOrderedLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.CustomLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VCustomLayout.class); return com.vaadin.terminal.gwt.client.ui.VCustomLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.TextField" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTextField.class); return com.vaadin.terminal.gwt.client.ui.VTextField.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.GridLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VGridLayout.class); return com.vaadin.terminal.gwt.client.ui.VGridLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.TextArea" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTextArea.class); return com.vaadin.terminal.gwt.client.ui.VTextArea.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.InlineDateField" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VDateFieldCalendar.class); return com.vaadin.terminal.gwt.client.ui.VDateFieldCalendar.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.UriFragmentUtility" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VUriFragmentUtility.class); return com.vaadin.terminal.gwt.client.ui.VUriFragmentUtility.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Window" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VWindow.class); return com.vaadin.terminal.gwt.client.ui.VWindow.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Button" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VButton.class); return com.vaadin.terminal.gwt.client.ui.VButton.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Upload" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VUpload.class); return com.vaadin.terminal.gwt.client.ui.VUpload.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Tree" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTree.class); return com.vaadin.terminal.gwt.client.ui.VTree.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.HorizontalSplitPanel" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class); return com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.MenuBar" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VMenuBar.class); return com.vaadin.terminal.gwt.client.ui.VMenuBar.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.VerticalLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VVerticalLayout.class); return com.vaadin.terminal.gwt.client.ui.VVerticalLayout.class;}
    else if ( fullyQualifiedName == "com.conx.logistics.kernel.ui.common.gwt.client.ActiveLink" ) { ensureInstantiator(com.conx.logistics.kernel.ui.common.gwt.client.VActiveLink.class); return com.conx.logistics.kernel.ui.common.gwt.client.VActiveLink.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.NativeSelect" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VNativeSelect.class); return com.vaadin.terminal.gwt.client.ui.VNativeSelect.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.PasswordField" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VPasswordField.class); return com.vaadin.terminal.gwt.client.ui.VPasswordField.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Panel" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VPanel.class); return com.vaadin.terminal.gwt.client.ui.VPanel.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.DateField" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VPopupCalendar.class); return com.vaadin.terminal.gwt.client.ui.VPopupCalendar.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.CustomComponent" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VCustomComponent.class); return com.vaadin.terminal.gwt.client.ui.VCustomComponent.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Video" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VVideo.class); return com.vaadin.terminal.gwt.client.ui.VVideo.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.TabSheet" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTabsheet.class); return com.vaadin.terminal.gwt.client.ui.VTabsheet.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.SplitPanel" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class); return com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.PopupView" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VPopupView.class); return com.vaadin.terminal.gwt.client.ui.VPopupView.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.HorizontalLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VHorizontalLayout.class); return com.vaadin.terminal.gwt.client.ui.VHorizontalLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.TreeTable" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTreeTable.class); return com.vaadin.terminal.gwt.client.ui.VTreeTable.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.RichTextArea" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.richtextarea.VRichTextArea.class); return com.vaadin.terminal.gwt.client.ui.richtextarea.VRichTextArea.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.TwinColSelect" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VTwinColSelect.class); return com.vaadin.terminal.gwt.client.ui.VTwinColSelect.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Label" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VLabel.class); return com.vaadin.terminal.gwt.client.ui.VLabel.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Accordion" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VAccordion.class); return com.vaadin.terminal.gwt.client.ui.VAccordion.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.FormLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VFormLayout.class); return com.vaadin.terminal.gwt.client.ui.VFormLayout.class;}
    else if ( fullyQualifiedName == "com.conx.logistics.kernel.ui.common.gwt.client.ui.ConXMenuBar" ) { ensureInstantiator(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXMenuBar.class); return com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXMenuBar.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.NativeButton" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VNativeButton.class); return com.vaadin.terminal.gwt.client.ui.VNativeButton.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.AbsoluteLayout" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout.class); return com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Form" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VForm.class); return com.vaadin.terminal.gwt.client.ui.VForm.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.DragAndDropWrapper" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VDragAndDropWrapper.class); return com.vaadin.terminal.gwt.client.ui.VDragAndDropWrapper.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Embedded" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VEmbedded.class); return com.vaadin.terminal.gwt.client.ui.VEmbedded.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Table" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VScrollTable.class); return com.vaadin.terminal.gwt.client.ui.VScrollTable.class;}
    else if ( fullyQualifiedName == "com.conx.logistics.kernel.ui.common.gwt.client.ui.ConXQuickLaunchBox" ) { ensureInstantiator(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXQuickLaunchBox.class); return com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXQuickLaunchBox.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Select" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VFilterSelect.class); return com.vaadin.terminal.gwt.client.ui.VFilterSelect.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.CheckBox" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VCheckBox.class); return com.vaadin.terminal.gwt.client.ui.VCheckBox.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.Link" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VLink.class); return com.vaadin.terminal.gwt.client.ui.VLink.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.ComboBox" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VFilterSelect.class); return com.vaadin.terminal.gwt.client.ui.VFilterSelect.class;}
    else if ( fullyQualifiedName == "com.vaadin.ui.VerticalSplitPanel" ) { ensureInstantiator(com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical.class); return com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical.class;}
    else return com.vaadin.terminal.gwt.client.ui.VUnknownComponent.class;
  }
  public void ensureInstantiator(Class<? extends Paintable> classType) {
  if(!instmap.containsKey(classType)){
  if( classType == com.vaadin.terminal.gwt.client.ui.VCssLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VCssLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VCssLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VOptionGroup.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VOptionGroup.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VOptionGroup.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VAudio.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VAudio.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VAudio.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VListSelect.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VListSelect.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VListSelect.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VSlider.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VSlider.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VSlider.class );
  }
  }
);} else if( classType == com.conx.logistics.gwt.client.VMyComponent.class) {instmap.put(com.conx.logistics.gwt.client.VMyComponent.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.conx.logistics.gwt.client.VMyComponent.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VProgressIndicator.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VProgressIndicator.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VProgressIndicator.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VOrderedLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VOrderedLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VOrderedLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VCustomLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VCustomLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VCustomLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTextField.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTextField.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTextField.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VGridLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VGridLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VGridLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTextArea.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTextArea.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTextArea.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VDateFieldCalendar.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VDateFieldCalendar.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VDateFieldCalendar.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VUriFragmentUtility.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VUriFragmentUtility.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VUriFragmentUtility.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VWindow.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VWindow.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VWindow.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VButton.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VButton.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VButton.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VUpload.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VUpload.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VUpload.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTree.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTree.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTree.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VSplitPanelHorizontal.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VMenuBar.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VMenuBar.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VMenuBar.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VVerticalLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VVerticalLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VVerticalLayout.class );
  }
  }
);} else if( classType == com.conx.logistics.kernel.ui.common.gwt.client.VActiveLink.class) {instmap.put(com.conx.logistics.kernel.ui.common.gwt.client.VActiveLink.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.conx.logistics.kernel.ui.common.gwt.client.VActiveLink.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VNativeSelect.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VNativeSelect.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VNativeSelect.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VPasswordField.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VPasswordField.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VPasswordField.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VPanel.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VPanel.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VPanel.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VPopupCalendar.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VPopupCalendar.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VPopupCalendar.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VCustomComponent.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VCustomComponent.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VCustomComponent.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VVideo.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VVideo.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VVideo.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTabsheet.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTabsheet.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTabsheet.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VPopupView.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VPopupView.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VPopupView.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VHorizontalLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VHorizontalLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VHorizontalLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTreeTable.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTreeTable.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTreeTable.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.richtextarea.VRichTextArea.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.richtextarea.VRichTextArea.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.richtextarea.VRichTextArea.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VTwinColSelect.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VTwinColSelect.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VTwinColSelect.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VLabel.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VLabel.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VLabel.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VAccordion.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VAccordion.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VAccordion.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VFormLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VFormLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VFormLayout.class );
  }
  }
);} else if( classType == com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXMenuBar.class) {instmap.put(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXMenuBar.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXMenuBar.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VNativeButton.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VNativeButton.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VNativeButton.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VForm.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VForm.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VForm.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VDragAndDropWrapper.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VDragAndDropWrapper.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VDragAndDropWrapper.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VEmbedded.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VEmbedded.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VEmbedded.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VScrollTable.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VScrollTable.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VScrollTable.class );
  }
  }
);} else if( classType == com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXQuickLaunchBox.class) {instmap.put(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXQuickLaunchBox.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.conx.logistics.kernel.ui.common.gwt.client.ui.VConXQuickLaunchBox.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VFilterSelect.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VFilterSelect.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VFilterSelect.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VCheckBox.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VCheckBox.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VCheckBox.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VLink.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VLink.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VLink.class );
  }
  }
);} else if( classType == com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical.class) {instmap.put(com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical.class, new WidgetInstantiator() {
   public Paintable get() {
   return GWT.create(com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical.class );
  }
  }
);}}
  }
  public Class<? extends Paintable>[] getDeferredLoadedWidgets() {
  return new Class[] {
  };
  }
  public Paintable instantiate(Class<? extends Paintable> classType) {
    Paintable p = super.instantiate(classType); if(p!= null) return p;
    return instmap.get(classType).get();
  }
}
