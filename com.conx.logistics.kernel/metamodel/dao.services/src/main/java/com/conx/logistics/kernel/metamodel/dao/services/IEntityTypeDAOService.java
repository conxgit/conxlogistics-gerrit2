package com.conx.logistics.kernel.metamodel.dao.services;

import java.util.List;

import javax.persistence.metamodel.IdentifiableType;

import com.conx.logistics.kernel.metamodel.domain.AbstractAttribute;
import com.conx.logistics.kernel.metamodel.domain.BasicAttribute;
import com.conx.logistics.kernel.metamodel.domain.EntityType;


public interface IEntityTypeDAOService {
	public EntityType get(long id);
	
	public List<EntityType> getAll();
	
	public EntityType getByClass(Class entityClass);	
	
	public AbstractAttribute getAttribute(Long entityTypeId, String name);	

	public EntityType add(EntityType record);

	public void delete(EntityType record);

	public EntityType update(EntityType record);
	
	public EntityType provide(IdentifiableType record) throws ClassNotFoundException;

	public List<BasicAttribute> getAllBasicAttributesByEntityType(EntityType parentEntityType);
}
