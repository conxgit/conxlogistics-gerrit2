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
import com.conx.logistics.mdm.dao.services.product.IDimUnitDAOService;
import com.conx.logistics.mdm.domain.constants.DimUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.product.DimUnit;

@Transactional
@Repository
public class DimUnitDAOImpl implements IDimUnitDAOService {
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
	public DimUnit get(long id) {
		return em.getReference(DimUnit.class, id);
	}    

	@Override
	public List<DimUnit> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.product.DimUnit o record by o.id",DimUnit.class).getResultList();
	}
	
	@Override
	public DimUnit getByCode(String code) {
		DimUnit org = null;
		
		try
		{
			TypedQuery<DimUnit> q = em.createQuery("select o from com.conx.logistics.mdm.domain.product.DimUnit o WHERE o.code = :code",DimUnit.class);
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
	public DimUnit add(DimUnit record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(DimUnit record) {
		em.remove(record);
	}

	@Override
	public DimUnit update(DimUnit record) {
		return em.merge(record);
	}


	@Override
	public DimUnit provide(DimUnit record) {
		DimUnit existingRecord = getByCode(record.getCode());
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
	public DimUnit provide(String code, String name) {
		DimUnit res = null;
		if ((res = getByCode(code)) == null)
		{
			DimUnit unit = new DimUnit();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(DimUnitCustomCONSTANTS.TYPE_IN, DimUnitCustomCONSTANTS.TYPE_IN_DESCRIPTION);
		provide(DimUnitCustomCONSTANTS.TYPE_FT, DimUnitCustomCONSTANTS.TYPE_FT_DESCRIPTION);
		provide(DimUnitCustomCONSTANTS.TYPE_M, DimUnitCustomCONSTANTS.TYPE_M_DESCRIPTION);
		provide(DimUnitCustomCONSTANTS.TYPE_YD, DimUnitCustomCONSTANTS.TYPE_YD_DESCRIPTION);
	}
}
