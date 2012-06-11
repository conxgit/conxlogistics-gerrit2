package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.vaadin.data.Container;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

public class TestTaskPage implements IPageFlowPage {

	@Override
	public Component getPageComponent() {
		// TODO Auto-generated method stub
		return new Button();
	}

	@Override
	public void setDataContainerMap(Map<String, Container> containerMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Container> getDataContainerMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		return "Test Task";
	}

	@Override
	public String getTaskId() {
		return "_169593DB-444E-493C-8B7F-AF32BB82B64A";
	}

}
