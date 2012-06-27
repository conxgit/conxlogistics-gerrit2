package com.conx.logistics.kernel.pageflow.tests;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;

@Transactional
public class PageflowEngineTests {
	
	@Autowired
	private IPageFlowManager defaultPageFlowEngine;
	
	@Autowired
	private TransactionManager globalTransactionManager;
	
	public void start() {
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("processId", "whse.rcv.asn.CreateNewASNByOrgV1.0");
			params.put("userId", "john");
			ITaskWizard wiz = defaultPageFlowEngine.createTaskWizard(params);
			
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, null);
			// ProcessInstanceRef pi =
			//bpmService.newInstance("whse.rcv.asn.CreateNewASNByOrgV1.0");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stop() {
	}
}
