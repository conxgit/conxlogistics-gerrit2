package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.organization.Contact;

public interface IContactDAOService {
	public Contact get(long id);
	
	public List<Contact> getAll();
	
	public Contact getByCode(String code);	

	public Contact add(Contact record);

	public void delete(Contact record);

	public Contact update(Contact record);
	
	public Contact provide(Contact record);
	
	public Contact provide(DefaultEntityMetadata entityMetadata, Long entityPK, Contact record);
	
	public void provideDefaults();
}
