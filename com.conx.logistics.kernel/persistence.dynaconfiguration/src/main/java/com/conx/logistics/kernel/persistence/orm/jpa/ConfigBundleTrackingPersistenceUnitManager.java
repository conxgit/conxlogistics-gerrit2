package com.conx.logistics.kernel.persistence.orm.jpa;

import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

import com.conx.logistics.kernel.persistence.services.IPersistenceConfugurationBundle;

import java.util.Map;

/**
 * An extension to the {@link DefaultPersistenceUnitManager} that is able to
 * merge multiple <tt>persistence.xml</tt> associated to the same persistence unit
 * name.
 * <p/>
 * If a module persistence unit defines managed classes explicitly, only adds the
 * specified classes. If the module persistence unit does not define any managed
 * classes, module scanning is assumed: any entity classes defined in the
 * module holding the persistence unit will be associated to the main one.
 */
public class ConfigBundleTrackingPersistenceUnitManager extends DefaultPersistenceUnitManager {
	enum UpdateAction { ADD, REMOVE };
	
    private final Logger logger = LoggerFactory.getLogger(ConfigBundleTrackingPersistenceUnitManager.class);

    private String persistenceUnitName = "pu";

	private DynamicLocalContainerConfiguration dynamicConfiguration;   
	
	private MutablePersistenceUnitInfo mainPui;

    protected void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        super.postProcessPersistenceUnitInfo(pui);

        mainPui = pui;
        /*
        if (mainPui != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Merging information from [" + pui.getPersistenceUnitName() + "] " +
                        "to [" + mainPui.getPersistenceUnitName() + "]");
            }
            mergePersistenceUnit(pui, mainPui);
            if (logger.isDebugEnabled()) {
                logger.debug("Persistence unit[" + mainPui.getPersistenceUnitName() + "] has now " +
                        "the following jar file urls " + mainPui.getJarFileUrls() + "");
            }
        } else if (!pui.getPersistenceUnitName().equals(persistenceUnitName)) {
            logger.debug("Adding persistence unit [" + pui.getPersistenceUnitName() + "] as is (no merging)");
        }
        */
    }



    /**
     * Sets the name of the persistence unit that should be used. If no
     * such persistence unit exists, an exception will be thrown, preventing
     * the factory to be created.
     * <p/>
     * When the <tt>strict</tt> mode is disabled, this name is used to
     * find all matching persistence units based on a prefix. Say for
     * instance that the <tt>persistenceUnitName</tt> to use is
     * <tt>pu</tt>, the following applies:
     * <ul>
     * <li><tt>pu-base</tt> will be merged</li>
     * <li><tt>pufoo</tt> will be merged</li>
     * <li><tt>base-pu</tt> will <b>not</b> be merged</li>
     * </ul>
     * Make sure to configure your entity manager factory to use this
     * name as the persistence unit
     *
     * @param persistenceUnitName the name of the persistence unit to use
     */
    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }


    /**
     * Returns the main {@link MutablePersistenceUnitInfo} to use, based on the
     * given {@link MutablePersistenceUnitInfo} and the settings of the manager.
     * <p/>
     * In strict mode, returns the declared persistence unit with the specified
     * name. In non strict mode and if the specified <tt>pui</tt> starts with
     * the configured <tt>persistenceUnitName</tt>, returns the template
     * persistence unit info used for the merging.
     *
     * @param pui the persistence unit info to handle
     * @return the persistence unit info to use for the merging
     */
    private MutablePersistenceUnitInfo getMainPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
    	if (pui.getPersistenceUnitName().startsWith(persistenceUnitName)) {
            // We have a match, retrieve our persistence unit name then
            final MutablePersistenceUnitInfo result = getPersistenceUnitInfo(persistenceUnitName);
            // Sanity check
            if (result == null) {
                throw new IllegalStateException("No persistence unit found with name [" + persistenceUnitName + "] " +
                        "so no merging is possible. It usually means that the bootstrap-persistence.xml has not been " +
                        "included in the list of persistence.xml location(s). Check your configuration as it " +
                        "should be the first in the list!");
            }
            return result;
        }
        // Nothing has been found
        return null;
    }
    
	public void setDynamicConfiguration(
			DynamicLocalContainerConfiguration dynamicConfiguration) {
		this.dynamicConfiguration = dynamicConfiguration;
	}

	public void registerConfigurationBundle(IPersistenceConfugurationBundle confBundle,
											Map properties) {
		String mappedClassList = (String)properties.get("mappedClassList");
		Bundle osgiBundle = confBundle.getBundle();
		try {
			logger.info("registerConfigurationBundle: " + osgiBundle.getSymbolicName());
			updateBundleClasses(osgiBundle,mappedClassList,UpdateAction.ADD);
		} catch(RuntimeException re) {
			logger.error("Error processing bundle event", re);
			throw re;
		} catch(ClassNotFoundException re) {
			logger.error("Error processing bundle event. ClassNotFoundException", re);
			throw new RuntimeException("Error processing bundle event. ClassNotFoundException", re);
		}
	}
	
	public void unregisterConfigurationBundle(IPersistenceConfugurationBundle confBundle,
											  Map properties) {
		String mappedClassList = (String)properties.get("mappedClassList");
		Bundle osgiBundle = confBundle.getBundle();
		try {
			logger.info("unregisterConfigurationBundle: " + osgiBundle.getSymbolicName());
			updateBundleClasses(osgiBundle,mappedClassList,UpdateAction.REMOVE);
		} catch(RuntimeException re) {
			logger.error("Error processing bundle event", re);
			throw re;
		} catch(ClassNotFoundException re) {
			logger.error("Error processing bundle event. ClassNotFoundException", re);
			throw new RuntimeException("Error processing bundle event. ClassNotFoundException", re);
		}
	}	

	private void updateBundleClasses(Bundle bundle,String mappedClassList, UpdateAction updateAction) throws ClassNotFoundException {
		if (mappedClassList != null) {
			String[] classes = parseHibernateClasses((String) mappedClassList);
			switch (updateAction) {
				case ADD:
					logger.info("Adding classes from hibernate configuration: " + writeArray(classes));
					for (String cls : classes)
					{
						//mainPui.addManagedClassName(bundle.loadClass(cls).getName());
						mainPui.addManagedClassName(cls);
					}
					dynamicConfiguration.createNewEntityManagerFactory();
					break;
				case REMOVE:
					logger.info("Removing classes from hibernate configuration: " + writeArray(classes));
					break;
				default:
					throw new IllegalArgumentException("" + updateAction);
			}
		}
		
		logger.info("Known classes are");
		for(String c : mainPui.getManagedClassNames()) {
			logger.info(c);
		}		
	}

	private String writeArray(String[] classes) {
		if (classes.length == 0) return "";
		StringBuffer sb = new StringBuffer();
		for(String s : classes) {
			sb.append(s).append(", ");
		}
		return sb.toString().substring(0, sb.length()-2);
	}

	private String[] parseHibernateClasses(String mappedClassList) {
		String[] classes = mappedClassList.split(",");
		return classes;
	}    
}