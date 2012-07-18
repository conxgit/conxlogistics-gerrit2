package com.conx.logistics.mdm.dao.services.impl.currency;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.dao.services.currency.ICurrencyUnitDAOService;
import com.conx.logistics.mdm.domain.constants.CurrencyUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.currency.CurrencyUnit;
import com.conx.logistics.mdm.domain.geolocation.Country;

@Transactional
@Repository
public class CurrencyUnitDAOImpl implements ICurrencyUnitDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext(unitName="pu")
    private EntityManager em;	
    
    @Autowired
    private ICountryDAOService countryDao;     
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public CurrencyUnit get(long id) {
		return em.getReference(CurrencyUnit.class, id);
	}    

	@Override
	public List<CurrencyUnit> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.currency.CurrencyUnit o record by o.id",CurrencyUnit.class).getResultList();
	}
	
	@Override
	public CurrencyUnit getByCode(String code) {
		CurrencyUnit org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CurrencyUnit> query = builder.createQuery(CurrencyUnit.class);
			Root<CurrencyUnit> rootEntity = query.from(CurrencyUnit.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<CurrencyUnit> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();				
			//TypedQuery<CurrencyUnit> q = em.createQuery("select o from com.conx.logistics.mdm.domain.currency.CurrencyUnit o WHERE o.code = :code",CurrencyUnit.class);
			//q.setParameter("code", code);
						
			//org = q.getSingleResult();
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
	public CurrencyUnit add(CurrencyUnit record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(CurrencyUnit record) {
		em.remove(record);
	}

	@Override
	public CurrencyUnit update(CurrencyUnit record) {
		return em.merge(record);
	}


	@Override
	public CurrencyUnit provide(CurrencyUnit record) {
		CurrencyUnit existingRecord = getByCode(record.getCode());
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
	public CurrencyUnit provide(String code, String name, Country country) {
		CurrencyUnit res = null;
		if ((res = getByCode(code)) == null)
		{
			CurrencyUnit unit = new CurrencyUnit();

			unit.setCode(code);
			unit.setName(name);
			unit.setCountry(country);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		Country country = countryDao.provide(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_CODE, CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_NAME);
		provide(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_CODE,CurrencyUnitCustomCONSTANTS.CURRENCY_USD_NAME,country);
	}
}
