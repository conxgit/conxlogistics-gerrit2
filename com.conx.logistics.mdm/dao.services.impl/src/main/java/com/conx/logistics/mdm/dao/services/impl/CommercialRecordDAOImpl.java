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
import com.conx.logistics.mdm.dao.services.ICommercialRecordDAOService;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;

@Transactional
@Repository
public class CommercialRecordDAOImpl implements ICommercialRecordDAOService {
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
	public CommercialRecord get(long id) {
		return em.getReference(CommercialRecord.class, id);
	}    

	@Override
	public List<CommercialRecord> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord o record by o.id",CommercialRecord.class).getResultList();
	}
	
	@Override
	public CommercialRecord getByCode(String code) {
		CommercialRecord org = null;
		
		try
		{
			TypedQuery<CommercialRecord> q = em.createQuery("select o from com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord o WHERE o.code = :code",CommercialRecord.class);
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
	public CommercialRecord add(CommercialRecord record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(CommercialRecord record) {
		em.remove(record);
	}

	@Override
	public CommercialRecord update(CommercialRecord record) {
		return em.merge(record);
	}


	@Override
	public CommercialRecord provide(CommercialRecord record) {
		CommercialRecord existingRecord = getByCode(record.getCode());
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
	public CommercialRecord provide(String code, String name) {
		CommercialRecord res = null;
		if ((res = getByCode(code)) == null)
		{
			CommercialRecord unit = new CommercialRecord();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
}
