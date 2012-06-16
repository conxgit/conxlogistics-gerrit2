package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.commercialrecord.CommercialValue;


public interface ICommercialValueDAOService {
	public CommercialValue get(long id);
	
	public List<CommercialValue> getAll();
	
	public CommercialValue getByCode(String code);	

	public CommercialValue add(CommercialValue record);

	public void delete(CommercialValue record);

	public CommercialValue update(CommercialValue record);
	
	public CommercialValue provide(CommercialValue record);
	
	public CommercialValue provide(String code, String name);
}
