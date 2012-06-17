package com.conx.logistics.kernel.bpm.services;

import org.jbpm.task.service.TaskService;

public interface IBPMTaskService {
	public TaskService createHumanTaskService();
}
