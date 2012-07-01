package com.conx.logistics.kernel.pageflow.services;


import java.util.Map;

import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.conx.logistics.mdm.domain.task.TaskDefinition;

public interface IPageFlowManager {
	/**
	 * Services:
	 */
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public IPageFlowSession startPageFlowSession(String userid, TaskDefinition td);
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public ITaskWizard createTaskWizard(Map<String, Object> properties) throws Exception;
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public ITaskWizard executeTaskWizard(ITaskWizard tw, Object data) throws Exception;	
	/**
	 * Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession closePageFlowSession(String userid, Long pageFlowSessionId);	
	/**
	 * Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession resumePageFlowSession(String userid, Long pageFlowSessionId);
	
	/**
	 * Main App
	 */
	public void setMainApplication(IMainApplication mainApp);
}
