package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.Commodity;

public interface ICommodityDAOService {
	public Commodity get(long id);
	
	public List<Commodity> getAll();
	
	public Commodity getByCode(String code);	

	public Commodity add(Commodity record);

	public void delete(Commodity record);

	public Commodity update(Commodity record);
	
	public Commodity provide(Commodity record);
	
	public Commodity provide(String code, String name);
}
