package com.conx.logistics.kernel.system.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.conx.logistics.kernel.system.dao.services.application.IFeatureDAOService;
import com.conx.logistics.mdm.domain.application.Feature;


/**
 * Implementation of {@link Feature} that uses JPA for persistence.<p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for this module, enables AspectJ weaving for
 * adding transaction demarcation to classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class FeatureDAOImpl implements IFeatureDAOService {	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	

	@Override
	public List<Feature> getFeatures() {
		return em.createQuery("select o from Feature o order by o.id",Feature.class).getResultList();
	}
	
	public Feature findFeatureByCode(String code) {
		TypedQuery<Feature> q = em.createQuery("select o from Feature o WHERE o.code = :code",Feature.class);
		q.setParameter("code", code);
		
		Feature org = null;
		
		try
		{
			org = q.getSingleResult();
		}
		catch(NoResultException e){}
		
		return org;
	}
	

	@Override
	public Feature addFeature(Feature fs) {

		fs = em.merge(fs);
		
		return fs;
	}

	@Override
	public void deleteFeature(Feature fs) {
		em.remove(fs);
	}

	@Override
	public Feature updateFeature(Feature fs) {
		return em.merge(fs);
	}

	@Override
	public Feature getFeature(long id) {
		return em.getReference(Feature.class, id);
	}
	
	/**
	 * BundleContext
	 */
}
