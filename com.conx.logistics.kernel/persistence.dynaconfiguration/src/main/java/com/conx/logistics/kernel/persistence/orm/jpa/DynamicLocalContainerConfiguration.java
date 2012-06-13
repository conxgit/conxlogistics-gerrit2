package com.conx.logistics.kernel.persistence.orm.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * Hibernate session factory that can get updated during the runtime of the
 * application.
 */
public class DynamicLocalContainerConfiguration implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(DynamicLocalContainerConfiguration.class);

	/**
	 * LocalContainerEntityManagerFactoryBean props
	 */
    private String persistenceUnitName;
    private ConfigBundleTrackingPersistenceUnitManager persistenceUnitManager;
    private JpaVendorAdapter jpaVendorAdapter;
    private JpaDialect jpaDialect;

	
	public void setJpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter) {
		this.jpaVendorAdapter = jpaVendorAdapter;
	}

	public void setJpaDialect(JpaDialect jpaDialect) {
		this.jpaDialect = jpaDialect;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public void setPersistenceUnitManager(
			ConfigBundleTrackingPersistenceUnitManager persistenceUnitManager) {
		this.persistenceUnitManager = persistenceUnitManager;
	}




	/**
	 * 
	 */
	private List<Class> annotatedClasses = new ArrayList<Class>();

	private Properties hibernateProperties;

	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	private DataSource dataSource;

	private int myhashCode;

	public void setHibernateProperties(Properties hibernateProperties) {
		this.hibernateProperties = hibernateProperties;
	}

	public void setAnnotatedClasses(List<Class> annotatedClasses) {
		this.annotatedClasses = annotatedClasses;
	}

	public void addAnnotatedClass(Class anotadedClass) throws Exception {
		annotatedClasses.add(anotadedClass);
		afterPropertiesSet();
	}
	
	public void removeAnnotatedClass(Class anotadedClass) throws Exception {
		annotatedClasses.remove(anotadedClass);
		afterPropertiesSet();
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		return entityManagerFactoryBean;
	}


	private SessionFactory sessionFactory;

	public void createNewEntityManagerFactory() {
		
		logger.info("Creating new entity manager factory...");
		
		if (hibernateProperties == null) {
			throw new IllegalStateException(
					"Hibernate properties have not been set yet");
		}
		
		if (entityManagerFactoryBean == null)
		{
			entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactoryBean.setJpaProperties(hibernateProperties);
			entityManagerFactoryBean.setPersistenceUnitName(persistenceUnitName);
			entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
			entityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManager);
		}
		entityManagerFactoryBean.afterPropertiesSet();
		
		
		logger.info("Created new session factory: " + sessionFactory);
	}

	public void afterPropertiesSet() throws Exception {
		persistenceUnitManager.setDynamicConfiguration(this);
		createNewEntityManagerFactory();
	}
}
