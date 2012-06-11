package com.conx.logistics.kernel.bpm.services;

import java.util.List;
import java.util.Map;

public interface IBPMProcessInstance {
	public boolean isExecuting();
	public String getUserId();
	public String getProcessInstanceId();
	public IBPMTask nextTask(Map<String, Object> params);
	public IBPMTask getCurrentTask();
	public IBPMTask previousTask();
	public void abort();
	public String getProcessId();
	public List<IBPMTask> getTasks();
}
