package com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.task.service;

import static org.jbpm.task.service.TaskService.eval;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.UserGroupCallback;

public class UserGroupCallbackOneImpl implements UserGroupCallback {
    
    private Map<User, List<Group>> userGroupMapping;
    
    public UserGroupCallbackOneImpl() {
        Reader reader = null;
        Map vars = new HashMap();
        try {
            reader = new InputStreamReader(UserGroupCallbackOneImpl.class.getResourceAsStream("UserGroupsAssignmentsOne.mvel"));
            userGroupMapping = (Map<User, List<Group>>) eval(reader, vars);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader = null;
        }
    }
    
    public boolean existsUser(String userId) {
        Iterator<User> iter = userGroupMapping.keySet().iterator();
        while(iter.hasNext()) {
            User u = iter.next();
            if(u.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsGroup(String groupId) {
        Iterator<User> iter = userGroupMapping.keySet().iterator();
        while(iter.hasNext()) {
            User u = iter.next();
            List<Group> groups = userGroupMapping.get(u);
            for(Group g : groups) {
                if(g.getId().equals(groupId)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<String> getGroupsForUser(String userId, List<String> groupIds) {
		return getGroupsForUser(userId);
	}
    
    

	public List<String> getGroupsForUser(String userId, List<String> groupIds,
			List<String> allExistingGroupIds) {
		return getGroupsForUser(userId);
	}

	public List<String> getGroupsForUser(String userId) {
        Iterator<User> iter = userGroupMapping.keySet().iterator();
        while(iter.hasNext()) {
            User u = iter.next();
            if(u.getId().equals(userId)) {
                List<String> groupList = new ArrayList<String>();
                List<Group> userGroupList = userGroupMapping.get(u);
                for(Group g : userGroupList) {
                    groupList.add(g.getId());
                }
                return groupList;
            }
        }
        return null;
    }
    
}
