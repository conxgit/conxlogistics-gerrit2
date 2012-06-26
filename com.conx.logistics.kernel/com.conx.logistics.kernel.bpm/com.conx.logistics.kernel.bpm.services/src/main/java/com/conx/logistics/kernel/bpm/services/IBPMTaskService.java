package com.conx.logistics.kernel.bpm.services;

import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;

public interface IBPMTaskService {
	public TaskService getTaskService();

	public LocalTaskService getLocalTaskService();
}
