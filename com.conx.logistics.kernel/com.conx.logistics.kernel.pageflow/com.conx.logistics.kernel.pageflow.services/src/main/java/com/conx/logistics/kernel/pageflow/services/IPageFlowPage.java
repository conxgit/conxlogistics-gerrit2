package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;

public interface IPageFlowPage {
	
	public static final String PROCESS_ID = "PROCESS_ID"; // Process Id in BPMN
//	public static final String TASK_ID = "TASK_ID"; // Task Id in BPMN
	
	public String getTaskId();
	
	public Component getPageComponent();
	
	public void setDataContainerMap(Map<String,Container> containerMap);
	
	public Map<String,Container> getDataContainerMap();
	
	public String getTitle();
	
	@Override
	public boolean equals(Object o);
}
