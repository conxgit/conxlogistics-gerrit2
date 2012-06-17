package com.conx.logistics.mdm.dao.services.impl.referencenumber;

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
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberTypeDAOService;
import com.conx.logistics.mdm.domain.constants.ReferenceNumberTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;

@Transactional
@Repository
public class ReferenceNumberTypeDAOImpl implements IReferenceNumberTypeDAOService {
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
	public ReferenceNumberType get(long id) {
		return em.getReference(ReferenceNumberType.class, id);
	}    

	@Override
	public List<ReferenceNumberType> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType o record by o.id",ReferenceNumberType.class).getResultList();
	}
	
	@Override
	public ReferenceNumberType getByCode(String code) {
		ReferenceNumberType org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ReferenceNumberType> query = builder.createQuery(ReferenceNumberType.class);
			Root<ReferenceNumberType> rootEntity = query.from(ReferenceNumberType.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<ReferenceNumberType> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();			
			//TypedQuery<ReferenceNumberType> q = em.createQuery("select o from com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType o WHERE o.code = :code",ReferenceNumberType.class);
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
	public ReferenceNumberType add(ReferenceNumberType record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ReferenceNumberType record) {
		em.remove(record);
	}

	@Override
	public ReferenceNumberType update(ReferenceNumberType record) {
		return em.merge(record);
	}


	@Override
	public ReferenceNumberType provide(ReferenceNumberType record) {
		ReferenceNumberType existingRecord = getByCode(record.getCode());
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
	public ReferenceNumberType provide(String code, String name) {
		ReferenceNumberType res = null;
		if ((res = getByCode(code)) == null)
		{
			ReferenceNumberType unit = new ReferenceNumberType();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_PO, ReferenceNumberTypeCustomCONSTANTS.TYPE_PO_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_FEDEX, ReferenceNumberTypeCustomCONSTANTS.TYPE_FEDEX_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_UPS, ReferenceNumberTypeCustomCONSTANTS.TYPE_UPS_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_CarrierId, ReferenceNumberTypeCustomCONSTANTS.TYPE_CarrierId_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_NO_REF, ReferenceNumberTypeCustomCONSTANTS.TYPE_NO_REF_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_BOL, ReferenceNumberTypeCustomCONSTANTS.TYPE_BOL_NAME);
		provide(ReferenceNumberTypeCustomCONSTANTS.TYPE_ConXGeneric, ReferenceNumberTypeCustomCONSTANTS.TYPE_ConXGeneric_NAME);
	}
}
