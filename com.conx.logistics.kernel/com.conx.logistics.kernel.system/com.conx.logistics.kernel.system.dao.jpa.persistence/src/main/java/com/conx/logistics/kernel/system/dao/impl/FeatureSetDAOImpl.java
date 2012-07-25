package com.conx.logistics.kernel.system.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.system.dao.services.application.IFeatureSetDAOService;
import com.conx.logistics.mdm.domain.application.FeatureSet;


/**
 * Implementation of {@link FeatureSet} that uses JPA for persistence.<p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for this module, enables AspectJ weaving for
 * adding transaction demarcation to classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class FeatureSetDAOImpl implements IFeatureSetDAOService {	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	

	public List<FeatureSet> getFeatureSets() {
		return em.createQuery("select o from FeatureSet o order by o.id",FeatureSet.class).getResultList();
	}
	
	public FeatureSet findFeatureSetByCode(String code) {
		TypedQuery<FeatureSet> q = em.createQuery("select o from FeatureSet o WHERE o.code = :code",FeatureSet.class);
		q.setParameter("code", code);
		
		FeatureSet org = null;
		
		try
		{
			org = q.getSingleResult();
		}
		catch(NoResultException e){}
		
		return org;
	}
	

	@Override
	public FeatureSet addFeatureSet(FeatureSet fs) {

		fs = em.merge(fs);
		
		return fs;
	}

	@Override
	public void deleteFeatureSet(FeatureSet fs) {
		em.remove(fs);
	}

	@Override
	public FeatureSet updateFeatureSet(FeatureSet fs) {
		return em.merge(fs);
	}

	@Override
	public FeatureSet getFeatureSet(long id) {
		return em.getReference(FeatureSet.class, id);
	}
	
	/**
	 * BundleContext
	 */
}
