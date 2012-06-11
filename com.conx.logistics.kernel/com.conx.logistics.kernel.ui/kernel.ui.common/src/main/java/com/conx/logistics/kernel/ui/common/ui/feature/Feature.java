package com.conx.logistics.kernel.ui.common.ui.feature;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.ui.Component;

/**
 * Represents one feature or sample, with associated example.
 * <p>
 * 
 * </p>
 * 
 */
@SuppressWarnings("serial")
public class Feature implements Serializable {
    public static final Object PROPERTY_ICON = "Icon";
    public static final Object PROPERTY_NAME = "Name";
    public static final Object PROPERTY_DESCRIPTION = "Description";

    private static final String MSG_SOURCE_NOT_AVAILABLE = "I'm terribly sorry,"
            + " but it seems the source could not be found.\n"
            + "Please try adding the source folder to the classpath for your"
            + " server, or tell the administrator to do so!";

    private static final Object MUTEX = new Object();
    private String javaSource = null;
    private FeatureSet parentFeatureSet;
    
	private String name;
	private String description;
	private Class<? extends Feature>[] relatedFeatures;
	private Version sinceVersion;
	private String code;
	
	public Feature()
	{
	}
	
	public Feature(String code,
				   String name,
				   String description,
				   Class<? extends Feature>[] relatedFeatures,
				   Version sinceVersion)
	{
		this.code = code;
		this.name = name;
		this.description = description;
		this.relatedFeatures = relatedFeatures;
		this.sinceVersion = sinceVersion;
	}
	
	

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * Gets the name of this feature. Try not to exceed 25 characters too much.
     * 
     * @return
     */
    public String getName()
    {
    	return this.name;
    }

    /**
     * Gets the description for this feature. Should describe what the example
     * intends to showcase. May contain HTML. 100 words should be enough, and
     * about 7 rows...
     * 
     * @return the description
     */
    public String getDescription()
    {
    	return this.description;
    }

    /**
     * Gets related Features; the classes returned should extend Feature.
     * <p>
     * Good candidates are Features similar to this one, Features using the
     * functionality demoed in this one, and Features being used in this one.
     * </p>
     * <p>
     * May return null, if no other Features are related to this one.
     * <p>
     * 
     * @return
     */
    public Class<? extends Feature>[] getRelatedFeatures()
    {
    	return this.relatedFeatures;
    }

    /**
     * Gets the name of the icon for this feature, usually simpleName +
     * extension.
     * 
     * @return
     */
    public String getIconName() {
        String icon = getClass().getSimpleName() + ".gif";
        return icon;
    }

    /**
     * Get the example instance. Override if instantiation needs parameters.
     * 
     * @return
     */
    public Component getExample() {

        String className = this.getClass().getName() + "Example";
        try {
            Class<?> classObject = getClass().getClassLoader().loadClass(
                    className);
            return (Component) classObject.newInstance();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

    }

    /**
     * Gets the name used when resolving the path for this feature. Usually no
     * need to override, but NOTE that this must be unique within Sampler.
     * 
     * @return
     */
    public String getFragmentName() {
        return getClass().getSimpleName();
    }

    public enum Version {
        OLD(0), BUILD(Integer.parseInt(AbstractApplicationServlet.VERSION_MAJOR
                + "" + AbstractApplicationServlet.VERSION_MINOR)), V62(62), V63(
                63), V64(64), V65(65), V66(66), V67(67);

        private final int version;

        Version(int version) {
            this.version = version;
        }

        /**
         * Checks whether this version is newer or as new as the build that it
         * is included in.
         * 
         * You can use Version.BUILD if you wish for a Feature to always appear
         * as new.
         * 
         * @return
         */
        public boolean isNew() {
            return BUILD.version <= version;
        }
    }

    /**
     * Returns the Vaadin version number in which this feature was added to
     * Sampler. Usually features should only be added in major and minor
     * version, not in maintenance versions.
     * 
     * Uses int internally for easy comparison: version 6.1.4 -> 61 (maintenance
     * version number is ignored)
     * 
     * Override in each feature. Returns Version.OLD otherwise.
     * 
     * @return Version Vaadin version when this feature was added to Sampler
     */
    public Version getSinceVersion()
    {
    	return this.sinceVersion;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        // A feature is uniquely identified by its class name
        if (obj == null) {
            return false;
        }
        return obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        // A feature is uniquely identified by its class name
        return getClass().hashCode();
    }

    public void setParentFeature(FeatureSet parentFeatureSet) {
        this.parentFeatureSet = parentFeatureSet;
    }

    protected Class<? extends Feature>[] getSiblingFeatures() {
        if (parentFeatureSet != null) {
            Feature[] features = parentFeatureSet.getFeatures();
            ArrayList<Class<? extends Feature>> siblingFeatureList = new ArrayList<Class<? extends Feature>>(
                    features.length - 1);
            for (Feature f : features) {
                if (f != this) {
                    siblingFeatureList.add(f.getClass());
                }
            }
            @SuppressWarnings("unchecked")
            Class<? extends Feature>[] siblingFeaturs = (Class<? extends Feature>[]) siblingFeatureList
                    .toArray(new Class<?>[siblingFeatureList.size()]);
            return siblingFeaturs;
        } else {
            return null;
        }
    }
}