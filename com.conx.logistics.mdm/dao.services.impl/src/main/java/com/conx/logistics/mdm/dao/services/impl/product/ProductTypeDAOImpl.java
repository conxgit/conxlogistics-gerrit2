package com.conx.logistics.mdm.dao.services.impl.product;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.product.IProductTypeDAOService;
import com.conx.logistics.mdm.domain.constants.ProductTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.product.ProductType;

@Transactional
@Repository
public class ProductTypeDAOImpl implements IProductTypeDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext(unitName="pu")
    private EntityManager em;	
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public ProductType get(long id) {
		return em.getReference(ProductType.class, id);
	}    

	@Override
	public List<ProductType> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.product.ProductType o record by o.id",ProductType.class).getResultList();
	}
	
	@Override
	public ProductType getByCode(String code) {
		ProductType org = null;
		
		try
		{
			TypedQuery<ProductType> q = em.createQuery("select o from com.conx.logistics.mdm.domain.product.ProductType o WHERE o.code = :code",ProductType.class);
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
	public ProductType add(ProductType record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ProductType record) {
		em.remove(record);
	}

	@Override
	public ProductType update(ProductType record) {
		return em.merge(record);
	}


	@Override
	public ProductType provide(ProductType record) {
		ProductType existingRecord = getByCode(record.getCode());
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
	public ProductType provide(String code, String name) {
		ProductType res = null;
		if ((res = getByCode(code)) == null)
		{
			ProductType unit = new ProductType();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(ProductTypeCustomCONSTANTS.TYPE_Printer_Matter, ProductTypeCustomCONSTANTS.TYPE_Printer_Matter);
		provide(ProductTypeCustomCONSTANTS.TYPE_Food_Item, ProductTypeCustomCONSTANTS.TYPE_Food_Item);
		provide(ProductTypeCustomCONSTANTS.TYPE_Hazardous_Material, ProductTypeCustomCONSTANTS.TYPE_Hazardous_Material);
		provide(ProductTypeCustomCONSTANTS.TYPE_Textiles, ProductTypeCustomCONSTANTS.TYPE_Textiles);
		provide(ProductTypeCustomCONSTANTS.TYPE_ConXGeneric, ProductTypeCustomCONSTANTS.TYPE_ConXGeneric);
		provide(ProductTypeCustomCONSTANTS.TYPE_AD_HOC, ProductTypeCustomCONSTANTS.TYPE_AD_HOC);
	}
}
