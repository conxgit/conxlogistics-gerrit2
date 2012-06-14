package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.organization.Organization;


public interface IOrganizationDAOService {
	public Organization get(long id);
	
	public List<Organization> getAll();
	
	public Organization getByCode(String code);	

	public Organization add(Organization record);

	public void delete(Organization record);

	public Organization update(Organization record);
	
	public Organization provide(Organization record);
}
