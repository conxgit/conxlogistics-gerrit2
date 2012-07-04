package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;

public class AcceptASNOrgWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory.getLogger(AcceptASNOrgWIH.class);
	
	private EntityManagerFactory conxlogisticsEMF;
	private JndiTemplate jndiTemplate;
	private IASNDAOService asnDao;
	private IOrganizationDAOService orgDao;

	public void setAsnDao(IASNDAOService asnDao) {
		this.asnDao = asnDao;
	}
	
	public void setOrgDao(IOrganizationDAOService orgDao) {
		this.orgDao = orgDao;
	}

	public void setConxlogisticsEMF(EntityManagerFactory conxlogisticsEMF) {
		this.conxlogisticsEMF = conxlogisticsEMF;
	}

	public void setJndiTemplate(JndiTemplate jndiTemplate) {
		this.jndiTemplate = jndiTemplate;
	}
	
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		/*
		UserTransaction ut = null;
		try {
			Context ctx = jndiTemplate.getContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/

		try {
			Map<String, Object> params = workItem.getParameters();
			HashMap varsIn = (HashMap)workItem.getParameter("asnVarMapIn");

			//asn = this.asnDao.update(asn);
			
			//Map<String, Object> output = new HashMap<String, Object>();
			//output.put("asnVarMapOut",asnParamsIn);
			manager.completeWorkItem(workItem.getId(), null);
			//WIUtils.waitTillCompleted(workItem,1000L);
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("AcceptASNOrgWIH:\r\n"+stacktrace, e);
		}	
		catch (Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("AcceptASNOrgWIH:\r\n"+stacktrace, e);			
		}			
	}


	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
