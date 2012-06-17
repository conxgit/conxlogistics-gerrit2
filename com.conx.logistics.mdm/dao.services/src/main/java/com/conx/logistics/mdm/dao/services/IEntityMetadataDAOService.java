package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;

public interface IEntityMetadataDAOService {
	public DefaultEntityMetadata get(long id);
	
	public List<DefaultEntityMetadata> getAll();
	
	public DefaultEntityMetadata getByClass(Class entityClass);	

	public DefaultEntityMetadata add(DefaultEntityMetadata record);

	public void delete(DefaultEntityMetadata record);

	public DefaultEntityMetadata update(DefaultEntityMetadata record);
	
	public DefaultEntityMetadata provide(DefaultEntityMetadata record) throws ClassNotFoundException;
	
	public DefaultEntityMetadata provide(Class entityClass);	
}
