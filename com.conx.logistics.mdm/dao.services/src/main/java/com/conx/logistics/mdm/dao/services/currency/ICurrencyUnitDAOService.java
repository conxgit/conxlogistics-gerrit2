package com.conx.logistics.mdm.dao.services.currency;

import java.util.List;

import com.conx.logistics.mdm.domain.currency.CurrencyUnit;
import com.conx.logistics.mdm.domain.geolocation.Country;


public interface ICurrencyUnitDAOService {
	public CurrencyUnit get(long id);
	
	public List<CurrencyUnit> getAll();
	
	public CurrencyUnit getByCode(String code);	

	public CurrencyUnit add(CurrencyUnit record);

	public void delete(CurrencyUnit record);

	public CurrencyUnit update(CurrencyUnit record);
	
	public CurrencyUnit provide(CurrencyUnit record);
	
	public CurrencyUnit provide(String code, String name, Country country);
	
	public void provideDefaults();
}
