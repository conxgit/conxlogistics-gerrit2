package com.conx.logistics.kernel.bpm.impl.jbpm.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Connection;
import org.drools.definition.process.Node;
import org.drools.definition.process.NodeContainer;
import org.drools.definition.process.WorkflowProcess;
import org.jboss.bpm.console.client.model.ActiveNodeInfo;
import org.jboss.bpm.console.client.model.DiagramInfo;
import org.jboss.bpm.console.client.model.DiagramNodeInfo;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.workflow.core.node.HumanTaskNode;
import org.jbpm.workflow.core.node.Split;
import org.jbpm.workflow.core.node.StartNode;

import com.conx.logistics.kernel.bpm.impl.jbpm.BPMServerImpl;
import com.conx.logistics.kernel.bpm.impl.jbpm.shared.utils.GuvnorConnectionUtils;

public class ProcessGraphManagement {
	private BPMServerImpl bpmService;
	private JPAProcessInstanceDbLog jpaDbLog;
	
	public ProcessGraphManagement(BPMServerImpl bpmService) {
		this.bpmService = bpmService;
		jpaDbLog = new JPAProcessInstanceDbLog(bpmService.getJbpmEMF());
	}
	
	public List<ActiveNodeInfo> getActiveNodeInfo(String instanceId) {
		ProcessInstanceLog processInstance = jpaDbLog.findProcessInstance(new Long(instanceId));
		if (processInstance == null) {
			throw new IllegalArgumentException("Could not find process instance " + instanceId);
		} 
		Map<String, NodeInstanceLog> nodeInstances = new HashMap<String, NodeInstanceLog>();
		for (NodeInstanceLog nodeInstance: jpaDbLog.findNodeInstances(new Long(instanceId))) {
			if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
				nodeInstances.put(nodeInstance.getNodeInstanceId(), nodeInstance);
			} else {
				nodeInstances.remove(nodeInstance.getNodeInstanceId());
			}
		}
		if (!nodeInstances.isEmpty()) {
			List<ActiveNodeInfo> result = new ArrayList<ActiveNodeInfo>();
			for (NodeInstanceLog nodeInstance: nodeInstances.values()) {
				boolean found = false;
				DiagramInfo diagramInfo = getDiagramInfo(processInstance.getProcessId());
				if (diagramInfo != null) {
    				for (DiagramNodeInfo nodeInfo: diagramInfo.getNodeList()) {
    					if (nodeInfo.getName().equals("id=" + nodeInstance.getNodeId())) {
    						result.add(new ActiveNodeInfo(diagramInfo.getWidth(), diagramInfo.getHeight(), nodeInfo));
    						found = true;
    						break;
    					}
    				}
				} else {
				    throw new IllegalArgumentException("Could not find info for diagram for process " + processInstance.getProcessId());
				}
				if (!found) {
					throw new IllegalArgumentException("Could not find info for node "
						+ nodeInstance.getNodeId() + " of process " + processInstance.getProcessId());
				}
			}
			return result;
		}
		return null;
	}
	
	public DiagramInfo getDiagramInfo(String processId) {
		org.drools.definition.process.Process process = bpmService.getKsession().getKnowledgeBase().getProcess(processId);
		DiagramInfo result = new DiagramInfo();
		List<DiagramNodeInfo> nodeList = new ArrayList<DiagramNodeInfo>();
		if (process instanceof WorkflowProcess) {
			addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(), "id=");
		}
		result.setNodeList(nodeList);
		return result;
	}
	
	private void addNodesInfo(List<DiagramNodeInfo> nodeInfos, Node[] nodes, String prefix) {
		for (Node node: nodes) {
			nodeInfos.add(new DiagramNodeInfo(
				prefix + node.getId(),
				(Integer) node.getMetaData().get("x"),
				(Integer) node.getMetaData().get("y"),
				(Integer) node.getMetaData().get("width"),
				(Integer) node.getMetaData().get("height")));
			if (node instanceof NodeContainer) {
				addNodesInfo(nodeInfos, ((NodeContainer) node).getNodes(), prefix + node.getId() + ":");
			}
		}
	}
	
	public byte[] getProcessImage(String processId) {
		InputStream is = ProcessGraphManagement.class.getResourceAsStream("/" + processId + ".png");
		if (is != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				transfer(is, os);
			} catch (IOException e) {
				throw new RuntimeException("Could not read process image: " + e.getMessage());
			}
			return os.toByteArray();
		}
		
		// now check guvnor
		GuvnorConnectionUtils guvnorUtils = new GuvnorConnectionUtils();
		if(guvnorUtils.guvnorExists()) {
			try {
				return guvnorUtils.getProcessImageFromGuvnor(processId);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		} else {
		}
		
		return null;
	}
	
	private static final int BUFFER_SIZE = 512;

	public static int transfer(InputStream in, OutputStream out) throws IOException {
		int total = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = in.read(buffer);
		while (bytesRead != -1) {
			out.write(buffer, 0, bytesRead);
			total += bytesRead;
			bytesRead = in.read(buffer);
		}
		return total;
	}

	public URL getDiagramURL(String id) {
		GuvnorConnectionUtils guvnorUtils = new GuvnorConnectionUtils();
        if(guvnorUtils.guvnorExists()) {
        	try {
				return new URL(guvnorUtils.getProcessImageURLFromGuvnor(id));
			} catch (Throwable t) {
				t.printStackTrace();
			}
        } else {
        }
		
		URL result = ProcessGraphManagement.class.getResource("/" + id + ".png");
		if (result != null) {
			return result;
		}
		
		return null;
	}

	public List<ActiveNodeInfo> getNodeInfoForActivities(
			String processDefinitionId, List<String> activities) {
		// TODO Auto-generated method stub
		return new ArrayList<ActiveNodeInfo>();
	}
	
	public List<HumanTaskNode> getProcessHumanTaskNodes(String definitionId) {
		List<HumanTaskNode> list = new ArrayList<HumanTaskNode>();
		org.drools.definition.process.Process proc = bpmService.getKsession().getKnowledgeBase().getProcess(definitionId);
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			Node node;
			List<Connection> connections;
			for (Node n : nodes) {
				if (n instanceof StartNode) {
					node = n;
					while (node != null) {
						if (node instanceof HumanTaskNode) {
							list.add((HumanTaskNode)node);
						}
						connections = null;
						connections = node.getOutgoingConnections("DROOLS_DEFAULT");
						if (connections != null && connections.size() > 0) {
							node = node.getOutgoingConnections("DROOLS_DEFAULT").get(0).getTo();
						} else {
							break;
						}
					}
				}
			}
			return list;
		}
		return null;
	}
	
	
	public boolean humanTaskNodeIsGatewayDriver(String taskname, String definitionId) {
		boolean res = false;
		
		Node node = findHumanTaskNodeForTask(taskname,definitionId);

		Node nextNode = getNextSplitNode(taskname, node);
		
		return (nextNode != null);
	}

	public Node getNextSplitNode(String taskname, Node node) {
		Node splitNode = null;
		
		//Ask the question
		if (node == null) {
			throw new IllegalArgumentException("HumanTask node for Task ["+taskname+"] not found");
		}			
		if (!(node instanceof HumanTaskNode)) {
			throw new IllegalArgumentException("Task ["+taskname+"] is not of HumanTaskNode type");
		}
		List<Connection> connections = null;
		connections = node.getOutgoingConnections("DROOLS_DEFAULT");
		if (connections != null && connections.size() > 0) {
			node = node.getOutgoingConnections("DROOLS_DEFAULT").get(0).getTo();
			if (node instanceof Split)
			{
				splitNode = node;
			}
		} else {
			throw new IllegalArgumentException("Task ["+taskname+"] node has zero outgoing connections");
		}
		return splitNode;
	}	
	
	public HumanTaskNode findHumanTaskNodeForTask(String taskname, String definitionId) {
		Node res = null;
		org.drools.definition.process.Process proc = bpmService.getKsession().getKnowledgeBase().getProcess(definitionId);		
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			Node node = null;
			List<Connection> connections = null;
			//Find task node
			for (Node n : nodes) {
				if (n instanceof StartNode) {
					node = n;
					while (node != null) {
						if (node.getName().equals(taskname))
						{
							res = node;
							break;
						}
						connections = null;
						connections = node.getOutgoingConnections("DROOLS_DEFAULT");
						if (connections != null && connections.size() > 0) {
							node = node.getOutgoingConnections("DROOLS_DEFAULT").get(0).getTo();
						} else {
							break;
						}						
					}
				}
			}
		}
		return (HumanTaskNode)res;
	}
	
	public List<HumanTaskNode> findAllHumanTaskNodesAfterTask(String taskname, String definitionId) {
		
		Node node_ = findHumanTaskNodeForTask(taskname,definitionId);
		
		List<HumanTaskNode> list = getProcessHumanTaskNodes(definitionId);
		
		//Grab all HT's after index (excl.)
		int index = list.indexOf(node_);
		
		return list.subList(index, list.size()-1);
	}
	
	public List<HumanTaskNode> findAllHumanTaskNodesBeforeTask(String taskname, String definitionId) {
		
		Node node_ = findHumanTaskNodeForTask(taskname,definitionId);
		
		List<HumanTaskNode> list = new ArrayList<HumanTaskNode>();

		Node node = node_;
		List<Connection> connections;
		while (node != null) {
			if (node instanceof HumanTaskNode) {
				list.add((HumanTaskNode)node);
			}
			connections = null;
			connections = node.getIncomingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				node = node.getIncomingConnections("DROOLS_DEFAULT").get(0).getTo();
			} else {
				break;
			}
		}
		
		return list;
	}	

	public List<Node> getActiveNode(String instanceId) {
		ProcessInstanceLog processInstanceLog = jpaDbLog.findProcessInstance(new Long(instanceId));
		if (processInstanceLog != null) {
			Map<String, NodeInstanceLog> nodeInstanceLogs = new HashMap<String, NodeInstanceLog>();
			List<Node> result = new ArrayList<Node>();
			for (NodeInstanceLog nodeInstance: getAllNodeInstances(instanceId)) {
				if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
					nodeInstanceLogs.put(nodeInstance.getNodeInstanceId(), nodeInstance);
				} else {
					nodeInstanceLogs.remove(nodeInstance.getNodeInstanceId());
				}
			}
			if (!nodeInstanceLogs.isEmpty()) {
				org.drools.definition.process.Process proc = bpmService.getKsession().getKnowledgeBase().getProcess(processInstanceLog.getProcessId());
				Node[] nodes = ((WorkflowProcess) proc).getNodes();
				for (NodeInstanceLog nodeInstanceLog : nodeInstanceLogs.values()) {
					for (Node n : nodes) {
						if (n.getName().equals(nodeInstanceLog.getNodeName())) {
							result.add(n);
						}
					}
				}
			}
			return result;
		}
		return null;
	}
	
	public List<NodeInstanceLog> getAllNodeInstances(String instanceId) {
		Map<String, NodeInstanceLog> nodeInstanceLogs = new HashMap<String, NodeInstanceLog>();
		List<NodeInstanceLog> result = new ArrayList<NodeInstanceLog>();
		
		ProcessInstanceLog processInstanceLog = jpaDbLog.findProcessInstance(new Long(instanceId));
		if (processInstanceLog != null) {
			for (NodeInstanceLog nodeInstance: jpaDbLog.findNodeInstances(new Long(instanceId))) {
				if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
					nodeInstanceLogs.put(nodeInstance.getNodeInstanceId(), nodeInstance);
				} else {
					nodeInstanceLogs.remove(nodeInstance.getNodeInstanceId());
				}
			}		
			for (NodeInstanceLog nodeInstanceLog : nodeInstanceLogs.values()) {
				result.add(nodeInstanceLog);
			}
		}
		
		return result;
	}
}
