package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.PackUnit;

public interface IPackUnitDAOService {
	public PackUnit get(long id);
	
	public List<PackUnit> getAll();
	
	public PackUnit getByCode(String code);	

	public PackUnit add(PackUnit record);

	public void delete(PackUnit record);

	public PackUnit update(PackUnit record);
	
	public PackUnit provide(PackUnit record);
	
	public PackUnit provide(String code, String name);
	
	public void provideDefaults();
}
