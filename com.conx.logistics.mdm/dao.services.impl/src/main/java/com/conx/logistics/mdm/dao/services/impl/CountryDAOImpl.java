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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;

@Transactional
@Repository
public class CountryDAOImpl implements ICountryDAOService {
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
	public Country get(long id) {
		return em.getReference(Country.class, id);
	}    

	@Override
	public List<Country> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.Country o record by o.id",Country.class).getResultList();
	}
	
	@Override
	public Country getByCode(String code) {
		Country org = null;
		
		try
		{
			TypedQuery<Country> q = em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.Country o WHERE o.code = :code",Country.class);
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
	public Country add(Country record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Country record) {
		em.remove(record);
	}

	@Override
	public Country update(Country record) {
		return em.merge(record);
	}


	@Override
	public Country provide(Country record) {
		Country existingRecord = getByCode(record.getCode());
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
	public Country provide(String code, String name) {
		Country res = null;
		if ((res = getByCode(code)) == null)
		{
			Country country = new Country();

			country.setCode(code);
			country.setName(name);

			res = add(country);
		}
		return res;
	}	
}
