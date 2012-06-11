package com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service;

import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.junit.Test;


public class UserGroupTest extends BaseTestNoUserGroupSetup {
	
	public UserGroupTest()
	{
		setPluginsDirPath("../../../../com.conx.logistics.testingframework/osgi-bpm-bundles");
	}

    @Test
    public void testAddUser() {
        User user = new User("mike");
        assertFalse(taskSession.getTaskPersistenceManager().userExists(user.getId()));
        taskSession.addUser(user);
        
        assertTrue(taskSession.getTaskPersistenceManager().userExists(user.getId()));
    }
    
    @Test
    public void testAddDuplicatedUser() {
        User user = new User("mike");
        assertFalse(taskSession.getTaskPersistenceManager().userExists(user.getId()));
        taskSession.addUser(user);
        
        assertTrue(taskSession.getTaskPersistenceManager().userExists(user.getId()));
        User user2 = new User("mike");
        taskSession.addUser(user2);
        
    }
    
    @Test
    public void testAddGroup() {
        Group group = new Group("mike");
        assertFalse(taskSession.getTaskPersistenceManager().groupExists(group.getId()));
        taskSession.addGroup(group);
        
        assertTrue(taskSession.getTaskPersistenceManager().groupExists(group.getId()));
    }
    
    @Test
    public void testAddDuplicatedGroup() {
        Group group = new Group("mike");
        assertFalse(taskSession.getTaskPersistenceManager().groupExists(group.getId()));
        taskSession.addGroup(group);
        
        assertTrue(taskSession.getTaskPersistenceManager().groupExists(group.getId()));
        
        Group group2 = new Group("mike");
        
        taskSession.addGroup(group2);
    }
}
