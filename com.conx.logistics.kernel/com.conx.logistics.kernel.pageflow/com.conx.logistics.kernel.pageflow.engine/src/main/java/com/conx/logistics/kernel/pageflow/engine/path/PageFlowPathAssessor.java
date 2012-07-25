package com.conx.logistics.kernel.pageflow.engine.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Node;
import org.jbpm.workflow.core.node.HumanTaskNode;

import com.conx.logistics.kernel.pageflow.services.PageFlowPage;

public class PageFlowPathAssessor {
	private String name;
	private List<Node> nodePath;
	private Map<String,List<Node>> possibleNextPaths;
	
	private List<PageFlowPage> currentOrderedPageList;
	
	private Map<String,PageFlowPage> pageCache;
	private boolean pagesChanged;
	
	public PageFlowPathAssessor(){
	}
	
	public PageFlowPathAssessor(
			    String name, 
				List<Node> nodePath,
			    Map<String, List<Node>> possibleNextPaths,
			    Map<String,PageFlowPage> pageCache) {
		super();
		this.name = name;
		this.nodePath = nodePath;
		this.possibleNextPaths = possibleNextPaths;
		this.pageCache = pageCache;
		
		createOrderedPageList();
	}
	
	public boolean restActivePages(HumanTaskNode currentTask)
	{
		String name_ = null;
		List<Node> nodePath_ = null;
		
		//Find next path
		for (String pathKey : possibleNextPaths.keySet())
		{
			if (pathKey.startsWith(name+"-->"+currentTask.getName()))
			{
				name_ = pathKey;
				nodePath_ = possibleNextPaths.get(pathKey);
			}
		}
		
		if (name != name_)
			this.pagesChanged = true;
		else
			this.pagesChanged = false;
		
		
		name = name_;
		nodePath = nodePath_;
		
		if (this.pagesChanged)
		{
			createOrderedPageList();
		}
		
		return this.pagesChanged;
	}

	private void createOrderedPageList() {
		//Create currentOrderedPageList
		this.currentOrderedPageList = new ArrayList<PageFlowPage>();
		for (Node node : nodePath)
		{
			if (node instanceof HumanTaskNode)
			{
				this.currentOrderedPageList.add(pageCache.get(node.getName()));
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Node> getNodePath() {
		return nodePath;
	}

	public Map<String, List<Node>> getPossibleNextPaths() {
		return possibleNextPaths;
	}

	public List<PageFlowPage> getCurrentOrderedPageList() {
		return currentOrderedPageList;
	}

	public Map<String, PageFlowPage> getPageCache() {
		return pageCache;
	}

	public boolean isPagesChanged() {
		return pagesChanged;
	}
}
