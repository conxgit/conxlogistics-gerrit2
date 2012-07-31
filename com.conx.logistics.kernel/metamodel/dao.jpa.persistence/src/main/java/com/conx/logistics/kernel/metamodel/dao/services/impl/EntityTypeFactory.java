package com.conx.logistics.kernel.metamodel.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.MappedByteBuffer;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conx.logistics.kernel.metamodel.dao.services.IEntityTypeDAOService;
import com.conx.logistics.kernel.metamodel.domain.AbstractManagedType;
import com.conx.logistics.kernel.metamodel.domain.EntityType;
import com.conx.logistics.mdm.domain.product.Product;


public class EntityTypeFactory {
	protected static Logger logger = LoggerFactory.getLogger(EntityTypeFactory.class);	
	
	public static EntityType create(EntityManager em, IEntityTypeDAOService entityTypeDao, Class entityClass) throws ClassNotFoundException
	{
		Metamodel mm = em.getMetamodel();
    	javax.persistence.metamodel.ManagedType mt = null;
    	try {
			mt = mm.managedType(entityClass);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
    	
    	//SuperClass

    	
    	//Id and Version attr
    	
    	
    	//Create entity
    	EntityType res = null;
    	EntityType st = null;
    	if (mt instanceof javax.persistence.metamodel.EntityType)
    	{
    		if (((javax.persistence.metamodel.EntityType)mt).getSupertype() != null)
    			st = createMappedSupperClass(em,entityTypeDao,((javax.persistence.metamodel.EntityType)mt).getSupertype().getJavaType());    		
	    	res = new EntityType(((EntityType)mt).getName(),
	    			       mt.getJavaType(),
	    			       st,
	    			       null,//SingularAttribute id, 
	    			       null,//SingularAttribute version,
	    			       mt.getJavaType().getName());
    	}
    	else if (mt instanceof MappedSuperclassType)
    	{
    		if (((MappedSuperclassType) mt).getSupertype() != null)
    			st = createMappedSupperClass(em,entityTypeDao,((MappedSuperclassType) mt).getSupertype().getJavaType());      		
	    	res = new EntityType(((EntityType)mt).getName(),
 			       mt.getJavaType(),
 			       st,
 			       null,//SingularAttribute id, 
 			       null,//SingularAttribute version,
 			       mt.getJavaType().getName());
    	}
    	
    	//Save 
    	res = entityTypeDao.provide(res);
    	
    	//Process attributes
    	Set<SingularAttribute> sattrs = mt.getDeclaredSingularAttributes();
    	
    	Set<PluralAttribute> pattrs = mt.getDeclaredPluralAttributes();
    	
    	PersistentAttributeType at;
    	for (SingularAttribute sattr : sattrs)
    	{
    		at = sattr.getPersistentAttributeType();
    	}
    	
    	for (PluralAttribute pattr : pattrs)
    	{
    		
    	}    	
    	
		return res;
	}

	private static EntityType createMappedSupperClass(EntityManager em,
			IEntityTypeDAOService entityTypeDao,
			Class<? extends IdentifiableType> msc) {
		
		Class<?> sc = msc.getSuperclass();

		return null;
	}

	private static EntityType provideSuperEntityType(EntityManager em, IEntityTypeDAOService entityTypeDao, Type msType) throws ClassNotFoundException {
		EntityType et = null;
		if (msType != null)
		{
			et = entityTypeDao.getByClass(msType.getJavaType());
			
			if (et == null)	
			{
				if (msType instanceof ManagedType)
				{			
					et = create(em, entityTypeDao, msType.getJavaType());
				}
				else if (msType instanceof MappedSuperclassType)
				{
					EntityType st = provideSuperEntityType(em,entityTypeDao,((MappedSuperclassType) msType).getSupertype());
					et = new EntityType(((MappedSuperclassType)msType).getJavaType().getName(),
			    			   ((MappedSuperclassType)msType).getJavaType(),
			 			       st,
			 			       null,//SingularAttribute id, 
			 			       null,//SingularAttribute version,
			 			      ((MappedSuperclassType)msType).getJavaType().getName());
				}
			}
		}
		return et;
	}
}
