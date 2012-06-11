package com.conx.logistics.kernel.system.ui.navigation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.system.data.HierarchicalFeatureContainer;
import com.conx.logistics.kernel.system.ui.ControlPanelEventBus;
import com.conx.logistics.kernel.system.ui.navigation.view.IControlPanelNavigationView;
import com.conx.logistics.kernel.system.ui.navigation.view.ControlPanelNavigationView;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.mdm.domain.application.Application;
import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.Tree;

import com.vaadin.data.util.filter.Compare;

@Presenter(view = ControlPanelNavigationView.class)
public class ControlPanelNavigationPresenter extends
		BasePresenter<IControlPanelNavigationView, ControlPanelEventBus> {

	private final ObjectProperty<Feature> currentFeature = new ObjectProperty<Feature>(null, Feature.class);
	private Tree navigationTree;
	private MainMVPApplication mainApp;
	private EntityManagerFactory kernelSystemEntityManagerFactory;
	private EntityManager kernelSystemEntityManager;
	private JPAContainer<Application> systemApps;
	private Application controlPanelApplication;
	private JPAContainer<Feature> controlPanelFeatures;

	@Override
	public void bind() {
	}
	
	/**
	 * onStart event handler
	 */
	public void onStart(MainMVPApplication mainApp)
	{
		this.mainApp = mainApp;
		this.kernelSystemEntityManagerFactory = mainApp.getKernelSystemEntityManagerFactory();
		this.kernelSystemEntityManager = this.kernelSystemEntityManagerFactory.createEntityManager();
		
		//== Find ControlPanel system app and init container filter
		TypedQuery<Application> appQuery = this.kernelSystemEntityManager.createQuery("select o from Application o where o.code = :code",Application.class);
		appQuery.setParameter("code", IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_CODE);
		try 
		{
			this.controlPanelApplication = appQuery.getSingleResult();
		} 
		catch (NoResultException e) 
		{
		}	
		this.controlPanelFeatures = new HierarchicalFeatureContainer(this.kernelSystemEntityManager);
		this.controlPanelFeatures.addContainerFilter(new Compare.Equal("parentApplication.id",this.controlPanelApplication.getId()));
		
		// Menu tree, initially shown
		this.navigationTree = createMenuTree();
		this.navigationTree.setSizeFull();
		this.view.getMainLayout().addComponent(navigationTree);
	}

	private Tree createMenuTree() {
		final Tree tree = new Tree();
		try {
			// -- Create datasource
			/*
			Feature userInfo = new Feature("KERNEL.WORKSPACE.PROFILE.BASICVIEW","Basic", "Basic User Details", null,Feature.Version.V67);
			Feature userContactInfo = new Feature("KERNEL.WORKSPACE.PROFILE.CONTACTSVIEW","Contacts", "User Contacts",null, Feature.Version.V67);
			FeatureSet profileFS = new FeatureSet("Profile", "Profile",
					new Feature[] { userInfo, userContactInfo });
			this.allFeatures = new FeatureSet("Home", "Home",
					new Feature[] { profileFS });
			*/
			// -- Populate tree
			tree.setImmediate(true);
			tree.setStyleName("menu");
			tree.setItemCaptionPropertyId("name");
			tree.setContainerDataSource(this.controlPanelFeatures);
			currentFeature.addListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					Feature f = (Feature) event.getProperty().getValue();
					Feature v = ControlPanelNavigationPresenter.this.controlPanelFeatures.getItem(tree.getValue()).getEntity();
					if ((f != null && !f.equals(v)) || (f == null && v != null)) {
						tree.setValue(f);
					}
				}
			});
			for (Object iid : this.controlPanelFeatures.getItemIds()) {
				tree.expandItemsRecursively(iid);
			}
			//tree.expandItemsRecursively(allFeatures);
			tree.addListener(new Tree.ValueChangeListener() {
				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					Feature f = (Feature) ControlPanelNavigationPresenter.this.controlPanelFeatures.getItem(event.getProperty().getValue()).getEntity();
					setFeature(f);
				}
			});
			tree.setItemStyleGenerator(new Tree.ItemStyleGenerator() {
				@Override
				public String getStyle(Object itemId) {
					Feature f = (Feature) ControlPanelNavigationPresenter.this.controlPanelFeatures.getItem(itemId).getEntity();
					//if (f.getSinceVersion().isNew()) {
					//	return "new";
					//}
					return "new";
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}

	/**
	 * Displays a Feature(Set)
	 * 
	 * @param f
	 *            the Feature(Set) to show
	 */
	public void setFeature(Feature f) {
		//if (f == this.allFeatures) {
			// "All" is no longer in the tree, use null instead
		//	f = null;
		//}
		currentFeature.setValue(f);
		//String fragment = (f != null ? f.getFragmentName() : "");
		
		this.getEventBus().openFeatureView(f);
		//updateFeatureList(currentList);
	}

	public void onSelectMenu(ValueChangeEvent event) {
		// get the selected menu entry and initiate another event
		// MenuEntry menuEntry = (MenuEntry) this.view.getTree().getValue();
		// this.eventBus.openModule(menuEntry.getPresenterType());
	}

}
