package com.conx.logistics.kernel.bpm.impl.jbpm.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.definition.process.Connection;
import org.drools.definition.process.Node;
import org.drools.definition.process.NodeContainer;
import org.drools.definition.process.WorkflowProcess;
import org.jboss.bpm.console.client.model.ActiveNodeInfo;
import org.jboss.bpm.console.client.model.DiagramInfo;
import org.jboss.bpm.console.client.model.DiagramNodeInfo;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.workflow.core.node.EndNode;
import org.jbpm.workflow.core.node.HumanTaskNode;
import org.jbpm.workflow.core.node.Join;
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
		ProcessInstanceLog processInstance = jpaDbLog
				.findProcessInstance(new Long(instanceId));
		if (processInstance == null) {
			throw new IllegalArgumentException(
					"Could not find process instance " + instanceId);
		}
		Map<String, NodeInstanceLog> nodeInstances = new HashMap<String, NodeInstanceLog>();
		for (NodeInstanceLog nodeInstance : jpaDbLog
				.findNodeInstances(new Long(instanceId))) {
			if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
				nodeInstances.put(nodeInstance.getNodeInstanceId(),
						nodeInstance);
			} else {
				nodeInstances.remove(nodeInstance.getNodeInstanceId());
			}
		}
		if (!nodeInstances.isEmpty()) {
			List<ActiveNodeInfo> result = new ArrayList<ActiveNodeInfo>();
			for (NodeInstanceLog nodeInstance : nodeInstances.values()) {
				boolean found = false;
				DiagramInfo diagramInfo = getDiagramInfo(processInstance
						.getProcessId());
				if (diagramInfo != null) {
					for (DiagramNodeInfo nodeInfo : diagramInfo.getNodeList()) {
						if (nodeInfo.getName().equals(
								"id=" + nodeInstance.getNodeId())) {
							result.add(new ActiveNodeInfo(diagramInfo
									.getWidth(), diagramInfo.getHeight(),
									nodeInfo));
							found = true;
							break;
						}
					}
				} else {
					throw new IllegalArgumentException(
							"Could not find info for diagram for process "
									+ processInstance.getProcessId());
				}
				if (!found) {
					throw new IllegalArgumentException(
							"Could not find info for node "
									+ nodeInstance.getNodeId() + " of process "
									+ processInstance.getProcessId());
				}
			}
			return result;
		}
		return null;
	}

	public DiagramInfo getDiagramInfo(String processId) {
		org.drools.definition.process.Process process = bpmService
				.getKsession().getKnowledgeBase().getProcess(processId);
		DiagramInfo result = new DiagramInfo();
		List<DiagramNodeInfo> nodeList = new ArrayList<DiagramNodeInfo>();
		if (process instanceof WorkflowProcess) {
			addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(),
					"id=");
		}
		result.setNodeList(nodeList);
		return result;
	}

	private void addNodesInfo(List<DiagramNodeInfo> nodeInfos, Node[] nodes,
			String prefix) {
		for (Node node : nodes) {
			nodeInfos.add(new DiagramNodeInfo(prefix + node.getId(),
					(Integer) node.getMetaData().get("x"), (Integer) node
							.getMetaData().get("y"), (Integer) node
							.getMetaData().get("width"), (Integer) node
							.getMetaData().get("height")));
			if (node instanceof NodeContainer) {
				addNodesInfo(nodeInfos, ((NodeContainer) node).getNodes(),
						prefix + node.getId() + ":");
			}
		}
	}

	public byte[] getProcessImage(String processId) {
		InputStream is = ProcessGraphManagement.class.getResourceAsStream("/"
				+ processId + ".png");
		if (is != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				transfer(is, os);
			} catch (IOException e) {
				throw new RuntimeException("Could not read process image: "
						+ e.getMessage());
			}
			return os.toByteArray();
		}

		// now check guvnor
		GuvnorConnectionUtils guvnorUtils = new GuvnorConnectionUtils();
		if (guvnorUtils.guvnorExists()) {
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

	public static int transfer(InputStream in, OutputStream out)
			throws IOException {
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
		if (guvnorUtils.guvnorExists()) {
			try {
				return new URL(guvnorUtils.getProcessImageURLFromGuvnor(id));
			} catch (Throwable t) {
				t.printStackTrace();
			}
		} else {
		}

		URL result = ProcessGraphManagement.class
				.getResource("/" + id + ".png");
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
	
	public Map<String, List<Node>> findAllNodePaths(
			String definitionId) {
		Map<String, List<Node>> paths = new HashMap<String, List<Node>>();
		Map<String, List<Node>> finalizedPaths = new HashMap<String, List<Node>>();
		List<Node> list = new ArrayList<Node>();
		List<Node> finalizedList = null;
		Node startNode = null;
		org.drools.definition.process.Process proc = bpmService.getKsession()
				.getKnowledgeBase().getProcess(definitionId);
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			Node node;
			List<Connection> connections;
			for (Node n : nodes) {
				if (n instanceof StartNode) {
					startNode = n;
					createNodePaths(paths,"",list,n);
					break;
				}
			}
			// Add Start node to all paths
			for (String key : paths.keySet())
			{
				list = paths.get(key);
				finalizedList = new ArrayList<Node>();
				finalizedList.add(startNode);
				finalizedList.addAll(list);
				finalizedPaths.put("Start-->"+key, finalizedList);
			}
			return finalizedPaths;
		}
		return null;
	}
	
	private void createNodePaths(Map<String, List<Node>> paths,String pathName, List<Node> pathNodeList, Node node) {

		
		//If one of the outgoing conn of this node is Split, 
		List<Connection> connections;
		List<Node> nodeList;
		Map<String,String> keyPathsToRemove = new HashMap<String,String>();
		Node n;
		if (node instanceof Split)
		{
			connections = node
					.getOutgoingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				String path = null;
				ArrayList<Node> rootNodeList = new ArrayList<Node>();
				rootNodeList.addAll(pathNodeList);
				
				//Create paths for each OUT connection
				for (Connection cs : connections) {
					nodeList = new ArrayList<Node>();
					nodeList.addAll(pathNodeList);
					
					n = cs.getTo();
					if (!"Unknown".equals(getNodeName(n)))
					{
						path = (pathName.length() > 0)?pathName+"-->"+getNodeName(n):getNodeName(n);
						nodeList.add(n);
					}		
					else
						path = pathName;					
					createNodePaths(paths,path,nodeList,n);
				}
				
				//Also, add a Splitter Path for e.g. PageFlow purposes
				path = pathName;
				paths.put(path, rootNodeList);
			}
		}	
		else
		{
			//Traverse next OUT connection nodes
			connections = null;
			connections = node.getOutgoingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				for (Connection c : node.getOutgoingConnections("DROOLS_DEFAULT"))
				{
					Node node_ = c.getTo();
					String path = null;
					if (!"Unknown".equals(getNodeName(node_)))
					{
						path = (pathName.length() > 0)?pathName+"-->"+getNodeName(node_):getNodeName(node_);
						pathNodeList.add(node_);
					}
					else
						path = pathName;
					createNodePaths(paths,path,pathNodeList,node_);
					
				}
			}
			else
			{
				paths.put(pathName, pathNodeList);
			}
		}
	}	
	
	private String getNodeName(Node n) {
		String name = "Unknown";
		
		if (n instanceof HumanTaskNode)
		{
			name = n.getName();
		}
		else if (n instanceof StartNode)
		{
			name = "Start";
		}
		else if (n instanceof EndNode)
		{
			name = "End";
		}
		else if (n instanceof Split)
		{
			name = "Split";
		}
		else if (n instanceof Join)
		{
			name = "Join";
		}

		return name;
	}

	private void addNodeToPath(Map<String, List<Node>> paths, Map<String, String> keyPathsToRemove, Node n) {
		
		List<Node> nodesBefore = findLastNodesBeforeNode(paths,n);
		
		List<Node> nodeList;
		List<Node> rootPathList = null;
		Set<String> pathsSet = new HashSet<String>();
		pathsSet.addAll(paths.keySet());
		
		String name = null;
		
		for (Node nodeBefore : nodesBefore)
		{
			for (String key : pathsSet)
			{
				if (nodeBefore.getName().length() > 0)
				{
					name = nodeBefore.getName();
				}
				else if (nodeBefore instanceof StartNode)
				{
					name = "Start";
				}
				else if (nodeBefore instanceof EndNode)
				{
					name = "End";
				}
				else if (nodeBefore instanceof Split)
				{
					name = "Split";
				}
				else if (nodeBefore instanceof Join)
				{
					name = "Join";
				}
				else
					name = "Unknown";
				
				
				
				if (key.endsWith(nodeBefore.getName()))
				{
					nodeList = paths.get(key);
					rootPathList = new ArrayList<Node>();
					rootPathList.addAll(nodeList);
	
					rootPathList.add(n);
					
					paths.put(key+"-->"+n.getName(),rootPathList);
					paths.put(key+"#",nodeList);
					paths.remove(key);
					//keyPathsToRemove.put(key, key);
				}
				else if (key.endsWith(nodeBefore.getName()+"#"))
				{
					String key_ = key.replace("#","");
					
					nodeList = paths.get(key);
					rootPathList = new ArrayList<Node>();
					rootPathList.addAll(nodeList);
	
					rootPathList.add(n);
					
					paths.put(key_+"-->"+n.getName(),rootPathList);
					paths.put(key+"#",nodeList);			
				}
			}
		}
	}	
	
	public List<Node> findLastNodesBeforeNode(Map<String, List<Node>> paths, Node node_) {
		List<Connection> connections;
		
		List<Node> res = new ArrayList<Node>();

		connections = null;
		connections = node_.getIncomingConnections("DROOLS_DEFAULT");
		if (connections != null && connections.size() > 0) {
			for (Connection connection : connections)
			{
				res.add(connection.getFrom());
			}
		} 
		
		return res;
	}	
	

	public Map<String, List<HumanTaskNode>> findAllHumanTaskPaths(
			String definitionId) {
		Map<String, List<HumanTaskNode>> paths = new HashMap<String, List<HumanTaskNode>>();
		List<HumanTaskNode> list = null;
		org.drools.definition.process.Process proc = bpmService.getKsession()
				.getKnowledgeBase().getProcess(definitionId);
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			Node node;
			List<Connection> connections;
			for (Node n : nodes) {
				if (n instanceof StartNode) {
					node = n;
					while (node != null) {
						if (node instanceof HumanTaskNode) {
							if (paths.isEmpty())// First HN
							{
								list = new ArrayList<HumanTaskNode>();
								list.add((HumanTaskNode) node);
								paths.put(node.getName(), list);
							}

							connections = null;
							connections = node
									.getOutgoingConnections("DROOLS_DEFAULT");
							if (connections != null && connections.size() > 0) {
								createHumanNodePaths(paths,node.getOutgoingConnections("DROOLS_DEFAULT").get(0).getTo());
								break;
							}
						}

						connections = null;
						connections = node
								.getOutgoingConnections("DROOLS_DEFAULT");
						if (connections != null && connections.size() > 0) {
							for (Connection cs : connections) {
								// Create path for each
							}
							node = node
									.getOutgoingConnections("DROOLS_DEFAULT")
									.get(0).getTo();
						} else {
							break;
						}
					}
				}
			}
			return paths;
		}
		return null;
	}

	private void createHumanNodePaths(Map<String, List<HumanTaskNode>> paths, Node node) {

		
		//If one of the outgoing conn of this node is Split, 
		List<Connection> connections;
		List<HumanTaskNode> nodeList;
		Map<String,String> keyPathsToRemove = new HashMap<String,String>();
		Node n;
		if (node instanceof Split)
		{
			connections = node
					.getOutgoingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				for (Connection cs : connections) {
					n = cs.getTo();
					//if (n instanceof HumanTaskNode)
					//{
					//addHumanTaskNodeToPath(paths, nodeBefore,keyPathsToRemove, n);
					createHumanNodePaths(paths, n);
					//}
				}
/*				for (String key : keyPathsToRemove.keySet())
				{
					paths.remove(key);
				}*/
			}
		}	
		else
		{
			//If HN, then simply add a new path and return
			if (node instanceof HumanTaskNode)
			{
				addHumanTaskNodeToPath(paths,keyPathsToRemove, node);
			}
			//Traverse next OUT connection nodes
			connections = null;
			connections = node.getOutgoingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				for (Connection c : node.getOutgoingConnections("DROOLS_DEFAULT"))
				{
					Node node_ = c.getTo();
					createHumanNodePaths(paths, node_);
				}
			}
		}
	}

	private void addHumanTaskNodeToPath(Map<String, List<HumanTaskNode>> paths, Map<String, String> keyPathsToRemove, Node n) {
		
		HumanTaskNode nodeBefore = findLastHumanTaskNodeBeforeNode(paths,n);
		
		List<HumanTaskNode> nodeList;
		List<HumanTaskNode> rootPathList = null;
		Set<String> pathsSet = new HashSet<String>();
		pathsSet.addAll(paths.keySet());
		for (String key : pathsSet)
		{
			if (key.endsWith(nodeBefore.getName()))
			{
				nodeList = paths.get(key);
				rootPathList = new ArrayList<HumanTaskNode>();
				rootPathList.addAll(nodeList);

				rootPathList.add((HumanTaskNode)n);
				
				paths.put(key+"-->"+n.getName(),rootPathList);
				paths.put(key+"#",nodeList);
				paths.remove(key);
				//keyPathsToRemove.put(key, key);
			}
			else if (key.endsWith(nodeBefore.getName()+"#"))
			{
				String key_ = key.replace("#","");
				
				nodeList = paths.get(key);
				rootPathList = new ArrayList<HumanTaskNode>();
				rootPathList.addAll(nodeList);

				rootPathList.add((HumanTaskNode)n);
				
				paths.put(key_+"-->"+n.getName(),rootPathList);
				paths.put(key+"#",nodeList);			
			}
		}
	}

	public List<HumanTaskNode> getProcessHumanTaskNodes(String definitionId) {
		List<HumanTaskNode> list = new ArrayList<HumanTaskNode>();
		org.drools.definition.process.Process proc = bpmService.getKsession()
				.getKnowledgeBase().getProcess(definitionId);
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			Node node;
			List<Connection> connections;
			for (Node n : nodes) {
				if (n instanceof StartNode) {
					node = n;
					while (node != null) {
						if (node instanceof HumanTaskNode) {
							list.add((HumanTaskNode) node);
						}
						connections = null;
						connections = node
								.getOutgoingConnections("DROOLS_DEFAULT");
						if (connections != null && connections.size() > 0) {
							node = node
									.getOutgoingConnections("DROOLS_DEFAULT")
									.get(0).getTo();
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

	public boolean humanTaskNodeIsGatewayDriver(String taskname,
			String definitionId) {
		boolean res = false;

		Node node = findHumanTaskNodeForTask(taskname, definitionId);

		Node nextNode = getNextSplitNode(taskname, node);

		return (nextNode != null);
	}

	public Node getNextSplitNode(String taskname, Node node) {
		Node splitNode = null;

		// Ask the question
		if (node == null) {
			throw new IllegalArgumentException("HumanTask node for Task ["
					+ taskname + "] not found");
		}
		if (!(node instanceof HumanTaskNode)) {
			throw new IllegalArgumentException("Task [" + taskname
					+ "] is not of HumanTaskNode type");
		}
		List<Connection> connections = null;
		connections = node.getOutgoingConnections("DROOLS_DEFAULT");
		if (connections != null && connections.size() > 0) {
			node = node.getOutgoingConnections("DROOLS_DEFAULT").get(0).getTo();
			if (node instanceof Split) {
				splitNode = node;
			}
		} else {
			throw new IllegalArgumentException("Task [" + taskname
					+ "] node has zero outgoing connections");
		}
		return splitNode;
	}

	public HumanTaskNode findHumanTaskNodeForTask(String taskname,
			String definitionId) {
		HumanTaskNode res = null;

		List<HumanTaskNode> htnodes = extractHumanTaskNodes(findAllNodePaths(definitionId));	
		for (HumanTaskNode htnode : htnodes)
		{
			if (taskname.equals(htnode.getName()))
				res = htnode;
				
		}
		
		return (HumanTaskNode) res;
	}

	private List<HumanTaskNode> extractHumanTaskNodes(
			Map<String, List<Node>> nodePaths) {
		List<HumanTaskNode> res = new ArrayList<HumanTaskNode>();
		for (String path_ : nodePaths.keySet())
		{
			List<Node> nList = nodePaths.get(path_);
			for (Node n : nList)
			{
				if (n instanceof HumanTaskNode)
					res.add((HumanTaskNode)n);
			}
		}
		return res;
	}

	public List<HumanTaskNode> findAllHumanTaskNodesAfterTask(String taskname,
			String definitionId) {

		Node node_ = findHumanTaskNodeForTask(taskname, definitionId);

		List<HumanTaskNode> list = getProcessHumanTaskNodes(definitionId);

		// Grab all HT's after index (excl.)
		int index = list.indexOf(node_);

		return list.subList(index, list.size() - 1);
	}

	public List<HumanTaskNode> findAllHumanTaskNodesBeforeTask(String taskname,
			String definitionId) {

		Node node_ = findHumanTaskNodeForTask(taskname, definitionId);

		List<HumanTaskNode> list = new ArrayList<HumanTaskNode>();

		Node node = node_;
		List<Connection> connections;
		while (node != null) {
			if (node instanceof HumanTaskNode) {
				list.add((HumanTaskNode) node);
			}
			connections = null;
			connections = node.getIncomingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				node = node.getIncomingConnections("DROOLS_DEFAULT").get(0)
						.getTo();
			} else {
				break;
			}
		}

		return list;
	}
	
	public HumanTaskNode findNextHumanTaskNodeAfterNode(Node node_) {

		HumanTaskNode nextHTNode = null;

		Node node = node_;
		List<Connection> connections;
		while (node != null) {
			connections = null;
			connections = node.getOutgoingConnections("DROOLS_DEFAULT");
			if (connections != null && connections.size() > 0) {
				node = connections.get(0).getTo();
			} else {
				break;
			}
			
			if (node instanceof HumanTaskNode) {
				nextHTNode = (HumanTaskNode)node;
				break;
			}

		}

		return nextHTNode;
	}		
	
	public HumanTaskNode findLastHumanTaskNodeBeforeNode(Map<String, List<HumanTaskNode>> paths, Node node_) {

		HumanTaskNode lastHTNode = null;

		Node node = node_;
		List<Connection> connections;
		Set<String> allCurrentPathkeys = paths.keySet();
		while (node != null) {
			connections = null;
			connections = node.getIncomingConnections("DROOLS_DEFAULT");
			boolean matchFound = false;
			if (connections != null && connections.size() > 0) {
				if (connections.size() == 1)
				{
					node = connections.get(0).getFrom();
				}
				else
				{
					//Select from node that has no matching suffices
					Node fromNode;
					boolean prevNodeFound = false;
					for (Connection c : connections)
					{
						matchFound = false;
						fromNode = c.getFrom();
						if (!fromNode.getName().isEmpty())
						{
							if (pathForNodeNotAlreadyConnected(allCurrentPathkeys,fromNode))
							{
								node = fromNode;
								prevNodeFound = true;
								break;
							}//Finish last part
							else if (findNextHumanTaskNodeAfterNode(fromNode) != null && findNextHumanTaskNodeAfterNode(fromNode).getId() == node_.getId())
							{
								node = fromNode;
								prevNodeFound = true;
								break;								
							}
						}
					}
					if (!prevNodeFound)
					{
						lastHTNode = null;
						node = null;
					}
				}
			} else {
				break;
			}
			
			if (node instanceof HumanTaskNode) {
				lastHTNode = (HumanTaskNode)node;
				break;
			}

		}

		return lastHTNode;
	}	

	private boolean pathForNodeNotAlreadyConnected(Set<String> pathKeySet, Node fromNode) {
		boolean pathHasNot = !stringSetHasValueWithSuffix(pathKeySet,"-->"+fromNode.getName());
		pathHasNot = pathHasNot && !stringSetHasValueWithSuffix(pathKeySet,"#-->"+fromNode.getName());
		return pathHasNot;
	}

	private boolean stringSetHasValueWithSuffix(Set<String> pathKeySet,
			String suffix) {
		for (String key : pathKeySet)
		{
			if (key.endsWith(suffix))
			{
				return true;
			}
		}
		return false;
	}

	public List<Node> getActiveNode(String instanceId) {
		ProcessInstanceLog processInstanceLog = jpaDbLog
				.findProcessInstance(new Long(instanceId));
		if (processInstanceLog != null) {
			Map<String, NodeInstanceLog> nodeInstanceLogs = new HashMap<String, NodeInstanceLog>();
			List<Node> result = new ArrayList<Node>();
			for (NodeInstanceLog nodeInstance : getAllNodeInstances(instanceId)) {
				if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
					nodeInstanceLogs.put(nodeInstance.getNodeInstanceId(),
							nodeInstance);
				} else {
					nodeInstanceLogs.remove(nodeInstance.getNodeInstanceId());
				}
			}
			if (!nodeInstanceLogs.isEmpty()) {
				org.drools.definition.process.Process proc = bpmService
						.getKsession().getKnowledgeBase()
						.getProcess(processInstanceLog.getProcessId());
				Node[] nodes = ((WorkflowProcess) proc).getNodes();
				for (NodeInstanceLog nodeInstanceLog : nodeInstanceLogs
						.values()) {
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

		ProcessInstanceLog processInstanceLog = jpaDbLog
				.findProcessInstance(new Long(instanceId));
		if (processInstanceLog != null) {
			for (NodeInstanceLog nodeInstance : jpaDbLog
					.findNodeInstances(new Long(instanceId))) {
				if (nodeInstance.getType() == NodeInstanceLog.TYPE_ENTER) {
					nodeInstanceLogs.put(nodeInstance.getNodeInstanceId(),
							nodeInstance);
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
