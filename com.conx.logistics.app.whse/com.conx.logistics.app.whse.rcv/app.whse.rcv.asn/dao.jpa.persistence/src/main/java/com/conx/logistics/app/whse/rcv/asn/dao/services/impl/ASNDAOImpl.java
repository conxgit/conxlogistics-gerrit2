package com.conx.logistics.app.whse.rcv.asn.dao.services.impl;

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


import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;


/**
 * Implementation of {@link ASN} that uses JPA for persistence.<p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for this module, enables AspectJ weaving for
 * adding transaction demarcation to classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class ASNDAOImpl implements IASNDAOService {
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
	public ASN get(long id) {
		return em.getReference(ASN.class, id);
	}    

	@Override
	public List<ASN> getAll() {
		return em.createQuery("select o from com.conx.logistics.app.whse.rcv.asn.domain.ASN o record by o.id",ASN.class).getResultList();
	}	

	@Override
	public ASN add(ASN record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ASN record) {
		em.remove(record);
	}

	@Override
	public ASN update(ASN record) {
		return em.merge(record);
	}

	@Override
	public ASN addLines(Long asnId, List<ASNLine> lines) {
		ASN asn = em.getReference(ASN.class, asnId);
		asn.getAsnLines().addAll(lines);
		return update(asn);
	}

	@Override
	public ASN addRefNums(Long asnId, List<ReferenceNumber> numbers) {
		ASN asn = em.getReference(ASN.class, asnId);
		asn.getRefNumbers().addAll(numbers);
		return update(asn);
	}

	@Override
	public ASN addLocalTrans(Long asnId, ASNPickup pickUp, ASNDropOff dropOff) {
		ASN asn = em.getReference(ASN.class, asnId);
		asn.setPickup(pickUp);
		asn.setDropOff(dropOff);
		return update(asn);
	}	
}
