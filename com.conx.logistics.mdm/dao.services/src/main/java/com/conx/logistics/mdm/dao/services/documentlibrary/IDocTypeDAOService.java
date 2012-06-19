package com.conx.logistics.mdm.dao.services.documentlibrary;

import java.util.List;

import com.conx.logistics.mdm.domain.documentlibrary.DocType;

public interface IDocTypeDAOService {
	public DocType get(long id);
	
	public List<DocType> getAll();
	
	public DocType getByCode(String code);	

	public DocType add(DocType record);

	public void delete(DocType record);

	public DocType update(DocType record);
	
	public DocType provide(DocType record);
	
	public DocType provide(String code, String name);
	
	public void provideDefaults();
}
