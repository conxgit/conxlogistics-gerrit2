package com.conx.logistics.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.dao.services.ICountryStateDAOService;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;

@Transactional
@Repository
public class CountryStateDAOImpl implements ICountryStateDAOService {
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
	public CountryState get(long id) {
		return em.getReference(CountryState.class, id);
	}    

	@Override
	public List<CountryState> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.CountryState o record by o.id",CountryState.class).getResultList();
	}
	
	@Override
	public CountryState getByCode(String code) {
		CountryState org = null;
		
		try
		{
			TypedQuery<CountryState> q = em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.CountryState o WHERE o.code = :code",CountryState.class);
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
	public CountryState add(CountryState record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(CountryState record) {
		em.remove(record);
	}

	@Override
	public CountryState update(CountryState record) {
		return em.merge(record);
	}


	@Override
	public CountryState provide(CountryState record) {
		CountryState existingRecord = getByCode(record.getCode());
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
	public CountryState provide(String code, 
			 String name,
			 Long countryPK)
	{
		CountryState res = null;
		if ((res = getByCode(code)) == null)
		{
			CountryState cs = new CountryState();
			
			Country country= countryDao.get(countryPK);
			cs.setCountry(country);

			cs.setCode(code);
			cs.setName(name);
			
			res = add(cs);
		}
		return res;		
	}
}
