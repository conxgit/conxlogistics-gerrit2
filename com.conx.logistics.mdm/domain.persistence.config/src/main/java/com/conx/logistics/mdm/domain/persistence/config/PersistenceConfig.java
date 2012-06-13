package com.conx.logistics.mdm.domain.persistence.config;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import com.conx.logistics.kernel.persistence.services.IPersistenceConfugurationBundle;

public class PersistenceConfig implements IPersistenceConfugurationBundle, BundleContextAware {
	private BundleContext bc;
	
	@Override
	public void setBundleContext(BundleContext bc) {
		this.bc = bc;
	}

	@Override
	public Bundle getBundle() {
		return bc.getBundle();
	}
}
