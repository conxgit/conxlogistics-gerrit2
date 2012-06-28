package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;

public class AcceptASNWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory.getLogger(AcceptASNWIH.class);
	
	private EntityManagerFactory conxlogisticsEMF;
	private JndiTemplate jndiTemplate;
	private IASNDAOService asnDao;

	public void setAsnDao(IASNDAOService asnDao) {
		this.asnDao = asnDao;
	}
	
	public void setConxlogisticsEMF(EntityManagerFactory conxlogisticsEMF) {
		this.conxlogisticsEMF = conxlogisticsEMF;
	}

	public void setJndiTemplate(JndiTemplate jndiTemplate) {
		this.jndiTemplate = jndiTemplate;
	}
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		try {
			ASN asnParamsIn = (ASN)workItem.getParameter("asnParamsIn");

			//asn = this.asnDao.update(asn);
			/**
			 * 
			 * Save ASN
			 * 
			 */
			ASN asn = asnDao.add(asnParamsIn);
			
			Map<String, Object> output = new HashMap<String, Object>();
			output.put("asnParamsOut",asn);
			manager.completeWorkItem(workItem.getId(), output);
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("AcceptASNWIH:\r\n"+stacktrace, e);
		}	
		catch (Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("AcceptASNWIH:\r\n"+stacktrace, e);			
		}
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
