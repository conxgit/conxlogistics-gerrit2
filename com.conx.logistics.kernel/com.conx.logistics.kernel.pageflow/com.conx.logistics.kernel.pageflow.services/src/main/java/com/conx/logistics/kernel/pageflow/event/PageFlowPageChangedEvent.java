package com.conx.logistics.kernel.pageflow.event;

import java.util.Map;

public class PageFlowPageChangedEvent {
	private Map<String,Object> changedVars;
	
	public PageFlowPageChangedEvent(){
	}
	
	public PageFlowPageChangedEvent(Map<String,Object> changedVars) {
		this.changedVars = changedVars;
	}
	
	public Map<String, Object> getChangedVars() {
		return changedVars;
	}
	
	public void setChangedVars(Map<String, Object> changedVars) {
		this.changedVars = changedVars;
	}
}
