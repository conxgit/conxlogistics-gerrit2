package com.conx.logistics.app.whse.dao.services;

import java.util.List;

import com.conx.logistics.app.whse.domain.docktype.DockType;

public interface IDockTypeDAOService {
	public DockType get(long id);
	
	public List<DockType> getAll();
	
	public DockType getByCode(String code);	

	public DockType add(DockType record);

	public void delete(DockType record);

	public DockType update(DockType record);
	
	public DockType provide(DockType record);
	
	public DockType provide(String code, String name);
	
	public void provideDefaults();
}
