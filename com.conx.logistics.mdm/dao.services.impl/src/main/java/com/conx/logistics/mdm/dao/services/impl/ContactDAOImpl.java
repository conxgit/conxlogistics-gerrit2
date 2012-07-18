package com.conx.logistics.mdm.dao.services.impl;

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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.IContactDAOService;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.organization.Contact;

@Transactional
@Repository
public class ContactDAOImpl implements IContactDAOService {
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
	public Contact get(long id) {
		return em.getReference(Contact.class, id);
	}    

	@Override
	public List<Contact> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.organization.Contact o record by o.id",Contact.class).getResultList();
	}
	

	public Contact getByMetadataAndId(DefaultEntityMetadata entityMetadata, Long entityPK) {
		Contact res = null;
		try {
			TypedQuery<Contact> q = em
					.createQuery(
							"SELECT Contact FROM com.conx.logistics.mdm.domain.organization.Contact AS contact WHERE contact.entityMetadata = :entityMetadata AND contact.entityPK = :entityPK",
							Contact.class);
			q.setParameter("entityMetadata", entityMetadata);
			q.setParameter("entityPK", entityPK);
			res = q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return res;
	}	
	
	@Override
	public Contact getByCode(String code) {
		Contact org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Contact> query = builder.createQuery(Contact.class);
			Root<Contact> rootEntity = query.from(Contact.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<Contact> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();			
			//TypedQuery<Contact> q = em.createQuery("select o from com.conx.logistics.mdm.domain.organization.Contact o WHERE o.code = :code",Contact.class);
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
	public Contact add(Contact record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Contact record) {
		em.remove(record);
	}

	@Override
	public Contact update(Contact record) {
		return em.merge(record);
	}


	@Override
	public Contact provide(Contact record) {
		Contact existingRecord = getByCode(record.getCode());
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
	public Contact provide(DefaultEntityMetadata entityMetadata, Long entityPK, Contact record) {
		Contact res = null;
		res = getByMetadataAndId(entityMetadata,entityPK);
		if (Validator.isNull(res)) {
			record.setCode(record.getFirstName()+"."+record.getLastName());
			record.setEntityMetadata(entityMetadata);
			record.setEntityMetadata(entityMetadata);
			record.setEntityPK(entityPK);
			res = add(record);
		}
		
		return res;
	}	
	
	@Override
	public void provideDefaults() {
	}
}
