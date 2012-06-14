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

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.domain.application.Application;
import com.conx.logistics.mdm.domain.application.Feature;
import com.conx.logistics.mdm.domain.organization.Organization;

public class OrganizationDAOImpl implements IOrganizationDAOService {
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
	public Organization get(long id) {
		return em.getReference(Organization.class, id);
	}    

	@Override
	public List<Organization> getAll() {
		return em.createQuery("select o from Organization o order by o.id",Organization.class).getResultList();
	}
	
	@Override
	public Organization getByCode(String code) {
		Organization org = null;
		
		try
		{
			TypedQuery<Organization> q = em.createQuery("select o from Organization o WHERE o.code = :code",Organization.class);
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
	public Organization add(Organization record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Organization record) {
		em.remove(app);
	}

	@Override
	public Organization update(Organization record) {
		return em.merge(app);
	}


	@Override
	public Organization provide(Organization record) {
		Organization existingRecord = getByCode(record.getCode());
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
}
