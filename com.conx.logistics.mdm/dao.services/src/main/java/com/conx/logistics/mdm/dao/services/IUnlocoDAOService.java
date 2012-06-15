package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.geolocation.Unloco;

public interface IUnlocoDAOService {
	public Unloco get(long id);
	
	public List<Unloco> getAll();
	
	public Unloco getByCode(String code);	

	public Unloco add(Unloco record);

	public void delete(Unloco record);

	public Unloco update(Unloco record);
	
	public Unloco provide(Unloco record);
	
	public Unloco provide(String code, 
							 String description,
							 String portCity,
							 Long countryPK,
							 Long countryStatePK);
}
