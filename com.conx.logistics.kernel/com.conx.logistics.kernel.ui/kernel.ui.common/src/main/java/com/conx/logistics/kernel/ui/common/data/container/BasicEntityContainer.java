package com.conx.logistics.kernel.ui.common.data.container;

import javax.persistence.EntityManager;

import com.conx.logistics.mdm.domain.BaseEntity;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;

public class BasicEntityContainer<T extends BaseEntity> extends JPAContainer<T> {

	public BasicEntityContainer(Class<T> entityClass, EntityManager entityManager) {
		super(entityClass);
		setEntityProvider(new CachingLocalEntityProvider<T>(
				entityClass,
				entityManager));		
	}
}
