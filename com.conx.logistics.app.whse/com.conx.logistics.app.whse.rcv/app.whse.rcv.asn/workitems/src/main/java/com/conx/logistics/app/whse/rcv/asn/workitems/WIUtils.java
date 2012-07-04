package com.conx.logistics.app.whse.rcv.asn.workitems;

import org.drools.runtime.process.WorkItem;

public class WIUtils {
	static public void waitTillCompleted(WorkItem workItem, long time) throws Exception {
		int count = 0;
		while (count < 10) {
			if (workItem.getState() != org.drools.process.instance.WorkItem.COMPLETED) {
				if (workItem.getState() != org.drools.process.instance.WorkItem.ABORTED)
					throw new Exception("waitTillCompleted"+workItem.toString()+" is already aborted");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			count++;
		}
		if (count == 10) {
			throw new Exception("waitTillCompleted"+workItem.toString()+" Timed out");
		}		
	}

}
