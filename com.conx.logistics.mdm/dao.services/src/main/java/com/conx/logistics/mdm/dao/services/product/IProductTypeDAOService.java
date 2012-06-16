package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.ProductType;

public interface IProductTypeDAOService {
	public ProductType get(long id);
	
	public List<ProductType> getAll();
	
	public ProductType getByCode(String code);	

	public ProductType add(ProductType record);

	public void delete(ProductType record);

	public ProductType update(ProductType record);
	
	public ProductType provide(ProductType record);
	
	public ProductType provide(String code, String name);
	
	public void provideDefaults();
}
