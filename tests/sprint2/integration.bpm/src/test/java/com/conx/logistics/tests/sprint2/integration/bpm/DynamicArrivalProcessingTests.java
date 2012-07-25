package com.conx.logistics.tests.sprint2.integration.bpm;


import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.drools.definition.process.Node;
import org.drools.process.instance.WorkItemHandler;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.task.I18NText;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.core.node.HumanTaskNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.vaadin.teemu.wizards.WizardStep;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.kernel.bpm.impl.jbpm.BPMServerImpl;
import com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.HumanTaskServer;
import com.conx.logistics.kernel.pageflow.engine.PageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.PathBasedPageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.engine.ui.TaskWizard;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;

@ContextConfiguration(locations = { "/META-INF/tm.jta-module-context.xml",
        "/META-INF/persistence.datasource-module-context.xml",
        "/META-INF/jbpm.persistence.datasource-module-context.xml",
        "/META-INF/persistence.dynaconfiguration-module-context.xml",
        "/META-INF/mdm.dao.services.impl-module-context.xml",
        "/META-INF/app.whse.dao.jpa.persistence-module-context.xml",
        "/META-INF/app.whse.rcv.asn.dao.jpa.persistence-module-context.xml",
        "/META-INF/data.uat.sprint2.data-module-context.xml",
        "/META-INF/jbpm.taskserver.emf-module-context.xml",
        "/META-INF/jbpm.taskserver-module-context.xml",
        "/META-INF/jbpm.bpmserver.emf-module-context.xml",
        "/META-INF/jbpm.bpmserver-module-context.xml",
        "/META-INF/kernel.pageflow.engine-module-context.xml",
        "/META-INF/app.whse.rcv.rcv.pageflow-module-context.xml",
        "/META-INF/app.whse.rcv.rcv.workitems-module-context.xml"
        })
public class DynamicArrivalProcessingTests extends AbstractTestNGSpringContextTests {
	private static final String PROCESS_ID = "whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0";
	
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private HumanTaskServer humanTaskServer;
	
	@Autowired
	private BPMServerImpl bpmServer;	
	
	@Autowired
	private PathBasedPageFlowEngineImpl pageFlowImpl;
	
	private UserTransaction userTransactionManager;


	private ProcessInstanceRef procInstance;


	private List<HumanTaskNode> tasks;


	private Task currentTask;


	private Map<String, List<Node>> paths;

	@BeforeClass
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(humanTaskServer);
        Assert.assertNotNull(bpmServer);
        Assert.assertNotNull(pageFlowImpl);	
        Assert.assertNotNull(pageFlowImpl.getBpmService());
        
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
        
        /**
         * Register Proc WIH's
         */
        WorkItemHandler wih = (WorkItemHandler) applicationContext.getBean("attachNewArrivalWIH");
        Assert.assertNotNull(wih);
        Map props = new HashMap();
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","AttachNewArrivalWIH");
        bpmServer.registerWIH(wih, props);
        
        props = new HashMap();
        wih = (WorkItemHandler) applicationContext.getBean("attachNewDynamicArrivalWIH");
        Assert.assertNotNull(wih);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","AttachNewDynamicArrivalWIH");
        bpmServer.registerWIH(wih, props);      
        
        /**
         * Register Page's
         */   
        props = new HashMap();
        PageFlowPage page = (PageFlowPage) applicationContext.getBean("findReceivePage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","FindReceive");        
        pageFlowImpl.registerPageFlowPage(page, props);    
        
        page = (PageFlowPage) applicationContext.getBean("confirmReceivePage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","ConfirmReceive");        
        pageFlowImpl.registerPageFlowPage(page, props);  
        
        page = (PageFlowPage) applicationContext.getBean("processArrivalReceipts");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","ProcessArrivalReceipts");        
        pageFlowImpl.registerPageFlowPage(page, props);         
          
        page = (PageFlowPage) applicationContext.getBean("createDynamicReceiveFromBOLPage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","CreateDynamicReceiveFromBOL");        
        pageFlowImpl.registerPageFlowPage(page, props);  
        
        page = (PageFlowPage) applicationContext.getBean("processDynamicArrivalReceiptsPage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","ProcessDynamicArrivalReceipts");        
        pageFlowImpl.registerPageFlowPage(page, props);   
        
        page = (PageFlowPage) applicationContext.getBean("confirmArrivalPage");
        Assert.assertNotNull(page);
        props.put("PROCESS_ID","whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
        props.put("TASK_NAME","ConfirmArrival");        
        pageFlowImpl.registerPageFlowPage(page, props);         
	}
	
    @Test
    public void testDynamicArrival() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", "whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
		params.put("userId", "john");
		
		//-- 1. Start Proc
		ITaskWizard wiz = pageFlowImpl.createTaskWizard(params);	
		List<WizardStep> steps = ((TaskWizard)wiz).getSteps();
		Assert.assertEquals(steps.size(), 1);
		Map<String, Object> res = wiz.getProperties();	
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		org.h2.tools.Script.execute("jdbc:h2:mem:conxjbpm5;MODE=MySQL","sa", "sasa",out);
		String dbOut = null;
		try {
		    dbOut = new String(out.toByteArray(), "UTF-8");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		
		//-- 2. Complete ConfirmASNOrg Task and get vars
		logger.info("Current Step: "+((TaskWizard)wiz).getCurrentStep());
		HashMap<String,Object> outParams = new HashMap<String, Object>();
		outParams.putAll(res);
		wiz = pageFlowImpl.executeTaskWizard(wiz, null);
		
		//Assert step/page count
		steps = ((TaskWizard)wiz).getSteps();
		Assert.assertEquals(steps.size(), 4);
		
		//Assert step names
		List<String> stepNames = Collections.emptyList();
		stepNames.add("FindReceive");
		stepNames.add("CreateDynamicReceiveFromBOL");
		stepNames.add("ProcessDynamicArrivalReceipts");
		stepNames.add("ConfirmArrival");
		for (PageFlowPage pf : ((TaskWizard)wiz).getSession().getPages())
		{
			Assert.assertEquals(stepNames.contains(pf.getTaskName()), "true");
		}
		
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");	
    }	
	
    @Test
    public void testArrival() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", "whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
		params.put("userId", "john");
		
		//-- 1. Start Proc
		ITaskWizard wiz = pageFlowImpl.createTaskWizard(params);	
		List<WizardStep> steps = ((TaskWizard)wiz).getSteps();
		Assert.assertEquals(steps.size(), 1);
		Map<String, Object> res = wiz.getProperties();	
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		org.h2.tools.Script.execute("jdbc:h2:mem:conxjbpm5;MODE=MySQL","sa", "sasa",out);
		String dbOut = null;
		try {
		    dbOut = new String(out.toByteArray(), "UTF-8");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		
		//-- 2. Complete ConfirmASNOrg Task and get vars
		logger.info("Current Step: "+((TaskWizard)wiz).getCurrentStep());
		HashMap<String,Object> outParams = new HashMap<String, Object>();
		outParams.putAll(res);
		wiz = pageFlowImpl.executeTaskWizard(wiz, null);
		
		steps = ((TaskWizard)wiz).getSteps();
		Assert.assertEquals(steps.size(), 4);
		
		res = wiz.getProperties();
		res = (HashMap<String,Object>)res.get("Content");	
    }
    	
	
	/*
	@Test
	public void testProcessInstanceHumanTasks() throws Exception
	{
		UserTransaction ut = this.userTransactionManager;

		// 1. Create process instance		
		try
		{
			ut.begin();

			procInstance = bpmServer.newInstance(PROCESS_ID);

			ut.commit();
		}
		catch(Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);				 
			ut.rollback();
			throw e;
		}	
		
		Assert.assertNotNull(procInstance);
		
		//Get current task
		currentTask = waitForNextTask();
		
		//Get task name
		HashMap<String,Object> res = (HashMap<String,Object>)this.bpmServer.getTaskContentObject(currentTask);
		String taskName = (String)res.get("TaskName");
		
		this.paths = this.bpmServer.findAllNodePaths(procInstance.getDefinitionId());
		//List<NodeInstanceLog> nodes = this.bpmServer.getAllNodeInstances(procInstance.getId());
		//Assert.assertEquals(nodes.size(),1);

		//Is this task a gateway driver?
		boolean currentTaskIsChoiceSelector = this.bpmServer.humanTaskNodeIsGatewayDriver(taskName, procInstance.getDefinitionId());
		if (currentTaskIsChoiceSelector)
		{
			HumanTaskNode htTaskNode = this.bpmServer.findHumanTaskNodeForTask(taskName, procInstance.getDefinitionId());
			this.tasks = new ArrayList<HumanTaskNode>();
			this.tasks.add(htTaskNode);
		}
		else
		{
			this.tasks = this.bpmServer.findAllHumanTaskNodesAfterTask(taskName, procInstance.getDefinitionId());
		}
		
		//Check HT count
		Assert.assertEquals(this.tasks.size(),1);
	}
	*/
	

/*	@Test
	public void testHumanTaskCount() throws Exception {
		this.tasks = this.bpmServer.getProcessHumanTaskNodes(processInstance
				.getDefinitionId());		
	}*/
	

/*    @Test(dependsOnMethods={"testExecuteTaskWizard"})
    public void testTaskWizardState() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", "whse.rcv.arrivalproc.ProcessCarrierArrivalV1.0");
		params.put("userId", "john");
    }

	
	private Task waitForNextTask() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		int count = 0;
		Thread.sleep(1000L);
		while (count < 10) {
			tasks = bpmServer.getCreatedTasksByProcessId(Long
					.parseLong(procInstance.getId()));
			if (tasks.size() == 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			count++;
		}
		if (count == 10) {
			throw new Exception("waitForNextTask Timed out");
		}
		return tasks.get(0);
	}	*/
}
