package com.conx.logistics.mdm.dao.services;

import java.util.List;

import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;


public interface ICommercialRecordDAOService {
	public CommercialRecord get(long id);
	
	public List<CommercialRecord> getAll();
	
	public CommercialRecord getByCode(String code);	

	public CommercialRecord add(CommercialRecord record);

	public void delete(CommercialRecord record);

	public CommercialRecord update(CommercialRecord record);
	
	public CommercialRecord provide(CommercialRecord record);
	
	public CommercialRecord provide(String code, String name);
}
