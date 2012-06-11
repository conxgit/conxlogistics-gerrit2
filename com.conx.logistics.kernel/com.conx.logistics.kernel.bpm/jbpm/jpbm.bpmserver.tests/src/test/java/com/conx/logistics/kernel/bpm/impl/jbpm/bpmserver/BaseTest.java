package com.conx.logistics.kernel.bpm.impl.jbpm.bpmserver;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;

import org.osgi.framework.ServiceReference;

import com.conx.logistics.testingframework.tools.AbstractSpringDMTest;

public abstract class BaseTest extends AbstractSpringDMTest {

	protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    protected EntityManagerFactory conxlEMF;
    protected EntityManagerFactory bpmServiceEMF;
    protected EntityManagerFactory taskServiceEMF;
    protected PlatformTransactionManager globalTransManJTA;
    protected TransactionManager globalTransMan;

    protected Map<String, User> users;
    protected Map<String, Group> groups;

    protected TaskService taskService;
    protected TaskServiceSession taskSession;
    
    public BaseTest() {
    	setPluginsDirPath("../jpbm-osgi-bundles");    	
    }

    
    @Override
	protected void onSetUp() throws Exception {
    	ServiceReference[] srs = bundleContext.getAllServiceReferences(EntityManagerFactory.class.getName(),"(module=KERNEL.SYSTEM.BPM)");
    	if (srs.length > 0) //bpmServiceEMF service exists
    	{
    		bpmServiceEMF = (EntityManagerFactory)bundleContext.getService(srs[0]);
    	}
    }

    @Override
	protected void onTearDown() throws Exception {      
    }    
}
