package com.conx.logistics.kernel.pageflow.services;

import com.conx.logistics.mdm.domain.task.TaskDefinition;

public interface IPageFlowManager {
	/**
	 * Services:
	 */
	/**
	 * 1. Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public IPageFlowSession startPageFlowSession(String userid, TaskDefinition td);
	/**
	 * 2. Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession closePageFlowSession(String userid, Long pageFlowSessionId);	
	/**
	 * 3. Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession resumePageFlowSession(String userid, Long pageFlowSessionId);
}
