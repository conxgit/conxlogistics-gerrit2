package com.conx.logistics.tests.sprint1.integration.bpm;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.drools.process.instance.WorkItemHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.vaadin.teemu.wizards.WizardStep;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.kernel.bpm.impl.jbpm.BPMServerImpl;
import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.HumanTaskServer;
import com.conx.logistics.kernel.pageflow.engine.PageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.PathBasedPageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberTypeDAOService;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;


//@Test(groups = { "integration" })
@ContextConfiguration(locations = { "/META-INF/tm.jta-module-context.xml",
		                            "/META-INF/persistence.datasource-module-context.xml",
		                            "/META-INF/jbpm.persistence.datasource-module-context.xml",
		                            "/META-INF/persistence.dynaconfiguration-module-context.xml",
		                            "/META-INF/mdm.dao.services.impl-module-context.xml",
		                            "/META-INF/app.whse.dao.jpa.persistence-module-context.xml",
		                            "/META-INF/app.whse.rcv.asn.dao.jpa.persistence-module-context.xml",
		                            "/META-INF/data.uat.sprint1.data-module-context.xml",
		                            "/META-INF/jbpm.taskserver.emf-module-context.xml",
		                            "/META-INF/jbpm.taskserver-module-context.xml",
		                            "/META-INF/jbpm.bpmserver.emf-module-context.xml",
		                            "/META-INF/jbpm.bpmserver-module-context.xml",
		                            "/META-INF/kernel.pageflow.engine-module-context.xml",
		                            "/META-INF/app.whse.rcv.asn.pageflow-module-context.xml",
		                            "/META-INF/app.whse.rcv.asn.workitems-module-context.xml"
		                            })
public class PageFlowSessionTests extends AbstractTestNGSpringContextTests {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private HumanTaskServer humanTaskServer;
	
	@Autowired
	private BPMServerImpl bpmServer;	
	
	@Autowired
	private PathBasedPageFlowEngineImpl pageFlowImpl;
	
	@Autowired
	private IOrganizationDAOService orgDao;
	
	@Autowired
	private IProductDAOService productDao;
	
	@Autowired
	private IReferenceNumberTypeDAOService refTypeDao;	

    @BeforeClass
    protected void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(humanTaskServer);
        Assert.assertNotNull(bpmServer);
        Assert.assertNotNull(pageFlowImpl);
        
        
        /**
         * 
         * Ensure test data
         */
        Organization org = orgDao.getByCode("TESCUS1");
        Assert.assertNotNull(org);
        
        /**
         * Register Proc WIH's
         */
        WorkItemHandler wih = (WorkItemHandler) applicationContext.getBean("newASNWIH");
        Assert.assertNotNull(wih);
        Map props = new HashMap();
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","CreateNewASNWIH");
        bpmServer.registerWIH(wih, props);
        
        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("acceptASNOrgWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AcceptASNOrgWIH");
        bpmServer.registerWIH(wih, props);        
        
        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("acceptASNRefNumsWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AcceptASNRefNumsWIH");
        bpmServer.registerWIH(wih, props);   
        
        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("acceptASNLinesWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AcceptASNLinesWIH");
        bpmServer.registerWIH(wih, props);         

        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("acceptASNLocalTransWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AcceptASNLocalTransWIH");
        bpmServer.registerWIH(wih, props);     
        
        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("acceptASNWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AcceptASNWIH");
        bpmServer.registerWIH(wih, props);    
        
        /**
         * Register Page's
         */   
        props = new HashMap();
        PageFlowPage page = (PageFlowPage) applicationContext.getBean("confirmAsnOrgPage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","ConfirmASNOrg");        
        pageFlowImpl.registerPageFlowPage(page, props);
        
        props = new HashMap();
        page = (PageFlowPage) applicationContext.getBean("addAsnRefNumsPage");
        Assert.assertNotNull(page);        
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AddASNRefNums");        
        pageFlowImpl.registerPageFlowPage(page, props);
        
        props = new HashMap();
        page = (PageFlowPage) applicationContext.getBean("addAsnLinesPage");
        Assert.assertNotNull(page);           
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AddASNLines");        
        pageFlowImpl.registerPageFlowPage(page, props);
        
        props = new HashMap();
        page = (PageFlowPage) applicationContext.getBean("addAsnLocalTransPage");
        Assert.assertNotNull(page);           
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","AddASNLocalTrans");        
        pageFlowImpl.registerPageFlowPage(page, props);
        
        props = new HashMap();
        page = (PageFlowPage) applicationContext.getBean("confirmAsnPage");
        Assert.assertNotNull(page);           
        props.put("PROCESS_ID","whse.rcv.asn.CreateNewASNByOrgV1.0");
        props.put("TASK_NAME","ConfirmASN");        
        pageFlowImpl.registerPageFlowPage(page, props);        
    }

    @Test
    public void testExecuteTaskWizard() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", "whse.rcv.asn.CreateNewASNByOrgV1.0");
		params.put("userId", "john");

		//-- 1. Start Proc
		ITaskWizard wiz = pageFlowImpl.createTaskWizard(params);	
		Iterator<WizardStep> stepIt = ((TaskWizard)wiz).getSteps().iterator();
		Map<String, Object> res = wiz.getProperties();
		ASN asn = (ASN)res.get("Content");
		
		//-- 2. Complete ConfirmASNOrg Task and get vars
		WizardStep cs = stepIt.next();
		logger.info("Current Step: "+cs.getCaption());
		HashMap<String,Object> outParams = new HashMap<String, Object>();
		outParams.putAll(res);
		wiz = pageFlowImpl.executeTaskWizard(wiz, null);
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");

		
		
		//-- 3. Complete AddRefNums Task and get vars
		cs = stepIt.next();
		logger.info("Current Step: "+cs.getCaption());		
		HashMap<String, Object> asnRefNumMap = new HashMap<String, Object>();
		
		outParams = new HashMap<String, Object>();
		//outParams.putAll(res);			
		HashSet<ReferenceNumber> refNumbers = new HashSet<ReferenceNumber>();
		ReferenceNumber rn = new ReferenceNumber();
		rn.setCode("123456");
		rn.setName("123456");
		rn.setValue("123456");
		ReferenceNumberType rnt = refTypeDao.getByCode("ConXGeneric");
		rn.setType(rnt);
		refNumbers.add(rn);
		asnRefNumMap.put("asnRefNumCollection", refNumbers);	
		asnRefNumMap.put("asnRefNumTypeCollection", new HashSet<ReferenceNumberType>());	
		outParams.put("asnRefNumMapOut", asnRefNumMap);
		wiz = pageFlowImpl.executeTaskWizard(wiz, outParams);
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");
		
		/***
		 * Change VARS
		 */
		rn.setCode("ABCD");
		HashMap<String, Object> procVarMap = new HashMap<String, Object>();
		procVarMap.put("asnRefNumMap", asnRefNumMap);
		Map<String, Object> procInstVars = pageFlowImpl.updateProcessInstanceVariables(wiz, procVarMap);
		procVarMap = (HashMap<String, Object>)procInstVars.get("asnRefNumMap");
		HashSet<ReferenceNumber> refSet = (HashSet<ReferenceNumber>)procVarMap.get("asnRefNumCollection");
		rn = refSet.iterator().next();
		
		//-- 4. Complete ASN Lines Human and get vars
		cs = stepIt.next();
		logger.info("Current Step: "+cs.getCaption());		
		HashMap<String, Object> asnASNLineProductMap = new HashMap<String, Object>();
		outParams = new HashMap<String, Object>();
		outParams.putAll(res);			
		HashSet<ASNLine> asnLines = new HashSet<ASNLine>();
		ASNLine line = new ASNLine();
		Product prod = productDao.getByCode("fooite1");
		line.setProduct(prod);
		line.setLineNumber(1);
		asnLines.add(line);
		asnASNLineProductMap.put("asnLinesCollection", asnLines);	
		asnASNLineProductMap.put("productsCollection", new HashSet<Product>());
		
		outParams.put("asnASNLineProductMapOut", asnASNLineProductMap);
		wiz = pageFlowImpl.executeTaskWizard(wiz, outParams);
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");
		
		//-- 4. Complete Local Trans Human and get vars
		cs = stepIt.next();
		logger.info("Current Step: "+cs.getCaption());
		HashMap<String, Object> asnLocalTransMap = new HashMap<String, Object>();
		
		outParams = new HashMap<String, Object>();				
		ASNPickup asnp = new ASNPickup();
		ASNDropOff asnd = new ASNDropOff();
		asnLocalTransMap.put("asnPickup", asnp);	
		asnLocalTransMap.put("asnDropoff", asnd);
		
		outParams.put("asnLocalTransMapOut", asnLocalTransMap);
		
		wiz = pageFlowImpl.executeTaskWizard(wiz, outParams);
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");
		logger.info("Confirm ASN Vars: "+res);
		
		//-- 5. Confirm ASN and get vars
		cs = stepIt.next();
		logger.info("Current Step: "+cs.getCaption());		
		((TaskWizard)wiz).setCurrentStep(cs);
		outParams = new HashMap<String, Object>();
		wiz = pageFlowImpl.executeTaskWizard(wiz, null);
		//res = wiz.getProperties();
		//res = (HashMap<String,Object>)res.get("Content");		
    }

}
