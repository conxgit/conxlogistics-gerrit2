package com.conx.logistics.mdm.dao.services.impl.product;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.ICommercialRecordDAOService;
import com.conx.logistics.mdm.dao.services.ICommercialValueDAOService;
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.dao.services.currency.ICurrencyUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.ICommodityDAOService;
import com.conx.logistics.mdm.dao.services.product.IDimUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IPackUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductTypeDAOService;
import com.conx.logistics.mdm.dao.services.product.IWeightUnitDAOService;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialValue;
import com.conx.logistics.mdm.domain.constants.CurrencyUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.currency.CurrencyUnit;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.product.Commodity;
import com.conx.logistics.mdm.domain.product.DimUnit;
import com.conx.logistics.mdm.domain.product.PackUnit;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.product.ProductType;
import com.conx.logistics.mdm.domain.product.WeightUnit;

@Transactional
@Repository
public class ProductDAOImpl implements IProductDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private IEntityMetadataDAOService entityMetadataDao;     
    
    @Autowired
    private ICommercialRecordDAOService commercialRecordDao;     

    @Autowired
    private ICommercialValueDAOService commercialValueDao;   
    
    @Autowired
    private ICurrencyUnitDAOService currencyUnitDao;  
    
    @Autowired
    private ICountryDAOService countryUnitDao;
    
    @Autowired
    private IPackUnitDAOService packUnitDao;
    
    @Autowired
    private IDimUnitDAOService dimUnitDao;
    
    @Autowired
    private IWeightUnitDAOService weightUnitDao;
    
    @Autowired
    private IProductTypeDAOService productTypeDao;   
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Product get(long id) {
		return em.getReference(Product.class, id);
	}    

	@Override
	public List<Product> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.product.Product o record by o.id",Product.class).getResultList();
	}
	
	@Override
	public Product getByCode(String code) {
		Product org = null;
		
		try
		{
			TypedQuery<Product> q = em.createQuery("select o from com.conx.logistics.mdm.domain.product.Product o WHERE o.code = :code",Product.class);
			q.setParameter("code", code);
						
			org = q.getSingleResult();
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return org;
	}	

	@Override
	public Product add(Product record) throws Exception {
    	record.setDateCreated(new Date());
        record.setDateLastUpdated(new Date());

        record = (Product)em.merge(record);
    	String code = (Validator.isNull(record.getCode())?"Product No. "+record.getId():record.getCode());
    	String name = (Validator.isNull(record.getName())?"Product No. "+record.getId():record.getName());
    	String description = (Validator.isNull(record.getDescription())?"Product No. "+record.getId()+" Description":record.getDescription());
    	
    	record.setCode(code);
    	record.setName(name);
    	record.setDescription(description);
    	
		/**
		 * Default Unit Conversion
		 */
/*    	if (Validator.isNull(record.get))
		ProductUnitConversion uc = new ProductUnitConversion();
		uc.setParentPackUnit(parentRcvLine.getProduct().getInnerPackUnit());
		uc.setQuantityInParentPack(new Double(1.0));
		uc.setPackCount(new Integer(1));
		uc = ProductUnitConversion.add(uc);    	*/
    	
    	if (record.getVolume() == null)
    		record.setVolume(0.0);
    	if (record.getWeight() == null)
    		record.setWeight(0.0);   
    	if (record.getLen() == null)
    		record.setLen(0.0);     
    	if (record.getHeight() == null)
    		record.setHeight(0.0);      	
    	if (record.getWidth() == null)
    		record.setWidth(0.0);
    	
    	try {
			record = update(record);
		} catch (Exception e) {
			throw  e;
		}
    	
		return record;
	}

	@Override
	public void delete(Product record) {
		em.remove(record);
	}

	@Override
	public Product update(Product record) {
		return em.merge(record);
	}


	@Override
	public Product provide(Product record) {
		Product existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
			try {
				//em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return record;
	}

	@Override
	public Product provide(String productCode, String productDescription,
			String productTypeCode,
			String innerPackUnitCode, String weightUnitCode,
			String dimUnitCode, String volUnitCode, String commodityCode,
			Long warehousePK) throws Exception {
	   	Product prod = getByCode(productCode);
    	if (prod == null)
    	{
    		prod = new Product();
    		prod.setCode(productCode);
    		prod.setName(productCode);
    		prod.setDescription(productDescription);
    		prod = add(prod);
    		
    		/**
    		 * Commercial Record
    		 */
    		CommercialRecord cr = new CommercialRecord();
    		DefaultEntityMetadata emd = entityMetadataDao.provide(Product.class);
    		cr.setParentEntityMetadata(emd);
    		cr.setParentEntityId(prod.getId());
    		cr = commercialRecordDao.add(cr);
    		
    		//Country us = countryUnitDao.provide(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_CODE, CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_NAME);
    		CurrencyUnit curr = currencyUnitDao.getByCode(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_CODE);
    		CommercialValue cv = new CommercialValue();
    		cv.setCurrency(curr);
    		cv.setParentCommercialRecord(cr);
    		cv = commercialValueDao.add(cv);
    		cr.setCommercialValue(cv);
    		prod.setCommercialRecord(cr);  
    		
    		
    		//--Inner pack unit
    		PackUnit innerPackUnit = packUnitDao.provide(innerPackUnitCode, innerPackUnitCode);
    		prod.setInnerPackUnit(innerPackUnit);
    		
    		//--Weight unit
    		WeightUnit weightUnit = weightUnitDao.provide(weightUnitCode, weightUnitCode);
    		prod.setWeightUnit(weightUnit);
    		
    		//--Dim unit
    		DimUnit dimUnit = dimUnitDao.provide(dimUnitCode, dimUnitCode);
    		prod.setDimUnit(dimUnit);
    		
    		//--Vol unit
    		DimUnit volUnit = dimUnitDao.provide(volUnitCode, volUnitCode);
    		prod.setVolUnit(volUnit);
    		
    		//-- Commodity 
    		//Commodity commodity = commodityDao.provide(commodityCode, commodityCode);
    		//prod.setCommodity(commodity);
    		
    		//-- Product Type
    		ProductType pt = productTypeDao.getByCode(productTypeCode);
    		prod.setProductType(pt);
    		
    		prod = add(prod);
    		
    	}
    	return prod;
	}

	
	
	
}
