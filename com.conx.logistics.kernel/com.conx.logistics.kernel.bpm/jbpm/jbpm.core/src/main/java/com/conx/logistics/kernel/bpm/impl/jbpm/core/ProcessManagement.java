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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.NodeInstance;
import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.RESULT;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.STATE;
import org.jbpm.process.audit.JPAProcessInstanceDbLog;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.process.audit.VariableInstanceLog;

import com.conx.logistics.kernel.bpm.impl.jbpm.BPMServerImpl;

public class ProcessManagement implements org.jboss.bpm.console.server.integration.ProcessManagement {
    private BPMServerImpl bpmService;
	private CommandDelegate commandDelegate;

	public ProcessManagement(BPMServerImpl bpmService) {
        super();
        this.bpmService = bpmService;
        this.commandDelegate = new CommandDelegate(bpmService);
    }
    
    public List<ProcessDefinitionRef> getProcessDefinitions() {
        List<Process> processes = commandDelegate.getProcesses();
        List<ProcessDefinitionRef> result = new ArrayList<ProcessDefinitionRef>();
        for (Process process: processes) {
            result.add(Transform.processDefinition(process));
        }
        return result;
    }

    public ProcessDefinitionRef getProcessDefinition(String definitionId) {
        Process process = commandDelegate.getProcess(definitionId);
        return Transform.processDefinition(process);
    }

    /**
     * method unsupported
     */
    public List<ProcessDefinitionRef> removeProcessDefinition(String definitionId) {
        commandDelegate.removeProcess(definitionId); 
        return getProcessDefinitions();
    }
    
    
    public ProcessInstanceRef getProcessInstance(String instanceId) {
        ProcessInstanceLog processInstance = commandDelegate.getProcessInstanceLog(instanceId);
        Collection<NodeInstance> activeNodes = commandDelegate.getActiveNodeInstances(processInstance.getId());
        return Transform.processInstance(processInstance, activeNodes);
    }

    public List<ProcessInstanceRef> getProcessInstances(String definitionId) {
        List<ProcessInstanceLog> processInstances = commandDelegate.getActiveProcessInstanceLogsByProcessId(definitionId);
        List<ProcessInstanceRef> result = new ArrayList<ProcessInstanceRef>();
        for (ProcessInstanceLog processInstance: processInstances) {
            
            Collection<NodeInstance> activeNodes = commandDelegate.getActiveNodeInstances(processInstance.getId());
            result.add(Transform.processInstance(processInstance, activeNodes));
        }
        return result;
    }

    public ProcessInstanceRef newInstance(String definitionId) {
        ProcessInstanceLog processInstance = commandDelegate.startProcess(definitionId, null);
        Collection<NodeInstance> activeNodes = commandDelegate.getActiveNodeInstances(processInstance.getId());
        return Transform.processInstance(processInstance, activeNodes);
    }
    
    public ProcessInstanceRef newInstance(String definitionId, Map<String, Object> processVars) {
        ProcessInstanceLog processInstance = commandDelegate.startProcess(definitionId, processVars);
        Collection<NodeInstance> activeNodes = commandDelegate.getActiveNodeInstances(processInstance.getId());
        return Transform.processInstance(processInstance, activeNodes);
    }

    public void setProcessState(String instanceId, STATE nextState) {
        if (nextState == STATE.ENDED) {
            commandDelegate.abortProcessInstance(instanceId);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public Map<String, Object> getInstanceData(String instanceId) {
        return commandDelegate.getProcessInstanceVariables(instanceId);
    }

    public void setInstanceData(String instanceId, Map<String, Object> data) {
        commandDelegate.setProcessInstanceVariables(instanceId, data);
    }

    
    public void signalExecution(String executionId, String signal) {
        if (signal.indexOf("^") != -1) {
            String[] signalData = signal.split("\\^");
            commandDelegate.signalExecution(executionId, signalData[0], signalData[1]);
        } else {
            commandDelegate.signalExecution(executionId, signal, null);
        }
        
    }

    public void deleteInstance(String instanceId) {
        commandDelegate.abortProcessInstance(instanceId);
    }

    //result means nothing
    public void endInstance(String instanceId, RESULT result) {
        commandDelegate.abortProcessInstance(instanceId);
    }
    
    public Map<String, Object> getProcessInstanceVariables(String processInstanceId)
    {
    	return commandDelegate.getProcessInstanceVariables(processInstanceId);
    }
    
    public Map<String, String> findVariableInstances(Long processInstanceId)
    {
    	Map<String,String> varMap = new HashMap<String, String>();
    	List<VariableInstanceLog> vars = commandDelegate.findVariableInstances(processInstanceId);
    	for (VariableInstanceLog var : vars)
    	{
    		varMap.put(var.getVariableId(), var.getValue());
    	}
    	
    	return varMap;
    }    
    
    public void setProcessInstanceVariables(final String processInstanceId, final Map<String, Object> variables)
    {
    	commandDelegate.setProcessInstanceVariables(processInstanceId, variables);
    }
}