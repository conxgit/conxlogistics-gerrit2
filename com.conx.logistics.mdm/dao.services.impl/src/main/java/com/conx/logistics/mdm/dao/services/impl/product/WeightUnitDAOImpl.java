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
import com.conx.logistics.mdm.dao.services.product.IWeightUnitDAOService;
import com.conx.logistics.mdm.domain.constants.WeightUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.product.WeightUnit;

@Transactional
@Repository
public class WeightUnitDAOImpl implements IWeightUnitDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public WeightUnit get(long id) {
		return em.getReference(WeightUnit.class, id);
	}    

	@Override
	public List<WeightUnit> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.product.WeightUnit o record by o.id",WeightUnit.class).getResultList();
	}
	
	@Override
	public WeightUnit getByCode(String code) {
		WeightUnit org = null;
		
		try
		{
			TypedQuery<WeightUnit> q = em.createQuery("select o from com.conx.logistics.mdm.domain.product.WeightUnit o WHERE o.code = :code",WeightUnit.class);
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
	public WeightUnit add(WeightUnit record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(WeightUnit record) {
		em.remove(record);
	}

	@Override
	public WeightUnit update(WeightUnit record) {
		return em.merge(record);
	}


	@Override
	public WeightUnit provide(WeightUnit record) {
		WeightUnit existingRecord = getByCode(record.getCode());
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
	public WeightUnit provide(String code, String name) {
		WeightUnit res = null;
		if ((res = getByCode(code)) == null)
		{
			WeightUnit unit = new WeightUnit();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(WeightUnitCustomCONSTANTS.TYPE_DT, WeightUnitCustomCONSTANTS.TYPE_DT_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_G, WeightUnitCustomCONSTANTS.TYPE_G_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_KG, WeightUnitCustomCONSTANTS.TYPE_KG_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_KT, WeightUnitCustomCONSTANTS.TYPE_KT_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_LB, WeightUnitCustomCONSTANTS.TYPE_LB_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_LT, WeightUnitCustomCONSTANTS.TYPE_LT_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_MC, WeightUnitCustomCONSTANTS.TYPE_MC_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_MG, WeightUnitCustomCONSTANTS.TYPE_MG_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_OT, WeightUnitCustomCONSTANTS.TYPE_OT_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_OZ, WeightUnitCustomCONSTANTS.TYPE_OZ_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_T, WeightUnitCustomCONSTANTS.TYPE_T_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_TL, WeightUnitCustomCONSTANTS.TYPE_TL_DESCRIPTION);
		provide(WeightUnitCustomCONSTANTS.TYPE_TN, WeightUnitCustomCONSTANTS.TYPE_TN_DESCRIPTION);
	}
}
