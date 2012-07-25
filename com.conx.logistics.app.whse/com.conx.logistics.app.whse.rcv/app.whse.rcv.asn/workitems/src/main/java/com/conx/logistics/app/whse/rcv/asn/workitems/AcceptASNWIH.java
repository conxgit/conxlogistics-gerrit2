package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.app.whse.domain.docktype.DockType;
import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberTypeDAOService;
import com.conx.logistics.mdm.domain.constants.ReferenceNumberTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;

@Transactional
@Repository
public class AcceptASNWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(AcceptASNWIH.class);

	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IASNDAOService asnDao;

	@Autowired
	private UserTransaction userTransaction;

	private PlatformTransactionManager globalJtaTransactionManager;

	@Autowired
	private IReferenceNumberDAOService referenceNumberDao;
	
	@Autowired
	private IReferenceNumberTypeDAOService referenceNumberTypeDao;
	
	

	public void setGlobalJtaTransactionManager(
			PlatformTransactionManager globalJtaTransactionManager) {
		this.globalJtaTransactionManager = globalJtaTransactionManager;
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		try {
			// em.joinTransaction();

			ASN asnIn = (ASN) workItem.getParameter("asnIn");

			Map<String, Object> asnRefNumMapIn = (Map<String, Object>) workItem
					.getParameter("asnRefNumMapIn");
			Set<ReferenceNumber> refNumsCollectionIn = (Set<ReferenceNumber>) asnRefNumMapIn
					.get("asnRefNumCollection");
			if (Validator.isNull(refNumsCollectionIn) || refNumsCollectionIn.size() == 0)
			{
//				ReferenceNumberType rnt = referenceNumberTypeDao.getByCode(ReferenceNumberTypeCustomCONSTANTS.TYPE_NO_REF);
//				ReferenceNumber rn = new ReferenceNumber();
//				rn.setType(rnt);
//				rn.setValue("Dummy");
//				rn = em.merge(rn);
//				em.flush();
				
				refNumsCollectionIn = new HashSet<ReferenceNumber>();
//				refNumsCollectionIn.add(rn);
			}
			Set<ReferenceNumberType> refNumTypesCollectionIn = (Set<ReferenceNumberType>) asnRefNumMapIn
					.get("asnRefNumTypeCollection");

			Map<String, Object> asnASNLineProductMapIn = (Map<String, Object>) workItem
					.getParameter("asnASNLineProductMapIn");
			Set<ASNLine> asnLinesCollectionIn = (Set<ASNLine>) asnASNLineProductMapIn
					.get("asnLinesCollection");
			Set<Product> productsCollection = (Set<Product>) asnASNLineProductMapIn
					.get("productsCollection");

			Map<String, Object> asnLocalTransMapIn = (Map<String, Object>) workItem
					.getParameter("asnLocalTransMapIn");
			ASNPickup asnPickupIn = (ASNPickup) asnLocalTransMapIn
					.get("asnPickup");
			ASNDropOff asnDropoffIn = (ASNDropOff) asnLocalTransMapIn
					.get("asnDropoff");

			/**
			 * 
			 * Save ASN
			 * 
			 */
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_MANDATORY);
			TransactionStatus status = globalJtaTransactionManager
					.getTransaction(def);

			Map<String, Object> varsOut = new HashMap<String, Object>();
			//ASN asn = null;
			try {
				asnIn = em.merge(asnIn);
				em.flush();

				varsOut.put("asn", asnIn);
				Map<String, Object> output = new HashMap<String, Object>();
				output.put("asnOut", asnIn);

				
				Set<ReferenceNumber> attachedRefNums = new HashSet<ReferenceNumber>();
				if (Validator.isNotNull(asnASNLineProductMapIn)
						&& asnASNLineProductMapIn.size() > 0) {
					if (Validator.isNotNull(asnLinesCollectionIn))
					{
						for (ASNLine line_ : asnLinesCollectionIn)
						{
							if (line_.getRefNumber() != null)
							{
								attachedRefNums.add(line_.getRefNumber());
							}
						}
						
						Map<String, Object> asnASNLineProductMapOut = new HashMap<String, Object>();
						asnIn = addLines(asnIn.getId(), asnLinesCollectionIn);
						Set<ASNLine> asnLinesCollectionOut = asnIn.getAsnLines();
						asnASNLineProductMapOut.put("asnLinesCollection",
								asnLinesCollectionOut);
	
						varsOut.put("asnASNLineProductMapOut",
								asnASNLineProductMapOut);
					}
				}
				
				 
				
				if (Validator.isNotNull(asnRefNumMapIn)
						&& asnRefNumMapIn.size() > 0 
						&& refNumsCollectionIn.size() > 0) {
					Map<String, Object> asnRefNumMapOut = new HashMap<String, Object>();
					
					refNumsCollectionIn = removeAll(attachedRefNums,refNumsCollectionIn);
					asnIn = addRefNums(asnIn.getId(), refNumsCollectionIn);
					Set<ReferenceNumber> refNumsCollectionOut = asnIn
							.getRefNumbers();
					asnRefNumMapOut.put("asnRefNumCollection",
							refNumsCollectionOut);
					varsOut.put("refNumsCollectionOut", asnRefNumMapOut);
				}

				if (Validator.isNotNull(asnLocalTransMapIn)
						&& asnLocalTransMapIn.size() > 0) {
					Map<String, Object> asnLocalTransMapOut = new HashMap<String, Object>();
					asnIn = addLocalTrans(asnIn.getId(), asnPickupIn,
							asnDropoffIn);
					ASNPickup asnPickupOut = asnIn.getPickup();
					ASNDropOff asnDropoffOut = asnIn.getDropOff();
					asnLocalTransMapOut.put("asnPickup", asnPickupOut);
					asnLocalTransMapOut.put("asnDropoff", asnDropoffOut);

					varsOut.put("asnLocalTransMapOut", asnLocalTransMapOut);
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error(stacktrace);

				globalJtaTransactionManager.rollback(status);

				throw new IllegalStateException("AcceptASNWIH:Save New ASN\r\n"
						+ stacktrace, e);
			}
			globalJtaTransactionManager.commit(status);

			manager.completeWorkItem(workItem.getId(), varsOut);
			// WIUtils.waitTillCompleted(workItem,1000L);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);

			throw new IllegalStateException("AcceptASNWIH:\r\n" + stacktrace, e);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);

			throw new IllegalStateException("AcceptASNWIH:\r\n" + stacktrace, e);
		}
	}

	private Set<ReferenceNumber> removeAll(Set<ReferenceNumber> attachedRefNums,
			Set<ReferenceNumber> refNumsCollectionIn) {
		Set<ReferenceNumber> res = new HashSet<ReferenceNumber>();
		boolean found = false;
		for (ReferenceNumber rn : refNumsCollectionIn)
		{
			for (ReferenceNumber arn : attachedRefNums)
			{
				if (Validator.equals(rn.getValue(),arn.getValue()) && Validator.equals(rn.getType().getCode(),arn.getType().getCode()))
				{
					found = true;
					break;
				}
			}
			
			if (!found)
				res.add(rn);
		}
		return res;
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

	public ASN addLines(Long asnId, Set<ASNLine> lines) throws Exception {
		ASN asn = null;
		try {
			asn = em.getReference(ASN.class, asnId);
			Product prod = null;
			ReferenceNumber number = null;
			DefaultEntityMetadata emd = provideEMD(ASN.class);
			for (ASNLine line : lines) {
				line.setParentASN(asn);
				
				number = line.getRefNumber();
				if (Validator.isNotNull(number)) {
					number = em.merge(number);
					line.setRefNumber(number);
				}
				
				line = (ASNLine) em.merge(line);
				em.flush();

				if (Validator.isNotNull(number))
				{
					number = em.merge(number);
					if (Validator.isNull(number.getId()))
					{
						number.setEntityMetadata(emd);
						number.setEntityPK(number.getId());
					}
					line.setRefNumber(number);
					
					asn.getRefNumbers().add(number);
				}
				
				prod = em.merge(line.getProduct());
				line.setProduct(prod);
				
				line = (ASNLine) em.merge(line);

				asn.getAsnLines().add(line);
			}

			asn = em.merge(asn);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);

			throw e;
		}

		return asn;
	}

	public ASN addRefNums(Long asnId, Set<ReferenceNumber> numbers)
			throws Exception {
		ASN asn = null;
		try {
			asn = em.getReference(ASN.class, asnId);
			DefaultEntityMetadata emd = provideEMD(ASN.class);
			ReferenceNumberType rnt = null;
			for (ReferenceNumber number : numbers) {
				number.setEntityMetadata(emd);
				number.setEntityPK(asnId);
				
/*				if (Validator.isNull(number.getType().getId()))
				{
					rnt = new ReferenceNumberType();
					rnt.setCode(number.getType().getCode());
					rnt.setName(number.getType().getName());
					rnt.setDateCreated(new Date());
					rnt.setDateLastUpdated(new Date());
					rnt = em.merge(rnt);
					em.flush();
					
					number.setType(null);
					number = em.merge(number);
					
					number.setType(rnt);
					number = em.merge(number);
				}
				else
				{
					number = em.merge(number);
				}*/
				
				number = em.merge(number);
				em.flush();
				
				asn.getRefNumbers().add(number);
			}
			asn = em.merge(asn);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);

			throw e;
		}

		return asn;
	}

	public ASN addLocalTrans(Long asnId, ASNPickup pickUp, ASNDropOff dropOff)
			throws Exception {
		ASN asn = null;

		try {
			asn = em.getReference(ASN.class, asnId);

			if (Validator.isNotNull(pickUp)) {
				DockType dockType = pickUp.getDockType();
				if (dockType != null) {
					dockType = em.getReference(DockType.class, dockType.getId());
					pickUp.setDockType(dockType);
				}
				pickUp = (ASNPickup) em.merge(pickUp);
				asn.setPickup(pickUp);
			}

			if (Validator.isNotNull(dropOff)) {
				DockType dockType = dropOff.getDockType();
				if (dockType != null) {
					dockType = em.getReference(DockType.class, dockType.getId());
					dropOff.setDockType(dockType);
				}
				dropOff = (ASNDropOff) em.merge(dropOff);
				asn.setDropOff(dropOff);
			}

			asn.setPickup(pickUp);
			asn.setDropOff(dropOff);

			asn = em.merge(asn);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);

			throw e;
		}

		return asn;
	}

	public DefaultEntityMetadata getByClass(Class entityClass) {
		DefaultEntityMetadata record = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DefaultEntityMetadata> query = builder
					.createQuery(DefaultEntityMetadata.class);
			Root<DefaultEntityMetadata> rootEntity = query
					.from(DefaultEntityMetadata.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(
					builder.equal(rootEntity.get("entityJavaSimpleType"), p));

			TypedQuery<DefaultEntityMetadata> typedQuery = em
					.createQuery(query);
			typedQuery.setParameter(p, entityClass.getSimpleName());

			return typedQuery.getSingleResult();
			// TypedQuery<DefaultEntityMetadata> q =
			// em.createQuery("select DISTINCT  o from com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata o WHERE o.entityJavaSimpleType = :entityJavaSimpleType",DefaultEntityMetadata.class);
			// q.setParameter("entityJavaSimpleType",
			// entityClass.getSimpleName());
			// record = q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return record;
	}

	public DefaultEntityMetadata provideEMD(Class entityClass) {
		DefaultEntityMetadata existingRecord = getByClass(entityClass);
		if (Validator.isNull(existingRecord)) {
			existingRecord = new DefaultEntityMetadata();
			existingRecord.setDateCreated(new Date());
			existingRecord.setDateLastUpdated(new Date());
			existingRecord.setEntityJavaSimpleType(entityClass.getSimpleName());
			existingRecord.setEntityJavaType(entityClass.getName());
			existingRecord = em.merge(existingRecord);
		}
		return existingRecord;
	}

}
