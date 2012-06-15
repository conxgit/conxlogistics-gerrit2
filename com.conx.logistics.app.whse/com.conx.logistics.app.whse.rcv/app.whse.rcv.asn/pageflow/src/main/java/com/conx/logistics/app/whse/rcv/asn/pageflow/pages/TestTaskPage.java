package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class TestTaskPage extends IPageFlowPage {

	@Override
	public String getTaskName() {
		return "_169593DB-444E-493C-8B7F-AF32BB82B64A";
	}

	@Override
	public Component getContent() {
		return new Label(
                "<h2>Wizards for Vaadin add-on</h2><p>This is a demo application of the "
                        + "Wizards for Vaadin add-on.</p><p>The goal of this add-on is to provide a simple framework for easily creating wizard style "
                        + "user interfaces. Please use the controls below this content area to navigate "
                        + "through this wizard that demonstrates the features and usage of this add-on.</p><h3>Additional information</h3>"
                        + "<ul><li><a href=\"https://vaadin.com/addon/wizards-for-vaadin\">Wizards for Vaadin </a> at Vaadin Directory</li>"
                        + "<li><a href=\"http://code.google.com/p/wizards-for-vaadin/\">Project page</a> at Google Project Hosting (including source code of this demo)</li>"
                        + "<li><a href=\"https://vaadin.com/teemu/\">Author homepage</a> at Vaadin</li></ul>",
                Label.CONTENT_XHTML);
	}

	@Override
	public void setDataContainerMap(Map<String, Container> containerMap) {
	}

	@Override
	public Map<String, Container> getDataContainerMap() {
		return null;
	}

	@Override
	public String getCaption() {
		return "Test Page";
	}

}
