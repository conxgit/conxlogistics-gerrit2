package com.conx.logistics.kernel.ui.common.data.container;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.vaadin.data.Container;

public class EntityTypeContainerFactory {
	private static final Map<Class<?>,Container> entityTypeContainers = Collections
			.synchronizedMap(new HashMap<Class<?>,Container>());
	private EntityManager entityManager;
	
	public EntityTypeContainerFactory(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public Container createEntityTypeContainer(Class<?> entityType)
	{
		Container container = null;
		/**
		 * Hack: for now we assume all entities are served by KernelSystem EntityFactoryManager
		 * 
		 * A solution has to be such that entityType's can be transparently assoc. with corresp. E.F.M's
		 * 
		 */
		if (entityTypeContainers.containsKey(entityType))
			return entityTypeContainers.get(entityType);
		else
		{
			//--1. Create entity container
			container = new BasicEntityContainer(entityType,entityManager);
			entityTypeContainers.put(entityType, container);
		}
		
		return container;
	}
}
