package com.conx.logistics.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
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
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.domain.metadata.EntityMetadata;

@Transactional
@Repository
public class EntityMetadataDAOImpl implements IEntityMetadataDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
    @Autowired
    private IEntityMetadataDAOService countryDao;
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public EntityMetadata get(long id) {
		return em.getReference(EntityMetadata.class, id);
	}    

	@Override
	public List<EntityMetadata> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.metadata.EntityMetadata o record by o.id",EntityMetadata.class).getResultList();
	}
	
	@Override
	public EntityMetadata getByClass(Class entityClass) {
		EntityMetadata record = null;
		
		try
		{
			TypedQuery<EntityMetadata> q = em.createQuery("select o from com.conx.logistics.mdm.domain.metadata.EntityMetadata o WHERE o.entityJavaSimpleType = :entityJavaSimpleType",EntityMetadata.class);
			q.setParameter("entityJavaSimpleType", entityClass.getSimpleName());
						
			record = q.getSingleResult();
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
		
		return record;
	}	

	@Override
	public EntityMetadata add(EntityMetadata record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(EntityMetadata record) {
		em.remove(record);
	}

	@Override
	public EntityMetadata update(EntityMetadata record) {
		return em.merge(record);
	}


	@Override
	public EntityMetadata provide(Class entityClass) {
		EntityMetadata existingRecord = getByClass(entityClass);
		if (Validator.isNull(existingRecord))
		{		
			existingRecord = new EntityMetadata();
			existingRecord.setDateCreated(new Date());
			existingRecord.setDateLastUpdated(new Date());
			existingRecord.setEntityJavaSimpleType(entityClass.getSimpleName());
			existingRecord.setEntityJavaType(entityClass.getName());
			existingRecord = add(existingRecord);
			existingRecord = update(existingRecord);
		}
		return existingRecord;
	}

	@Override
	public EntityMetadata provide(EntityMetadata record) throws ClassNotFoundException {
		return provide(Class.forName(record.getEntityJavaType()));
	}
}
