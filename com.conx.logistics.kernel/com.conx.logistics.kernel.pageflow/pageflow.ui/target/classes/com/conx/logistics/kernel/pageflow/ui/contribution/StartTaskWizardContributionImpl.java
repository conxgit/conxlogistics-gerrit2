package com.conx.logistics.kernel.pageflow.ui.contribution;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.ui.service.contribution.ITaskActionContribution;
import com.conx.logistics.mdm.domain.task.TaskDefinition;
import com.vaadin.Application;
import com.vaadin.ui.Component;

public class StartTaskWizardContributionImpl implements ITaskActionContribution {
	
	private IPageFlowManager pageFlowservice;

	public void setDefaultPageFlowEngine(IPageFlowManager pageFlowservice) {
		this.pageFlowservice = pageFlowservice;
	}

	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Application application) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component execute(Application application,
			Map<String, Object> properties) throws Exception {
		return (Component)pageFlowservice.createTaskWizard(properties);
	}

}
