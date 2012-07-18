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
import com.conx.logistics.mdm.dao.services.IUnlocoDAOService;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;

@Transactional
@Repository
public class UnlocoDAOImpl implements IUnlocoDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext(unitName="pu")
    private EntityManager em;	
    
    @Autowired
    private ICountryDAOService countryDao;
    
    @Autowired
    private ICountryStateDAOService countryStateDao;    
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Unloco get(long id) {
		return em.getReference(Unloco.class, id);
	}    

	@Override
	public List<Unloco> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.Unloco o record by o.id",Unloco.class).getResultList();
	}
	
	@Override
	public Unloco getByCode(String code) {
		Unloco org = null;
		
		try
		{
			TypedQuery<Unloco> q = em.createQuery("select o from com.conx.logistics.mdm.domain.geolocation.Unloco o WHERE o.code = :code",Unloco.class);
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
	public Unloco add(Unloco record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Unloco record) {
		em.remove(record);
	}

	@Override
	public Unloco update(Unloco record) {
		return em.merge(record);
	}


	@Override
	public Unloco provide(Unloco record) {
		Unloco existingRecord = getByCode(record.getCode());
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
	public Unloco provide(String code, String description, String portCity,
			Long countryPK, Long countryStatePK) {
		Unloco res = null;
		if ((res = getByCode(code)) == null)
		{
			Unloco unloco = new Unloco();
			
			Country country= countryDao.get(countryPK);
			unloco.setCountry(country);
			
			if (countryStatePK != null)
			{
				CountryState countryState= countryStateDao.get(countryStatePK);
				unloco.setCountryState(countryState);				
			}


			unloco.setCode(code);
			unloco.setName(description);
			unloco.setDescription(description);
			unloco.setPortCity(portCity);
			
			res = add(unloco);
		}
		return res;
	}
}
