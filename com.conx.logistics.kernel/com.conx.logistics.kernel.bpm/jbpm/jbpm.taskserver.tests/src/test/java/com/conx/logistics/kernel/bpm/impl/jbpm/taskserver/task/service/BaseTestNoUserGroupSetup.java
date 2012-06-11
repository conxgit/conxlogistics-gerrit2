package com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.jbpm.task.User;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;

import com.conx.logistics.kernel.bpm.impl.jbpm.BaseTest;
import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.EntityManagerFactoryAndTracker;

public abstract class BaseTestNoUserGroupSetup extends BaseTest {

	protected void onSetUp() throws Exception {
        super.onSetUp();         
        if(!UserGroupCallbackManager.getInstance().existsCallback()) {
        	UserGroupCallbackManager.getInstance().setCallback(new UserGroupCallbackOneImpl());
        }
        taskSession.addUser(new User("Administrator"));		
	}
	
	protected void onTearDown() throws Exception {
        super.onTearDown();
	}

    
    @Override
    protected EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory realEmf = super.createEntityManagerFactory();
        return new EntityManagerFactoryAndTracker(realEmf);
    }
    
    @Override
    public void disableUserGroupCallback() {
        // do not disable
    }
    
}
