package com.conx.logistics.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
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
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;

@Transactional
@Repository
public class EntityMetadataDAOImpl implements IEntityMetadataDAOService {
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
	public DefaultEntityMetadata get(long id) {
		return em.getReference(DefaultEntityMetadata.class, id);
	}    

	@Override
	public List<DefaultEntityMetadata> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata o record by o.id",DefaultEntityMetadata.class).getResultList();
	}
	
	@Override
	public DefaultEntityMetadata getByClass(Class entityClass) {
		DefaultEntityMetadata record = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DefaultEntityMetadata> query = builder.createQuery(DefaultEntityMetadata.class);
			Root<DefaultEntityMetadata> rootEntity = query.from(DefaultEntityMetadata.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("entityJavaSimpleType"), p));

			TypedQuery<DefaultEntityMetadata> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, entityClass.getSimpleName());
			
			return typedQuery.getSingleResult();
			//TypedQuery<DefaultEntityMetadata> q = em.createQuery("select DISTINCT  o from com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata o WHERE o.entityJavaSimpleType = :entityJavaSimpleType",DefaultEntityMetadata.class);
			//q.setParameter("entityJavaSimpleType", entityClass.getSimpleName());
			//record = q.getSingleResult();
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
	public DefaultEntityMetadata add(DefaultEntityMetadata record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(DefaultEntityMetadata record) {
		em.remove(record);
	}

	@Override
	public DefaultEntityMetadata update(DefaultEntityMetadata record) {
		return em.merge(record);
	}


	@Override
	public DefaultEntityMetadata provide(Class entityClass) {
		DefaultEntityMetadata existingRecord = getByClass(entityClass);
		if (Validator.isNull(existingRecord))
		{		
			existingRecord = new DefaultEntityMetadata();
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
	public DefaultEntityMetadata provide(DefaultEntityMetadata record) throws ClassNotFoundException {
		return provide(Class.forName(record.getEntityJavaType()));
	}
}
