package com.conx.logistics.kernel.ui.common.ui.ext;

import java.util.ArrayList;
import java.util.Collection;

import com.conx.logistics.kernel.ui.common.ui.ext.ConXMenuBar.Command;
import com.conx.logistics.kernel.ui.common.ui.ext.ConXMenuBar.MenuItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;

public class ConXHeader extends HorizontalLayout {
	private static final long serialVersionUID = 8637231952575859159L;
	private static final String STYLE_CUSTOMER_LOGO = "conx-customer-logo";
	private static final String ICON_DEFAULT_CUSTOMER_LOGO = "customer/logo.png";
	private static final String STYLE_RIGHT_MENUBAR = "conx-header-right-menubar";
	private static final String STYLE_APPLICATION_BUTTON = "conx-header-generic-button";
	private static final String STYLE_APPLICATION_MENU = "conx-header-menubar";
	private static final String STYLE_LEFT_MENUBAR = "conx-header-left-menubar";
	private static final String STYLE_QUICK_LAUNCH_ICON = "conx-header-quick-launch-icon";
	private static final String STYLE_CONX_LOGO_BUTTON = "conx-header-x-button";
	private static final String TOOLSTRIP_HEIGHT = "35px";
	private static final String ICON_CONX_LOGO = "icons/conx/conx-logo-button.png";
	private static final String ICON_QUICK_LAUNCH = "icons/conx/conx-header-quick-launch-icon.png";
	private static final String ICON_APPLICATION = "icons/conx/conx-header-application-icon.png";
	private static final String ICON_ALERT = "icons/conx/conx-header-alerts-inbox-icon.png";
	
	private ConXMenuBar conxMenu;
	private ConXMenuBar applicationMenu;
	private ConXMenuBar alertMenu;
	private MenuItem conxButton;
	private ConXQuickLaunchBox quickLaunch;
	private MenuItem applicationButton;
	private Embedded customerLogo;
	private MenuItem alertButton;
	
	private ArrayList<ValueChangeListener> applicationSelectionListeners;

	public ConXHeader() {
		setWidth("100%");
		setHeight(TOOLSTRIP_HEIGHT);
		setMargin(false);
		addStyleName("header-toolstrip");
		
		initialize();
	}
	
	public void initialize() {
		applicationSelectionListeners = new ArrayList<Property.ValueChangeListener>();
		
		conxMenu = new ConXMenuBar();
		conxMenu.setHeight("25px");
		
		conxButton = conxMenu.addItem("", 
				new ThemeResource(ICON_CONX_LOGO), null);
		conxButton.setStyleName(STYLE_CONX_LOGO_BUTTON);
        
        quickLaunch = new ConXQuickLaunchBox();
        quickLaunch.setInputPrompt("Quick Launch...");
        quickLaunch.setWidth("175px");
        quickLaunch.setHeight("25px");
        quickLaunch.setNullSelectionAllowed(false);
        
        Embedded quickLaunchIcon = new Embedded();
        quickLaunchIcon.setIcon(new ThemeResource(ICON_QUICK_LAUNCH));
        quickLaunchIcon.setHeight("16px");
        quickLaunchIcon.setWidth("16px");
        quickLaunchIcon.addStyleName(STYLE_QUICK_LAUNCH_ICON);
        
        HorizontalLayout leftStrip = new HorizontalLayout();
		leftStrip.setMargin(false, false, false, false);
		leftStrip.setSpacing(true);
		leftStrip.setHeight("25px");
		leftStrip.addStyleName(STYLE_LEFT_MENUBAR);
		leftStrip.addComponent(new ConXMenuSeparator());
		leftStrip.addComponent(conxMenu);
		leftStrip.addComponent(new ConXMenuSeparator());
		leftStrip.addComponent(quickLaunchIcon);
		leftStrip.addComponent(quickLaunch);
		leftStrip.addComponent(new ConXMenuSeparator());
		leftStrip.setComponentAlignment(quickLaunch, Alignment.MIDDLE_LEFT);
		
		applicationMenu = new ConXMenuBar();
		applicationMenu.setHeight("25px");
		applicationMenu.setHtmlContentAllowed(true);
		applicationMenu.addStyleName(STYLE_APPLICATION_MENU);
		
		applicationButton = applicationMenu.addItem("Select Application", 
				new ThemeResource(ICON_APPLICATION),
				null);
		applicationButton.setStyleName(STYLE_APPLICATION_BUTTON);
		
		alertMenu = new ConXMenuBar();
		alertMenu.setHeight("25px");
		alertMenu.setHtmlContentAllowed(true);
		alertMenu.addStyleName(STYLE_APPLICATION_MENU);
		
		alertButton = alertMenu.addItem("Alerts (0)", new ThemeResource(ICON_ALERT), null);
		alertButton.setStyleName(STYLE_APPLICATION_BUTTON);
		alertButton.addItem("<b>Sample</b> Alert", null);
		
		HorizontalLayout rightStrip = new HorizontalLayout();
		rightStrip.addStyleName(STYLE_RIGHT_MENUBAR);
		rightStrip.addComponent(applicationMenu);
		rightStrip.addComponent(new ConXMenuSeparator());
		rightStrip.addComponent(alertMenu);
		rightStrip.addComponent(new ConXMenuSeparator());
		
		customerLogo = new Embedded();
		customerLogo.setHeight("20px");
		customerLogo.setWidth("55px");
		customerLogo.addStyleName(STYLE_CUSTOMER_LOGO);
		setCustomerLogo(ICON_DEFAULT_CUSTOMER_LOGO);
		
		HorizontalLayout customerStrip = new HorizontalLayout(); 
		customerStrip.addComponent(customerLogo);
		customerStrip.setMargin(false, true, false, true);
		
		addComponent(leftStrip);
		addComponent(rightStrip);
		addComponent(customerStrip);
		setExpandRatio(rightStrip, 1.0f);
		setComponentAlignment(leftStrip, Alignment.TOP_LEFT);
		setComponentAlignment(rightStrip, Alignment.TOP_LEFT);
		setComponentAlignment(customerStrip, Alignment.TOP_RIGHT);
	}
	
	public MenuItem getConXButton() {
		return conxButton;
	}
	
	public MenuItem getApplicationButton() {
		return applicationButton;
	}
	
	public MenuItem getAlertButton() {
		return alertButton;
	}
	
	public void addAppSelectionListener(ValueChangeListener listener) {
		applicationSelectionListeners.add(listener);
	}
	
	public void setApplicationMenuContainer(Container container, Object idPropertyId, Object captionPropertyId, Object iconPropertyId) {
		if (container != null) {
			Collection<?> ids = container.getItemIds();
			Item item = null;
			Object caption = null;
			Object icon = null;
			for (Object id : ids) {
				item = container.getItem(id);
				caption = item.getItemProperty(captionPropertyId).getValue();
				icon = item.getItemProperty(iconPropertyId).getValue();
				if (caption instanceof String && icon instanceof Resource) {
					final Property idProperty = item.getItemProperty(idPropertyId);
					applicationButton.addItem((String) caption, (Resource) icon, new Command() {
						private static final long serialVersionUID = 6731702813110881599L;

						@Override
						public void menuSelected(MenuItem selectedItem) {
							for (ValueChangeListener listener : applicationSelectionListeners) {
								listener.valueChange(new ValueChangeEvent() {
									private static final long serialVersionUID = -1001923802267487207L;

									@Override
									public Property getProperty() {
										return idProperty;
									}
								});
							}
						}
					});
				}
			}
		} else {
			applicationButton.removeChildren();
		}
	}
	
	public void setCustomerLogo(String url) {
		customerLogo.setIcon(new ThemeResource(url));
	}
}
