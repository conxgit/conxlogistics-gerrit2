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
import com.conx.logistics.mdm.dao.services.ICommercialValueDAOService;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialValue;

@Transactional
@Repository
public class CommercialValueDAOImpl implements ICommercialValueDAOService {
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
	public CommercialValue get(long id) {
		return em.getReference(CommercialValue.class, id);
	}    

	@Override
	public List<CommercialValue> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.commercialrecord.CommercialValue o record by o.id",CommercialValue.class).getResultList();
	}
	
	@Override
	public CommercialValue getByCode(String code) {
		CommercialValue org = null;
		
		try
		{
			TypedQuery<CommercialValue> q = em.createQuery("select o from com.conx.logistics.mdm.domain.commercialrecord.CommercialValue o WHERE o.code = :code",CommercialValue.class);
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
	public CommercialValue add(CommercialValue record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(CommercialValue record) {
		em.remove(record);
	}

	@Override
	public CommercialValue update(CommercialValue record) {
		return em.merge(record);
	}


	@Override
	public CommercialValue provide(CommercialValue record) {
		CommercialValue existingValue = getByCode(record.getCode());
		if (Validator.isNull(existingValue))
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
	public CommercialValue provide(String code, String name) {
		CommercialValue res = null;
		if ((res = getByCode(code)) == null)
		{
			CommercialValue unit = new CommercialValue();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
}
