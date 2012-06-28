package com.conx.logistics.mdm.dao.services.referencenumber;

import java.util.List;

import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

public interface IReferenceNumberDAOService {
	public ReferenceNumber get(long id);
	
	public List<ReferenceNumber> getAll();
	
	public ReferenceNumber getByCode(String code);	

	public ReferenceNumber add(ReferenceNumber record);

	public void delete(ReferenceNumber record);

	public ReferenceNumber update(ReferenceNumber record);
}
