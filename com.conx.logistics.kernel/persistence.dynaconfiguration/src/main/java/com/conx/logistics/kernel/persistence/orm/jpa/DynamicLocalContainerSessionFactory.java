package com.conx.logistics.kernel.persistence.orm.jpa;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Hibernate session factory that can get updated during the runtime of the
 * application.
 */
public class DynamicLocalContainerSessionFactory implements FactoryBean<EntityManagerFactory>, InitializingBean {

	private DynamicLocalContainerConfiguration configuration;

	public void setConfiguration(DynamicLocalContainerConfiguration configuration) {
		this.configuration = configuration;
	}

	public EntityManagerFactory getObject() throws Exception {
		return configuration.getEntityManagerFactoryBean().getNativeEntityManagerFactory();
	}

	public Class getObjectType() {
		return null;
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
	}
}
