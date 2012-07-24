package com.conx.logistics.kernel.ui.common.mvp.view;

import com.conx.logistics.kernel.ui.common.ui.ext.ConXFooter;
import com.conx.logistics.kernel.ui.common.ui.ext.ConXHeader;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MainView extends Window implements IMainView {
	private static final long serialVersionUID = 6734894019724150200L;
	// Window theme name; themes are located in web.app/src/main/webapp/VAADIN
	private static final String CONX_THEME_NAME = "conx";
	// Window title
	private final String WINDOW_TITLE = "ConX Logistics";

	private VerticalLayout mainLayout;
	private ConXHeader headerStrip;
	private ConXFooter footerStrip;
	private TabSheet applicationTabSheet;
	
	@Override
	public VerticalLayout getMainLayout() {
		return mainLayout;
	}
	
	public String getCurrentApplicationTheme() {
		return CONX_THEME_NAME;
	}

	@Override
	public void detach() {
		super.detach();
		setAppSelectionContainer(null, null, null, null);
	}

	public MainView() {
		// Main Layout of Window
		mainLayout = new VerticalLayout();
		// Replace window layout with mainLayout so application auto expands
		setContent(getMainLayout());
		// Window occupies maximum space available
		setSizeFull();
		// MainLayout occupies maximum space available
		getMainLayout().setSizeFull();
		setCaption(WINDOW_TITLE);
		// Sets the Window's Vaadin theme to the "ConX" theme
		setTheme(CONX_THEME_NAME);

		// Header
		headerStrip = new ConXHeader();
		// Footer
		footerStrip = new ConXFooter();
		// Application TabSheet
		applicationTabSheet = new TabSheet();
		
		// Add the pieces of the main layout
		mainLayout.addComponent(headerStrip);
		mainLayout.addComponent(applicationTabSheet);
		mainLayout.addComponent(footerStrip);
		mainLayout.setExpandRatio(applicationTabSheet, 1.0f);
	}
	
	@Override
	public TabSheet getApplicationTabSheet() {
		return applicationTabSheet;
	}

	
	@Override
	public void setAppSelectionContainer(Container container,
			Object idPropertyId, Object captionPropertyId, Object iconPropertyId) {
		headerStrip.setApplicationMenuContainer(container, idPropertyId, captionPropertyId, iconPropertyId);
	}

	@Override
	public void addAppSelectionListener(ValueChangeListener listener) {
		headerStrip.addAppSelectionListener(listener);
	}

	@Override
	public void setCustomerDetails(String customerName, String customerLogoUrl) {
		headerStrip.setCustomerLogo(customerLogoUrl);
		footerStrip.setCustomerName(customerName);
	}
	
	/**
	 * Components capable of listing Features should implement this.
	 * 
	 */
	interface FeatureList extends Component {
		/**
		 * Shows the given Features
		 * 
		 * @param c
		 *            Container with Features to show.
		 */
		public void setFeatureContainer(HierarchicalContainer c);
	}

}
