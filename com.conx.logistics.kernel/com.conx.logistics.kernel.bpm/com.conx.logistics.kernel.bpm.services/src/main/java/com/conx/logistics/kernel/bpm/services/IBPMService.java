package com.conx.logistics.kernel.bpm.services;

import java.util.Map;

public interface IBPMService {
	public IBPMProcessInstance startNewProcess(String userId, String processId);
	public IBPMProcessInstance getProcessInstance(String processId);
	public Map<String, IBPMTask> getProcessTasks(String processId);
	
}
