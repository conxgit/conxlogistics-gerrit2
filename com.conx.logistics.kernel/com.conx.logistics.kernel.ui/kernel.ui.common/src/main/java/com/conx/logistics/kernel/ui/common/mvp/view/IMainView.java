package com.conx.logistics.kernel.ui.common.mvp.view;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public interface IMainView {
	/**
	 * Gets the outermost layout for the main window of the vaadin application.
	 * @return Outermost layout for main window
	 */
	public abstract VerticalLayout getMainLayout();
	
	/**
	 * Gets the main tabsheet of the main layout of the application.
	 * @return Applciation tab sheet
	 */
	public TabSheet getApplicationTabSheet();

	/**
	 * Gets the current vaadin theme name for the vaadin application.
	 * @return Application theme name
	 */
	public String getCurrentApplicationTheme();

	/**
	 * Sets the container for the "Select Application" drop down in the header
	 * of the main layout of the vaadin application.
	 * @param container a vaadin container
	 * @param idPropertyId property id of the property field
	 * @param captionPropertyId property id of the caption field
	 * @param iconPropertyId property id of the icon field
	 */
	public void setAppSelectionContainer(Container container, Object idPropertyId, 
			Object captionPropertyId, Object iconPropertyId);
	
	/**
	 * Adds a listener that listens for when an application is selected.
	 * @param listener a value change listener
	 */
	public void addAppSelectionListener(ValueChangeListener listener);
	
	/**
	 * Puts customer branding and information on the main layout.
	 * @param customerName the company name of the customer
	 * @param customerLogoUrl the url to the customer's 30px high logo
	 */
	public void setCustomerDetails(String customerName, String customerLogoUrl);
}