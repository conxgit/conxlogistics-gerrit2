package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.util.HashMap;
import java.util.Map;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;

public class InitNewASNWIH implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		ASN newasn = new ASN();
		Map<String, Object> results = new HashMap<String, Object>();
		//workItem.getResults().put("asnOut", newasn);
		results.put("asnIdOut","201");
		manager.completeWorkItem(workItem.getId(), results);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
