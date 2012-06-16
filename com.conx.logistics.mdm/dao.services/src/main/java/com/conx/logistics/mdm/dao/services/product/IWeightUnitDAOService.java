package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.WeightUnit;

public interface IWeightUnitDAOService {
	public WeightUnit get(long id);
	
	public List<WeightUnit> getAll();
	
	public WeightUnit getByCode(String code);	

	public WeightUnit add(WeightUnit record);

	public void delete(WeightUnit record);

	public WeightUnit update(WeightUnit record);
	
	public WeightUnit provide(WeightUnit record);
	
	public WeightUnit provide(String code, String name);
	
	public void provideDefaults();
}
