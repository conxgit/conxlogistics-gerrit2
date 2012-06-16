package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.DimUnit;

public interface IDimUnitDAOService {
	public DimUnit get(long id);
	
	public List<DimUnit> getAll();
	
	public DimUnit getByCode(String code);	

	public DimUnit add(DimUnit record);

	public void delete(DimUnit record);

	public DimUnit update(DimUnit record);
	
	public DimUnit provide(DimUnit record);
	
	public DimUnit provide(String code, String name);
	
	public void provideDefaults();
}
