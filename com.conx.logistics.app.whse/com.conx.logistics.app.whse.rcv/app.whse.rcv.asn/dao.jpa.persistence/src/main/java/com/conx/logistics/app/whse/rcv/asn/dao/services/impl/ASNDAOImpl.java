package com.conx.logistics.app.whse.rcv.asn.dao.services.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
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
    
    @Autowired
    private IEntityMetadataDAOService entityMetadataDAOService;
    
    @Autowired
    private IReferenceNumberDAOService referenceNumberDAOService;
    
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
	public ASN addLines(Long asnId, Set<ASNLine> lines) {
		ASN asn = em.getReference(ASN.class, asnId);
		for (ASNLine line : lines)
		{
			line.setParentASN(asn);
			line = (ASNLine)em.merge(line);
			asn.getAsnLines().add(line);
		}

		return update(asn);
	}

	@Override
	public ASN addRefNums(Long asnId, Set<ReferenceNumber> numbers) {
		ASN asn = em.getReference(ASN.class, asnId);
		DefaultEntityMetadata emd = entityMetadataDAOService.provide(ASN.class);
		for (ReferenceNumber number : numbers)
		{
			number.setEntityMetadata(emd);
			number.setEntityPK(asnId);
			number = referenceNumberDAOService.add(number);
			asn.getRefNumbers().add(number);
		}
		return update(asn);
	}

	@Override
	public ASN addLocalTrans(Long asnId, ASNPickup pickUp, ASNDropOff dropOff) {
		ASN asn = em.getReference(ASN.class, asnId);
		
		pickUp = (ASNPickup)em.merge(pickUp);
		asn.setPickup(pickUp);
		
		dropOff = (ASNDropOff)em.merge(dropOff);
		asn.setDropOff(dropOff);	
		
		asn.setPickup(pickUp);
		asn.setDropOff(dropOff);
		
		return update(asn);
	}	
}
