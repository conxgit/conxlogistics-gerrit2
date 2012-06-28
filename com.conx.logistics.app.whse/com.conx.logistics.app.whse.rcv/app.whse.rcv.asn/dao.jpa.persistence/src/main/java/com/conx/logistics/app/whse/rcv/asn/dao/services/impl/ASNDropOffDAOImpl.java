package com.conx.logistics.app.whse.rcv.asn.dao.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDropOffDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;


/**
 * Implementation of {@link ASN} that uses JPA for persistence.<p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for this module, enables AspectJ weaving for
 * adding transaction demarcation to classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class ASNDropOffDAOImpl implements IASNDropOffDAOService {
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
	public ASNDropOff get(long id) {
		return em.getReference(ASNDropOff.class, id);
	}    

	@Override
	public List<ASNDropOff> getAll() {
		return em.createQuery("select o from com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff o record by o.id",ASNDropOff.class).getResultList();
	}	

	@Override
	public ASNDropOff add(ASNDropOff record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ASNDropOff record) {
		em.remove(record);
	}

	@Override
	public ASNDropOff update(ASNDropOff record) {
		return em.merge(record);
	}
}
