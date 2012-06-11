package com.conx.logistics.kernel.ui.common.ui.feature;

import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;

/**
 * Contains the FeatureSet implementation and the structure for the feature
 * 'tree'.
 * <p>
 * Each set is implemented as it's own class to facilitate linking to sets in
 * the same way as linking to individual features.
 * </p>
 * 
 */
@SuppressWarnings("serial")
public class FeatureSet extends Feature {

    @Override
    public Version getSinceVersion() {
        return Version.OLD;
    }


    // ----------------------------------------------------------
    /*
     * FeatureSet implementation follows.
     */

    private  HashMap<String, Feature> pathnameToFeature = null;

    private  String pathname = "";

    private  String name = "";

    private  String desc = "";

    private  String icon = "folder.gif";

    private  Feature[] content = null;

    private HierarchicalContainer container = null;

    private final boolean containerRecursive = false;


    public FeatureSet()
    {
    }

    public FeatureSet(String pathname, String name, Feature[] content) {
        this(pathname, name, "", content);
    }

    public FeatureSet(String pathname, String name, String desc, Feature[] content) {
        this.pathname = pathname;
        this.name = name;
        this.desc = desc;
        this.content = content;
        addFeature(this);
        if (content != null) {
            for (Feature f : content) {
                f.setParentFeature(this);
                if (f instanceof FeatureSet) {
                    continue;
                }
                addFeature(f);
            }
        }
    }

    private void addFeature(Feature f) {
        if (pathnameToFeature == null) {
            pathnameToFeature = new HashMap<String, Feature>();
        }
        if (pathnameToFeature.containsKey(f.getName())) {
            throw new IllegalArgumentException("Duplicate pathname for "
                    + f.getName() + ": "
                    + pathnameToFeature.get(f.getFragmentName()).getClass()
                    + " / " + f.getClass());
        }
        pathnameToFeature.put(f.getName(), f);
    }

    public Feature getFeature(String pathname) {
        return pathnameToFeature.get(pathname);
    }

    public Feature[] getFeatures() {
        return content;
    }

    public HierarchicalContainer getContainer(boolean recurse) {
        if (container == null || containerRecursive != recurse) {
            container = new HierarchicalContainer();
            container.addContainerProperty(PROPERTY_NAME, String.class, "");
            container.addContainerProperty(PROPERTY_DESCRIPTION, String.class,
                    "");
            // fill
            addFeatures(this, container, recurse);
        }
        return container;
    }

    private void addFeatures(FeatureSet f, HierarchicalContainer c,
            boolean recurse) {
        Feature[] features = f.getFeatures();
        for (int i = 0; i < features.length; i++) {
            Item item = c.addItem(features[i]);
            if (item != null)
            {
	            Property property = item.getItemProperty(PROPERTY_NAME);
	            property.setValue(features[i].getName());
	            property = item.getItemProperty(PROPERTY_DESCRIPTION);
	            property.setValue(features[i].getDescription());
	            if (recurse) {
	                c.setParent(features[i], f);
	                if (features[i] instanceof FeatureSet) {
	                    addFeatures((FeatureSet) features[i], c, recurse);
	                }
	            }
	            if (!(features[i] instanceof FeatureSet)) {
	                c.setChildrenAllowed(features[i], false);
	            }
            }
        }
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getFragmentName() {
        return pathname;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconName() {
        return icon;
    }

    @Override
    public Class<? extends Feature>[] getRelatedFeatures() {
        return null;
    }

}
