package com.conx.logistics.kernel.pageflow.tests;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.transaction.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

@Transactional
public class PageflowEngineTests {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IPageFlowManager defaultPageFlowEngine;
	
	@Autowired
	private TransactionManager globalTransactionManager;
	
	public void start() {
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("processId", "whse.rcv.asn.CreateNewASNByOrgV1.0");
			params.put("userId", "john");

			//-- 1. Start Proc
			ITaskWizard wiz = defaultPageFlowEngine.createTaskWizard(params);			
			Map<String, Object> res = wiz.getProperties();
			ASN asn = (ASN)res.get("Content");
			
			//-- 2. Complete ConfirmASNOrg Task and get vars
			HashMap<String,Object> outParams = new HashMap<String, Object>();
			outParams.putAll(res);
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, null);
			res = wiz.getProperties();
			res = (HashMap<String,Object>)res.get("Content");

			
			
			//-- 3. Complete AddRefNums Task and get vars
			outParams = new HashMap<String, Object>();
			//outParams.putAll(res);			
			HashSet<ReferenceNumber> refNumbers = new HashSet<ReferenceNumber>();
			ReferenceNumber rn = new ReferenceNumber();
			rn.setCode("123456");
			refNumbers.add(rn);
			outParams.put("asnRefNumMapOut", refNumbers);	
			//HashMap<String, Object> result = new HashMap<String, Object>();
			//result.put("Content", outParams);
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, outParams);
			res = wiz.getProperties();
			res = (HashMap<String,Object>)res.get("Content");
			
			//-- 4. Complete ASN Lines Human and get vars
			outParams = new HashMap<String, Object>();
			outParams.putAll(res);			
			HashSet<ASNLine> asnLines = new HashSet<ASNLine>();
			ASNLine line = new ASNLine();
			line.setLineNumber(1);
			asnLines.add(line);
			outParams.put("asnLinesCollectionOut", asnLines);			
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, outParams);
			res = wiz.getProperties();
			res = (HashMap<String,Object>)res.get("Content");
			
			//-- 4. Complete Local Trans Human and get vars
			outParams = new HashMap<String, Object>();
			outParams.putAll(res);				
			ASNPickup asnp = new ASNPickup();
			ASNDropOff asnd = new ASNDropOff();
			outParams.put("asnPickupOut", asnp);	
			outParams.put("asnDropoffOut", asnd);
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, outParams);
			res = wiz.getProperties();
			res = (HashMap<String,Object>)res.get("Content");
			
			//-- 5. Complete Accept ASN and get vars
			outParams = new HashMap<String, Object>();
			outParams.putAll(res);
			wiz = defaultPageFlowEngine.executeTaskWizard(wiz, outParams);
			res = wiz.getProperties();
			res = (HashMap<String,Object>)res.get("Content");
			
			// ProcessInstanceRef pi =
			//bpmService.newInstance("whse.rcv.asn.CreateNewASNByOrgV1.0");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			e.printStackTrace();
		}

	}

	public void stop() {
	}
}
