package com.conx.logistics.kernel.bpm.impl.jbpm.core.mock;

import java.util.List;
import java.util.Map;

import org.drools.runtime.process.ProcessInstance;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMTask;

public class BPMProcessInstanceImpl implements IBPMProcessInstance {
	private ProcessInstance instance;
	private String userId;
	
	public BPMProcessInstanceImpl(ProcessInstance instance, String userId) {
		this.instance = instance;
		this.userId = userId;
	}

	@Override
	public boolean isExecuting() {
		int state = instance.getState();
		return state == ProcessInstance.STATE_ACTIVE || state == ProcessInstance.STATE_PENDING;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public IBPMTask nextTask(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBPMTask getCurrentTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBPMTask previousTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProcessId() {
		return instance.getProcessId();
	}

	@Override
	public String getProcessInstanceId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBPMTask> getTasks() {
		// TODO Auto-generated method stub
		return null;
	}
}
