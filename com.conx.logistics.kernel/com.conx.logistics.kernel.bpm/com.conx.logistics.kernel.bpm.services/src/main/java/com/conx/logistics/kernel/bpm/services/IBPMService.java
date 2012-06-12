package com.conx.logistics.kernel.bpm.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.process.NodeInstance;
import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.RESULT;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.STATE;
import org.jbpm.process.audit.ProcessInstanceLog;

import com.conx.logistics.kernel.bpm.impl.jbpm.core.Transform;

public interface IBPMService {
	public IBPMProcessInstance startNewProcess(String userId, String processId);

	public IBPMProcessInstance getProcessInstance(String processId);

	public Map<String, IBPMTask> getProcessTasks(String processId);

	/**
	 * process Management methods
	 */
	public List<ProcessDefinitionRef> getProcessDefinitions();

	public ProcessDefinitionRef getProcessDefinition(String definitionId);

	public List<ProcessDefinitionRef> removeProcessDefinition(
			String definitionId);

	public ProcessInstanceRef getProcessInstance(String instanceId);

	public List<ProcessInstanceRef> getProcessInstances(String definitionId);

	public ProcessInstanceRef newInstance(String definitionId);

	public ProcessInstanceRef newInstance(String definitionId,
			Map<String, Object> processVars);

	public void setProcessState(String instanceId, STATE nextState);

	public Map<String, Object> getInstanceData(String instanceId);

	public void setInstanceData(String instanceId, Map<String, Object> data);

	public void signalExecution(String executionId, String signal);

	public void deleteInstance(String instanceId);

	// result means nothing
	public void endInstance(String instanceId, RESULT result);
}
