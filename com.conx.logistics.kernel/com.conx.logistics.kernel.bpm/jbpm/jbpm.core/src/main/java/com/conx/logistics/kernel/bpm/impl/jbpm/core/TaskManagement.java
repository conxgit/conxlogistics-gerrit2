/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.conx.logistics.kernel.bpm.impl.jbpm.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;

import org.jboss.bpm.console.client.model.TaskRef;
import org.jbpm.task.AccessType;
import org.jbpm.task.Content;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.TaskService;
import org.jbpm.task.User;
import org.jbpm.task.event.TaskFailedEvent;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.Command;
import org.jbpm.task.service.CommandName;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.SyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskClientHandler.QueryGenericResponseHandler;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.responsehandlers.BlockingQueryGenericResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;

import com.conx.logistics.kernel.bpm.impl.jbpm.BPMServerImpl;

public class TaskManagement implements org.jboss.bpm.console.server.integration.TaskManagement {
	
	private static int clientCounter = 0;
    
	private TaskService service;
	private BPMServerImpl bpmService;
	
	public TaskManagement (BPMServerImpl bpmService) {
	    super();
	    this.bpmService = bpmService;
	}
	
	public void connect() {
		if (service == null)
		{
			service = bpmService.getLocalHumanTaskServer().getLocalTaskService();
			//service = bpmService.getHumanTaskManager().getService();
		}
		
	    //Properties jbpmConsoleProperties = bpmService.getJbpmProperties();
        //service = TaskClientFactory.newInstance(jbpmConsoleProperties, "org.jbpm.integration.console.TaskManagement"+clientCounter);
        clientCounter++;
	}
	
	public Content getTaskContent(long taskId) {      
		connect();
		Content content = service.getContent(taskId);
		
        return content;
	}	
	
	public TaskRef getTaskById(long taskId) {
		connect();
		Task task = service.getTask(taskId);

        return Transform.task(task);
	}
	
	public Task getTaskObjectById(long taskId) {
		connect();
		Task task = service.getTask(taskId);

        return task;
	}	

	public void assignTask(long taskId, String idRef, String userId) {
		connect(); 
		
		if (idRef == null) {
			service.release(taskId, userId);
		} else if (idRef.equals(userId)) {
			List<String> roles = getCallerRoles();
			if (roles == null) {
				service.claim(taskId, idRef);
			} else {
				service.claim(taskId, idRef, roles);
			}
		} else {

			service.delegate(taskId, userId, idRef);
		}
		
	}
	
	public void assignAllTasks(String userId) {
		connect();
		service.claimNextAvailable(userId, "");
	}

	
    public List<Task> getCreatedTasksByProcessId(Long processInstanceId) {
    	String query = "select t from org.jbpm.task.Task t  where t.archived = 0 and t.taskData.status = '"+org.jbpm.task.Status.Ready+"' and t.taskData.processInstanceId = "+processInstanceId+" and t.taskData.expirationTime is null";
    	List<Object> args = new ArrayList<Object>( 3 );

    	Properties jbpmConsoleProperties = bpmService.getJbpmProperties();
    	TaskClient tc = TaskClientFactory.newAsyncInstance(jbpmConsoleProperties, "Mina Client");
    	BlockingQueryGenericResponseHandler qh = new BlockingQueryGenericResponseHandler();
    	tc.query(query, 1, 0, qh);
    	List<Task> res = (List<Task>)qh.getResults();
    	return res;
	}	
	
	public void nominateTask(long taskId, User nominnee) {
		List<OrganizationalEntity> potentialOwners = new ArrayList<OrganizationalEntity>();
		potentialOwners.add(nominnee);
		connect();
		Task task = service.getTask(taskId);
		service.nominate(task.getId(),"Administrator",potentialOwners);
		/*
    	Properties jbpmConsoleProperties = bpmService.getJbpmProperties();
    	TaskClient tc = TaskClientFactory.newAsyncInstance(jbpmConsoleProperties, "Mina Client");
        BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
        tc.nominate(taskId,"Administrator",potentialOwners,operationResponseHandler);
        operationResponseHandler.waitTillDone(15000);		
        */	
	}	
	
	public void startTask(long taskId, String userId) {
		/*
    	Properties jbpmConsoleProperties = bpmService.getJbpmProperties();
    	TaskClient tc = TaskClientFactory.newAsyncInstance(jbpmConsoleProperties, "Mina Client");
        BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
        tc.start(taskId,userId,operationResponseHandler);
        operationResponseHandler.waitTillDone(15000);		
		*/
		connect();
		Task task = service.getTask(taskId);
		service.start(task.getId(), userId);
	}		
	
	
	
	public void completeTask(long taskId, Map data, String userId) {
		connect();
		
		ContentData contentData = null;
		if (data != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(data);
				out.close();
				contentData = new ContentData();
				contentData.setContent(bos.toByteArray());
				contentData.setAccessType(AccessType.Inline);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
  
		service.complete(taskId, userId, contentData);
	}
	
	public void completeTask(long taskId, ContentData contentData, String userId) {
		/*
		TaskClient tc = TaskClientFactory.newAsyncInstance(null, "Mina Client");
        BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
        tc.complete(taskId,userId,contentData, operationResponseHandler);
        operationResponseHandler.waitTillDone(15000);
		*/
		connect();
		service.complete(taskId, userId, contentData);
	}	

	@SuppressWarnings("unchecked")
	public void completeTask(long taskId, String outcome, Map data, String userId) {
	    if ("jbpm_skip_task".equalsIgnoreCase(outcome)) {
	        skipTask(taskId, userId);
	    } else {
    		data.put("outcome", outcome);
    		completeTask(taskId, data, userId);
	    }
	}

	public void releaseTask(long taskId, String userId) {
		// TODO: this method is not being invoked, it's using
		// assignTask with null parameter instead
		connect(); 
		service.release(taskId, userId);
	}

	public List<TaskRef> getAssignedTasks(String idRef) {
		connect();
        List<TaskRef> result = new ArrayList<TaskRef>();
		try {
		    List<Status> onlyReserved = Collections.singletonList(Status.Reserved);
			List<TaskSummary> tasks = service.getTasksOwned(idRef, onlyReserved, "en-UK");
			
	        for (TaskSummary task: tasks) {
	        	result.add(Transform.task(task));
	        }
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}

	public List<TaskRef> getUnassignedTasks(String idRef, String participationType) {
		// TODO participationType ?
		connect();
        List<TaskRef> result = new ArrayList<TaskRef>();
		try {
            
			List<String> roles = getCallerRoles();
			List<TaskSummary> tasks = null;
			List<Status> onlyReady = Collections.singletonList(Status.Ready);
			if (roles == null) {
				tasks = service.getTasksAssignedAsPotentialOwnerByStatus(idRef, onlyReady, "en-UK");
			} else {
				tasks = service.getTasksAssignedAsPotentialOwnerByStatusByGroup(idRef, roles, onlyReady, "en-UK");
			}
			
			
	        for (TaskSummary task: tasks) {
	        	result.add(Transform.task(task));
	        }
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}
	
	public void skipTask(long taskId, String userId) {
	    connect();
        service.skip(taskId, userId);
	}

    private List<String> getCallerRoles() {
        List<String> roles = null;
        try {
            Subject subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
    
            if (subject != null) {
                Set<Principal> principals = subject.getPrincipals();
    
                if (principals != null) {
                    roles = new ArrayList<String>();
                    for (Principal principal : principals) {
                        if (principal instanceof Group  && "Roles".equalsIgnoreCase(principal.getName())) {
                            Enumeration<? extends Principal> groups = ((Group) principal).members();
                            
                            while (groups.hasMoreElements()) {
                                Principal groupPrincipal = (Principal) groups.nextElement();
                                roles.add(groupPrincipal.getName());
                               
                            }
                            break;
    
                        }
    
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

}
