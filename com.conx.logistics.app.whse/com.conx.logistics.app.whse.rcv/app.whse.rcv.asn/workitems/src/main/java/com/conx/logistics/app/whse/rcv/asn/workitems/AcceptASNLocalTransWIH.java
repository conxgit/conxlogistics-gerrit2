package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.util.HashMap;
import java.util.Map;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;

public class AcceptASNLocalTransWIH implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		ASN newasn = new ASN();
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("newasn",newasn);
		manager.completeWorkItem(workItem.getId(), output);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
