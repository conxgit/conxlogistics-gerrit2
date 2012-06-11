package com.conx.logistics.kernel.ui.common.mvp.view;

import java.net.URI;

import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class MainView extends Window implements IMainView {

	// init() inits
	private static final String[] THEMES = { "reindeer", "runo" };
	private static final String SAMPLER_THEME_NAME = "sampler";

	// used when trying to guess theme location
	private static String APP_URL = null;

	private String currentApplicationTheme = SAMPLER_THEME_NAME + "-"
			+ THEMES[0];

	private final String TITLE = "ConX Logistics";

	private final ThemeResource EMPTY_THEME_ICON = new ThemeResource(
			"../sampler/sampler/icon-empty.png");

	private final ThemeResource SELECTED_THEME_ICON = new ThemeResource(
			"../sampler/sampler/select-bullet.png");



	private final Tree navigationTree;
	// "backbutton"
	UriFragmentUtility uriFragmentUtility = new UriFragmentUtility();

	// breadcrumbs
	BreadCrumbs breadcrumbs = new BreadCrumbs();

	Button previousSample;
	Button nextSample;
	private ComboBox search;
	private ComboBox theme;

	private VerticalLayout mainLayout = new VerticalLayout();
	private ComboBox appselect;

	@Override
	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public String getCurrentApplicationTheme()
	{
		return currentApplicationTheme;
	}
	/**
	 * Tries to guess theme location.
	 * 
	 * @return
	 */
	public static String getThemeBase() {
		try {
			URI uri = new URI(APP_URL + "VAADIN/themes/" + SAMPLER_THEME_NAME
					+ "/");
			return uri.normalize().toString();
		} catch (Exception e) {
			System.err.println("Theme location could not be resolved:" + e);
		}
		return "/VAADIN/themes/" + SAMPLER_THEME_NAME + "/";
	}

	@Override
	public void detach() {
		super.detach();

		search.setContainerDataSource(null);
		navigationTree.setContainerDataSource(null);
	}

	public MainView() {
		// Main top/expanded-bottom layout
		setContent(getMainLayout());
		setSizeFull();
		getMainLayout().setSizeFull();
		setCaption(TITLE);
		//setTheme(currentApplicationTheme);

		// topbar (navigation)
		HorizontalLayout nav = new HorizontalLayout();
		getMainLayout().addComponent(nav);
		nav.setHeight("44px");
		nav.setWidth("100%");
		nav.setStyleName("topbar");
		nav.setSpacing(true);
		nav.setMargin(false, true, false, false);

		// Upper left logo
		Component logo = createLogo();
		nav.addComponent(logo);
		nav.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

		// Breadcrumbs
		nav.addComponent(breadcrumbs);
		nav.setExpandRatio(breadcrumbs, 1);
		nav.setComponentAlignment(breadcrumbs, Alignment.MIDDLE_LEFT);

		// invisible analytics -component
		//nav.addComponent(webAnalytics);

		// "backbutton"
		nav.addComponent(uriFragmentUtility);

		// Select theme
		// Component themeSelect = createThemeSelect();
		// nav.addComponent(themeSelect);
		// nav.setComponentAlignment(themeSelect, Alignment.MIDDLE_LEFT);

		// Layouts for top area buttons
		HorizontalLayout appnav = new HorizontalLayout();		
		HorizontalLayout quicknav = new HorizontalLayout();
		HorizontalLayout arrows = new HorizontalLayout();
		nav.addComponent(appnav);
		nav.addComponent(quicknav);
		nav.addComponent(arrows);
		nav.setComponentAlignment(appnav, Alignment.MIDDLE_LEFT);
		nav.setComponentAlignment(quicknav, Alignment.MIDDLE_LEFT);
		nav.setComponentAlignment(arrows, Alignment.MIDDLE_LEFT);
		quicknav.setStyleName("segment");
		appnav.setStyleName("segment");
		arrows.setStyleName("segment");

		// Previous sample
		previousSample = createPrevButton();
		arrows.addComponent(previousSample);
		// Next sample
		nextSample = createNextButton();
		arrows.addComponent(nextSample);
		
		// App nav
		appnav.addComponent(createAppSelection());
		//appnav.addComponent(createAppSelectionMenu());
		
		// "Search" combobox
		Component searchComponent = createSearch();
		quicknav.addComponent(searchComponent);

		// Menu tree, initially shown
		CssLayout menuLayout = new CssLayout();
		navigationTree = createMenuTree();
		menuLayout.addComponent(navigationTree);


		// Show / hide tree
		Component treeSwitch = createTreeSwitch();
		quicknav.addComponent(treeSwitch);

		addListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				/*
				if (getMainWindow() != SamplerWindow.this) {
					SamplerApplication.this.removeWindow(SamplerWindow.this);
				}
				*/
			}
		});
		
		
	}


	/*
	 * SamplerWindow helpers
	 */
	
	private Component createAppSelectionMenu() {
		ContextMenu cm = new ContextMenu();
		cm.setWidth("160px");
		cm.setHeight("20px");
		ContextMenuItem cp = cm.addItem("Control Panel");
		return cm;
	}

	private Component createAppSelection() {
		try {
			appselect = new ComboBox();
			appselect.setWidth("160px");
			appselect.setNewItemsAllowed(false);
			appselect.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
			appselect.setNullSelectionAllowed(false);
			appselect.setImmediate(true);
			appselect.setInputPrompt("Select app...");
			// TODO add icons for section/sample
			/*
			 * PopupView pv = new PopupView("", search) { public void
			 * changeVariables(Object source, Map variables) {
			 * super.changeVariables(source, variables); if (isPopupVisible()) {
			 * search.focus(); } } };
			 */
			final PopupView pv = new PopupView("<span></span>", appselect);
			pv.setStyleName("segment");
			pv.setDescription("Select Application");
		
			appselect.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return appselect;
	}	

	private Component createSearch() {
		search = new ComboBox();
		search.setWidth("160px");
		search.setNewItemsAllowed(false);
		search.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		search.setNullSelectionAllowed(true);
		search.setImmediate(true);
		search.setInputPrompt("Search conx...");
		// TODO add icons for section/sample
		/*
		 * PopupView pv = new PopupView("", search) { public void
		 * changeVariables(Object source, Map variables) {
		 * super.changeVariables(source, variables); if (isPopupVisible()) {
		 * search.focus(); } } };
		 */
		final PopupView pv = new PopupView("<span></span>", search);
		pv.setStyleName("quickjump");
		pv.setDescription("Quick jump");

		return pv;
	}

	private Component createThemeSelect() {
		theme = new ComboBox();
		theme.setWidth("32px");
		theme.setNewItemsAllowed(false);
		theme.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		theme.setImmediate(true);
		theme.setNullSelectionAllowed(false);
		for (String themeName : THEMES) {
			String id = SAMPLER_THEME_NAME + "-" + themeName;
			theme.addItem(id);
			theme.setItemCaption(id, themeName.substring(0, 1).toUpperCase()
					+ themeName.substring(1) + " theme");
			theme.setItemIcon(id, EMPTY_THEME_ICON);
		}

		final String currentWindowTheme = getTheme();
		theme.setValue(currentWindowTheme);
		theme.setItemIcon(currentWindowTheme, SELECTED_THEME_ICON);

		theme.addListener(new ComboBox.ValueChangeListener() {
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {

				final String newTheme = event.getProperty().getValue()
						.toString();
				setTheme(newTheme);

				for (String themeName : THEMES) {
					String id = SAMPLER_THEME_NAME + "-" + themeName;
					theme.setItemIcon(id, EMPTY_THEME_ICON);
				}

				theme.setItemIcon(newTheme, SELECTED_THEME_ICON);
				currentApplicationTheme = newTheme;
			}
		});

		theme.setStyleName("theme-select");
		theme.setDescription("Select Theme");

		return theme;
	}

	private Component createLogo() {
		Button logo = new NativeButton("", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//setFeature((Feature) null);
			}
		});
		logo.setDescription("Home");
		logo.setStyleName(BaseTheme.BUTTON_LINK);
		logo.addStyleName("logo");
		return logo;
	}

	private Button createNextButton() {
		Button b = new NativeButton("");
		b.setStyleName("next");
		b.setDescription("Jump to the next task");
		return b;
	}

	private Button createPrevButton() {
		Button b = new NativeButton("");
		b.setEnabled(false);
		b.setStyleName("previous");
		b.setDescription("Jump to the previous task");
		return b;
	}

	private Component createTreeSwitch() {
		final Button b = new NativeButton();
		b.setStyleName("tree-switch");
		b.addStyleName("down");
		b.setDescription("Toggle sample tree visibility");
		return b;
	}

	private Tree createMenuTree() {
		final Tree tree = new Tree();
		tree.setImmediate(true);
		tree.setStyleName("menu");
		return tree;
	}



	private class BreadCrumbs extends CustomComponent {
		HorizontalLayout layout;

		BreadCrumbs() {
			layout = new HorizontalLayout();
			layout.setSpacing(true);
			setCompositionRoot(layout);
			setStyleName("breadcrumbs");
			setPath(null);
		}

		public void setPath(String path) {

		}
	}
	
	@Override
	public ComboBox getAppSelection()
	{
		return this.appselect;
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
