package com.conx.logistics.kernel.workspace.ui.navigation;

import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.common.ui.feature.Feature;
import com.conx.logistics.kernel.ui.common.ui.feature.FeatureSet;
import com.conx.logistics.kernel.workspace.ui.WorkspaceEventBus;
import com.conx.logistics.kernel.workspace.ui.navigation.view.IWorkspaceNavigationView;
import com.conx.logistics.kernel.workspace.ui.navigation.view.WorkspaceNavigationView;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.Tree;

@Presenter(view = WorkspaceNavigationView.class)
public class WorkspaceNavigationPresenter extends
		BasePresenter<IWorkspaceNavigationView, WorkspaceEventBus> {

	private FeatureSet allFeatures;
	private final ObjectProperty<Feature> currentFeature = new ObjectProperty<Feature>(
			null, Feature.class);
	private Tree navigationTree;

	@Override
	public void bind() {
		// Menu tree, initially shown
		this.navigationTree = createMenuTree();
		this.navigationTree.setSizeFull();
		this.view.getMainLayout().addComponent(navigationTree);
	}

	private Tree createMenuTree() {
		final Tree tree = new Tree();
		try {
			// -- Create datasource
			Feature userInfo = new Feature("KERNEL.WORKSPACE.PROFILE.BASICVIEW","Basic", "Basic User Details", null,Feature.Version.V67);
			Feature userContactInfo = new Feature("KERNEL.WORKSPACE.PROFILE.CONTACTSVIEW","Contacts", "User Contacts",null, Feature.Version.V67);
			FeatureSet profileFS = new FeatureSet("Profile", "Profile",
					new Feature[] { userInfo, userContactInfo });
			this.allFeatures = new FeatureSet("Home", "Home",
					new Feature[] { profileFS });

			// -- Populate tree
			tree.setImmediate(true);
			tree.setStyleName("menu");
			tree.setContainerDataSource(allFeatures.getContainer(true));
			currentFeature.addListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					Feature f = (Feature) event.getProperty().getValue();
					Feature v = (Feature) tree.getValue();
					if ((f != null && !f.equals(v)) || (f == null && v != null)) {
						tree.setValue(f);
					}
				}
			});
			for (int i = 0; i < allFeatures.getFeatures().length; i++) {
				tree.expandItemsRecursively(allFeatures.getFeatures()[i]);
			}
			tree.expandItemsRecursively(allFeatures);
			tree.addListener(new Tree.ValueChangeListener() {
				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					Feature f = (Feature) event.getProperty().getValue();
					setFeature(f);
				}
			});
			tree.setItemStyleGenerator(new Tree.ItemStyleGenerator() {
				@Override
				public String getStyle(Object itemId) {
					Feature f = (Feature) itemId;
					if (f.getSinceVersion().isNew()) {
						return "new";
					}
					return null;
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tree;
	}

	private HierarchicalContainer getAllFeatures() {
		return this.allFeatures.getContainer(true);
	}

	/**
	 * Displays a Feature(Set)
	 * 
	 * @param f
	 *            the Feature(Set) to show
	 */
	public void setFeature(Feature f) {
		if (f == this.allFeatures) {
			// "All" is no longer in the tree, use null instead
			f = null;
		}
		currentFeature.setValue(f);
		String fragment = (f != null ? f.getFragmentName() : "");
		
		this.getEventBus().openFeatureView(f);
		//updateFeatureList(currentList);
	}

	public void onSelectMenu(ValueChangeEvent event) {
		// get the selected menu entry and initiate another event
		// MenuEntry menuEntry = (MenuEntry) this.view.getTree().getValue();
		// this.eventBus.openModule(menuEntry.getPresenterType());
	}

}
