package com.conx.logistics.mdm.dao.services.product;

import java.util.List;

import com.conx.logistics.mdm.domain.product.Product;

public interface IProductDAOService {
	public Product get(long id);
	
	public List<Product> getAll();
	
	public Product getByCode(String code);	

	public Product add(Product record) throws Exception;

	public void delete(Product record);

	public Product update(Product record);
	
	public Product provide(Product record);
	
	public Product provide(String productCode,
			  String productDescription,
			  String productTypeCode,
		      String innerPackUnitCode,
			  String weightUnitCode,
			  String dimUnitCode,
			  String volUnitCode,
			  String commodityCode,
			  Long warehousePK) throws Exception;	
}
