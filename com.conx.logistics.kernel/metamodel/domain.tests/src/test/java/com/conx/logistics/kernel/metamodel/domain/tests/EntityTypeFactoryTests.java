package com.conx.logistics.kernel.metamodel.domain.tests;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.conx.logistics.kernel.metamodel.dao.services.impl.EntityTypeFactory;
import com.conx.logistics.kernel.metamodel.dao.services.IEntityTypeDAOService;
import com.conx.logistics.kernel.metamodel.dao.services.impl.EntityTypeDAOImpl;
import com.conx.logistics.kernel.metamodel.domain.AbstractAttribute;
import com.conx.logistics.kernel.metamodel.domain.BasicAttribute;
import com.conx.logistics.mdm.domain.product.Product;

@ContextConfiguration(locations = { "/META-INF/tm.jta-module-context.xml",
        "/META-INF/persistence.datasource-module-context.xml",
        "/META-INF/jbpm.persistence.datasource-module-context.xml",
        "/META-INF/persistence.dynaconfiguration-module-context.xml",
        "/META-INF/metamodel.dao.jpa.persistence-module-context.xml"
        })
public class EntityTypeFactoryTests extends AbstractTestNGSpringContextTests {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory  conxLogisticsManagerFactory;
	
	private UserTransaction userTransactionManager;

	private EntityManager em;
	
	
	@Autowired
	private EntityTypeDAOImpl entityTypeDAOService; 
	
	@BeforeClass
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(conxLogisticsManagerFactory);
        
    	em = conxLogisticsManagerFactory.createEntityManager();
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
        
        Assert.assertNotNull(entityTypeDAOService);
    }	
	
	@AfterClass
	public void tearDown() throws Exception {
		em.close();
	}
	
    @Test
    public void testEntitTypeCreation() throws Exception {
    	Metamodel mm = em.getMetamodel();
    	Assert.assertNotNull(mm);
    	
    	EntityType<Product> me = mm.entity(Product.class);
    	
    	com.conx.logistics.kernel.metamodel.domain.EntityType et = entityTypeDAOService.provide(me);
    	Assert.assertNotNull(et);
    	
        List<BasicAttribute> attrs = entityTypeDAOService.getAllBasicAttributesByEntityType(et);
        Assert.assertTrue(attrs.size() > 0);
    }
}
