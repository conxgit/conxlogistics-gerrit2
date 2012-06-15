package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.geolocation.Address;

public interface IAddressDAOService {
	public Address get(long id);
	
	public List<Address> getAll();
	
	public Address getByCode(String code);	
	
	public Address getByTypeAndId(String entityType, Long entityId);

	public Address add(Address record);

	public void delete(Address record);

	public Address update(Address record);
	
	public Address provide(Address record);
	
	public Address provide(String entityType, Long entityId, String street1,
			String street2, String zipCode, String stateOrProvince,
			String unlocoCode, String unlocoPortCity, String countryCode,
			String countryName, String stateCode, String stateName);	
}
