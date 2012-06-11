package com.conx.logistics.kernel.system.dao.jpa.eclipselink.application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.conx.logistics.mdm.domain.task.TaskDefinition;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/module-context.xml","classpath:/META-INF/spring/test-context.xml" })
@TestExecutionListeners(value = DependencyInjectionTestExecutionListener.class)
public class TaskDefTests {
    @PersistenceContext
    private EntityManager em;	
    
    @Test
    public void testTaskDefitionBasics() {  
    	TaskDefinition td = new TaskDefinition();
    	Assert.assertNotNull(em);
    	
    	td = em.merge(td);
    	Assert.assertNotNull(td);
    }
     
}

