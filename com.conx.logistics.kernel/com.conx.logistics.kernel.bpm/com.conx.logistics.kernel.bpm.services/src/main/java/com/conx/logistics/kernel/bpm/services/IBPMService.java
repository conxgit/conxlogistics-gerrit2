package com.conx.logistics.kernel.bpm.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.drools.definition.process.Node;
import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.RESULT;
import org.jboss.bpm.console.client.model.ProcessInstanceRef.STATE;
import org.jboss.bpm.console.client.model.TaskRef;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.workflow.core.node.HumanTaskNode;

public interface IBPMService {
	/**
	 * process Management methods
	 */
	public List<ProcessDefinitionRef> getProcessDefinitions();

	public ProcessDefinitionRef getProcessDefinition(String definitionId); // DefinitionId refers to processId in bpmn

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
	
	public EntityManagerFactory getJbpmEMF();
	
	public List<HumanTaskNode> getProcessHumanTaskNodes(String definitionId);
	
	public List<Node> getActiveNode(String instanceId);
	
    public Map<String, Object> getProcessInstanceVariables(String processInstanceId);
    
    public void setProcessInstanceVariables(final String processInstanceId, final Map<String, Object> variables);
    
	public Map<String, String> findVariableInstances(Long processInstanceId);    
	
	/**
	 * Task Management methods
	 */
	public TaskRef getTaskById(long taskId);
	public Task getTaskObjectById(long taskId);
	public void assignTask(long taskId, String idRef, String userId);
	public void nominate(long taskId, String userId);
	public void startTask(long taskId, String userId);
	public void completeTask(long taskId, Map<String, Object> data, String userId);
	public void completeTask(long taskId, String outcome, Map<String, Object> data, String userId);
	public void completeTask(long taskId, ContentData contentData, String userId);
	public void releaseTask(long taskId, String userId);
	public List<TaskRef> getAssignedTasks(String idRef);
	public List<TaskRef> getUnassignedTasks(String idRef, String participationType);
	public void skipTask(long taskId, String userId);
	
	
	/**
	 * Task Amdin methiods
	 */
	public List<Task> getCreatedTasksByProcessId(Long processInstanceId);
	public List<TaskSummary> getCreatedTaskSummariesByProcessId(Long processInstanceId);
	public List<Task> getReadyTasksByProcessId(Long processInstanceId);
	public List<Task> getReservedTasksByProcessId(Long processInstanceId);
	public List<Task> getReadyAndReservedTasksByProcessId(Long processInstanceId);
	public Content getTaskContent(long taskId);
	public Object getTaskContentObject(Task task) throws IOException,
			ClassNotFoundException;
	public boolean humanTaskNodeIsGatewayDriver(String taskname, String definitionId);
	public List<HumanTaskNode> findAllHumanTaskNodesAfterTask(String taskname,
			String definitionId);
	public List<HumanTaskNode> findAllHumanTaskNodesBeforeTask(String taskname,
			String definitionId);	
	public Node getNextSplitNode(String taskname, Node node);
	public HumanTaskNode findHumanTaskNodeForTask(String taskname, String definitionId);
	public List<NodeInstanceLog> getAllNodeInstances(String instanceId);
	public Map<String, List<HumanTaskNode>> findAllHumanTaskPaths(String definitionId);
	public Map<String, List<Node>> findAllNodePaths(String definitionId);

	
}
