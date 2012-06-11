package com.conx.logistics.kernel.ui.common.mvp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.view.IMainView;
import com.conx.logistics.kernel.ui.common.mvp.view.MainView;
import com.conx.logistics.kernel.ui.common.ui.menu.app.AppMenuEntry;
import com.conx.logistics.mdm.domain.application.Application;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window;
import com.vaadin.ui.Component.Event;

@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<IMainView, MainEventBus>
		implements Property.ValueChangeListener {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private MainMVPApplication application;

	private IPresenter<?, ? extends EventBus> contentPresenter;

	private TabSheet mainTabSheet;

	private EntityManagerFactory kernelSystemEntityManagerFactory;

	private EntityManager kernelSystemEntityManager;

	private JPAContainer<Application> launchableAppsContainer;

	private Map<String, Tab> appTabMap = new HashMap<String, Tab>();

	private IndexedContainer appSelectionContainer;

	private boolean initialized = false;

	/**
	 * 
	 * EventBus callbacks
	 * 
	 */
	public void onStart(MainMVPApplication app) {
		try {
			// keep a reference to the application instance
			this.application = app;

			/**
			 * this.kernelSystemEntityManagerFactory =
			 * this.application.getKernelSystemEntityManagerFactory();
			 * this.kernelSystemEntityManager =
			 * this.kernelSystemEntityManagerFactory.createEntityManager();
			 * 
			 * this.launchableAppsContainer =
			 * JPAContainerFactory.make(Application
			 * .class,this.kernelSystemEntityManager);
			 * this.view.getAppSelection(
			 * ).setContainerDataSource(this.launchableAppsContainer);
			 */
			this.view.getAppSelection().addListener(MainPresenter.this);
			// set the applications main windows (the view)
			this.application.setMainWindow((Window) this.view);

			// load the workspace presenter

			IPresenterFactory pf = application.getPresenterFactory();
			// this.workspacePresenter = (WorkspacePresenter)
			// pf.createPresenter(WorkspacePresenter.class);
			// WorkspaceEventBus wsEventBus = (WorkspaceEventBus)
			// this.workspacePresenter.getEventBus();
			// wsEventBus.start(app);
			// Component ws = (Component)workspacePresenter.getView();
			// ((Layout)ws).setSizeFull();
			// mainTabSheet.addTab(ws, "", new ThemeResource(
			// "custom/img/home.png"));
			appSelectionContainer = new IndexedContainer();			
			appSelectionContainer.addContainerProperty("code", String.class, null);
			appSelectionContainer.addContainerProperty("name", String.class, null);
			appSelectionContainer.addContainerProperty("id", String.class, null);
			appSelectionContainer.addContainerProperty("icon", Resource.class, null);
			appSelectionContainer.addContainerProperty("launchable", Component.class, null);
			Filter filter = new Not(new SimpleStringFilter("id","KERNEL.WORKSPACE", true, false));
			appSelectionContainer.addContainerFilter(filter);
			this.view.getAppSelection().setContainerDataSource(appSelectionContainer);
			this.view.getAppSelection().setItemCaptionPropertyId("name");
			this.view.getAppSelection().setItemIconPropertyId("icon");
			AppMenuEntry[] menuEntries = this.application.createAppMenuEntries();	
			updateAppSelectContainer(menuEntries);
			this.initialized  = true;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}

	/**
	 * 
	 * App tab callback methods
	 *
	 */	
	public void onUpdateAppContributions(AppMenuEntry[] appMenuEntries) {
		try {
			updateAppSelectContainer(appMenuEntries);
			initialized = true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onAddAppContribution(AppMenuEntry appMenuEntry) {
		if (initialized)
		{
			String id = appMenuEntry.getCode();
			String name = appMenuEntry.getCaption();
			if (!appSelectionContainer.containsId(id))
			{
				try {
					Item item = this.appSelectionContainer.addItem(id);
					item.getItemProperty("launchable").setValue(appMenuEntry.getLaunchableAppComponent());
					item.getItemProperty("name").setValue(name);
					item.getItemProperty("id").setValue(id);
					item.getItemProperty("icon").setValue(new ThemeResource(appMenuEntry.getIconPath()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Error e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}	
	
	public void onRemoveAppContribution(AppMenuEntry appMenuEntry) {
		if (initialized)
		{
			String id = appMenuEntry.getCode();
			if (appSelectionContainer.containsId(id))
			{
				appSelectionContainer.removeItem(id);
				removeAppComponent(appMenuEntry.getCaption());
			}
		}
	}		

	private void removeAppComponent(String caption) {
		Tab tab = appTabMap.get(caption);
		mainTabSheet.removeTab(tab);
	}

	/**
	 * 
	 * Local methods
	 *
	 */
	public IndexedContainer updateAppSelectContainer(
			AppMenuEntry[] appMenuEntries) {
		fillAppSelectContainer(appSelectionContainer, appMenuEntries);
		return appSelectionContainer;
	}

	private void fillAppSelectContainer(IndexedContainer container,
			AppMenuEntry[] appMenuEntries) {
		for (int i = 0; i < appMenuEntries.length; i++) {
			String name = appMenuEntries[i].getCaption();
			String id = appMenuEntries[i].getCode();
			Item item = container.addItem(id);
			item.getItemProperty("launchable").setValue(
					appMenuEntries[i].getLaunchableAppComponent());
			item.getItemProperty("name").setValue(name);
			item.getItemProperty("id").setValue(id);
			item.getItemProperty("icon").setValue(
					new ThemeResource(appMenuEntries[i].getIconPath()));
		}
		appSelectionContainer.sort(new Object[] { "name" }, new boolean[] { true });		
	}

	public void onOpenModule(
			Class<? extends BasePresenter<?, ? extends EventBus>> presenter) {
		// load the menu presenter
		IPresenterFactory pf = application.getPresenterFactory();
		this.contentPresenter = pf.createPresenter(presenter);
		// this.view.setContent((Component) this.contentPresenter.getView());
	}

	public void onOpenApplication(
			Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass,
			String name, String iconPath,
			boolean closable) {
		try {
			IPresenterFactory pf = application.getPresenterFactory();
			IPresenter<?, ? extends EventBus> appPresenter = pf
					.createPresenter(appPresenterClass);
			StartableApplicationEventBus wsEventBus = (StartableApplicationEventBus) appPresenter
					.getEventBus();
			wsEventBus.start(this.application);
			Component ws = (Component) appPresenter.getView();
			((Layout) ws).setSizeFull();
			Tab appTab = mainTabSheet.addTab(ws, name, new ThemeResource(iconPath));
			appTab.setClosable(closable);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}

	public void onOpenApplicationComponent(Component appComponent, String id,
			String name, String iconPath,boolean closable) {
		try {
			if (appTabMap.containsKey(name)) {
				Tab selectedTab = appTabMap.get(name);
				mainTabSheet.setSelectedTab(selectedTab.getComponent());
			} else {
				appComponent.setSizeFull();
				Tab newTab = mainTabSheet.addTab(appComponent, name, new ThemeResource(iconPath));
				newTab.setClosable(closable);
				mainTabSheet.setSelectedTab(appComponent);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}

	public void onShowDialog(Window dialog) {
		this.application.getMainWindow().addWindow(dialog);
	}

	@Override
	public void bind() {
		/**
		 * Add workspace view
		 */
		mainTabSheet = new TabSheet();
		mainTabSheet.setSizeFull();

		// this.view.getMainLayout().setHeight(Sizeable.SIZE_UNDEFINED, 0); //
		// Default
		// this.view.getMainLayout().setHeight("500px");
		// this.view.getMainLayout().addComponent(bar);
		this.view.getMainLayout().addComponent(mainTabSheet);
		this.view.getMainLayout().setExpandRatio(mainTabSheet, 1);
		mainTabSheet.setStyleName("main-split");
	}

	/**
	 * OnApplicationSelection changed
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		String id = event.getProperty().toString();
		Item menuEntry = (Item) this.appSelectionContainer.getItem(id);
		onOpenApplicationComponent(
				(Component) menuEntry.getItemProperty("launchable").getValue(),
				(String) menuEntry.getItemProperty("id").getValue(),
				(String) menuEntry.getItemProperty("name").getValue(),
				((ThemeResource) menuEntry.getItemProperty("icon").getValue()).getResourceId(),
				true);
	}
}
