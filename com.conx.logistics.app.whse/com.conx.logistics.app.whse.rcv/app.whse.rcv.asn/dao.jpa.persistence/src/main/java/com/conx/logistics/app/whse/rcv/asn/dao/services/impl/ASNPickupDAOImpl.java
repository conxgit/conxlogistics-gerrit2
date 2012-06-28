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
import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNPickupDAOService;
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
public class ASNPickupDAOImpl implements IASNPickupDAOService {
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
	public ASNPickup get(long id) {
		return em.getReference(ASNPickup.class, id);
	}    

	@Override
	public List<ASNPickup> getAll() {
		return em.createQuery("select o from com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup o record by o.id",ASNPickup.class).getResultList();
	}	

	@Override
	public ASNPickup add(ASNPickup record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ASNPickup record) {
		em.remove(record);
	}

	@Override
	public ASNPickup update(ASNPickup record) {
		return em.merge(record);
	}
}
