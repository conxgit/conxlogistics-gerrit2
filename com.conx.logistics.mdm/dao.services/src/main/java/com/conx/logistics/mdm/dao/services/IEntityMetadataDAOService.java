package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.metadata.EntityMetadata;

public interface IEntityMetadataDAOService {
	public EntityMetadata get(long id);
	
	public List<EntityMetadata> getAll();
	
	public EntityMetadata getByClass(Class entityClass);	

	public EntityMetadata add(EntityMetadata record);

	public void delete(EntityMetadata record);

	public EntityMetadata update(EntityMetadata record);
	
	public EntityMetadata provide(EntityMetadata record) throws ClassNotFoundException;
	
	public EntityMetadata provide(Class entityClass);	
}
