package com.conx.logistics.kernel.persistence.dynaconfiguration;

import java.util.Map;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conx.logistics.kernel.persistence.services.IPersistenceConfugurationBundle;

public class ConfigurationBundleTracker {
	
	enum UpdateAction { ADD, REMOVE };
	
	private DynamicConfiguration dynamicConfiguration;
	
	private static Logger logger = LoggerFactory.getLogger(ConfigurationBundleTracker.class);
	
	public void start() {
	}
	 
	public void stop() {
	}

	public void setDynamicConfiguration(
			DynamicConfiguration dynamicConfiguration) {
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
		}
	}	

	private void updateBundleClasses(Bundle bundle,String mappedClassList, UpdateAction updateAction) {
		if (mappedClassList != null) {
			String[] classes = parseHibernateClasses((String) mappedClassList);
			switch (updateAction) {
				case ADD:
					logger.info("Adding classes from hibernate configuration: " + writeArray(classes));
					dynamicConfiguration.addAnnotatedClasses(bundle, classes);
					break;
				case REMOVE:
					logger.info("Removing classes from hibernate configuration: " + writeArray(classes));
					dynamicConfiguration.removeAnnotatedClasses(bundle, classes);
					break;
				default:
					throw new IllegalArgumentException("" + updateAction);
			}
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