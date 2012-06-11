package com.conx.logistics.kernel.bpm.impl.jbpm.core.mock;

import org.jbpm.workflow.core.node.HumanTaskNode;

import com.conx.logistics.kernel.bpm.services.IBPMTask;

public class BPMTaskImpl implements IBPMTask {
	private HumanTaskNode node;
	private String processId;
	
	public BPMTaskImpl(HumanTaskNode node, String processId) {
		this.node = node;
		this.processId = processId;
	}
	
	public BPMTaskImpl() {
	}
	
	@Override
	public String getName() {
		return node.getName();
	}

	@Override
	public String getId() {
		return node.getUniqueId();
	}

	@Override
	public String getProcessId() {
		return processId;
	}
}
