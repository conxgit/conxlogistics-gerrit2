package com.conx.logistics.kernel.pageflow.engine;

import java.util.Map;

import com.conx.logistics.kernel.bpm.services.IBPMTask;
import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.vaadin.data.Container;
import com.vaadin.ui.Component;

public class PageFlowPageImpl implements IPageFlowPage {
	private IBPMTask task;

	public PageFlowPageImpl(IBPMTask task) {
		this.task = task;
	}
	
	@Override
	public Component getPageComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataContainerMap(Map<String, Container> containerMap) {
	}

	@Override
	public Map<String, Container> getDataContainerMap() {
		return null;
	}

	@Override
	public String getTitle() {
		return task.getName();
	}

	@Override
	public String getTaskId() {
		return task.getId();
	}

}
