package com.conx.logistics.mdm.dao.services.impl.documentlibrary;

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
import com.conx.logistics.mdm.dao.services.documentlibrary.IDocTypeDAOService;
import com.conx.logistics.mdm.domain.constants.DocTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.documentlibrary.DocType;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

@Transactional
@Repository
public class DocTypeDAOImpl implements IDocTypeDAOService {
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
	public DocType get(long id) {
		return em.getReference(DocType.class, id);
	}    

	@Override
	public List<DocType> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.documentlibrary.DocType o record by o.id",DocType.class).getResultList();
	}
	
	@Override
	public DocType getByCode(String code) {
		DocType org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DocType> query = builder.createQuery(DocType.class);
			Root<DocType> rootEntity = query.from(DocType.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<DocType> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();			
//			TODO
//			TypedQuery<DocType> q = em.createQuery("select o from com.conx.logistics.mdm.domain.documentlibrary.DocType o WHERE o.code = :code",DocType.class);
//			q.setParameter("code", code);
//						
//			org = q.getSingleResult();
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
	public DocType add(DocType record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(DocType record) {
		em.remove(record);
	}

	@Override
	public DocType update(DocType record) {
		return em.merge(record);
	}


	@Override
	public DocType provide(DocType record) {
		DocType existingRecord = getByCode(record.getCode());
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
	public DocType provide(String code, String name) {
		DocType res = null;
		if ((res = getByCode(code)) == null)
		{
			DocType unit = new DocType();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(DocTypeCustomCONSTANTS.TYPE_PLATFORM, DocTypeCustomCONSTANTS.TYPE_PLATFORM_DESCRIPTION);
	}
}
