package com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

public class EntityManagerFactoryAndTracker implements EntityManagerFactory {

    private EntityManagerFactory emf;
    private HashSet<EntityManager> entityManagerMap = new HashSet<EntityManager>();

    public EntityManagerFactoryAndTracker(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager createEntityManager() {
        EntityManager em = emf.createEntityManager();
        entityManagerMap.add(em);
        return em;
    }

    public EntityManager createEntityManager(Map map) {
        EntityManager em = emf.createEntityManager(map);
        entityManagerMap.add(em);
        return em;
    }

    public void close() {
        // clean up all entity managers found.
        Iterator<EntityManager> iter = entityManagerMap.iterator();
        while (iter.hasNext()) {
            EntityManager em = iter.next();
            try {
                EntityTransaction etx = em.getTransaction();
                if (etx.isActive()) {
                    etx.rollback();
                }
            } catch (Throwable t) {
                // don't worry, it doesn't matter.
            } finally {
                try {
                    if (em.isOpen()) {
                        em.clear();
                        em.close();
                    }
                } catch (Throwable t) {
                    // do nothing..
                }
            }
        }
        emf.close();
    }

    public boolean isOpen() {
        return emf.isOpen();
    }

    /**
     * JPA 2 method.
     * {@inheritDoc}
     */
    public CriteriaBuilder getCriteriaBuilder() {
        return this.emf.getCriteriaBuilder();
    }

    /**
     * JPA 2 method.
     * {@inheritDoc}
     */
    public Metamodel getMetamodel() {
        return this.emf.getMetamodel();
    }

    /**
     * JPA 2 method.
     * {@inheritDoc}
     */
    public Map<String, Object> getProperties() {
        return this.emf.getProperties();
    }

    /**
     * JPA 2 method.
     * {@inheritDoc}
     */
    public Cache getCache() {
        return this.emf.getCache();
    }

    /**
     * JPA 2 method.
     * {@inheritDoc}
     */
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return this.emf.getPersistenceUnitUtil();
    }

}
