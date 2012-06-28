package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.SystemException;
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
import com.conx.logistics.mdm.domain.organization.Organization;

public class InitNewASNWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory.getLogger(InitNewASNWIH.class);
	
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
		ASN newasn = null;
		/*
		UserTransaction ut = null;
		try {
			Context ctx = jndiTemplate.getContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ut.begin();
			*/
			
		try
		{
			String userId = (String)workItem.getParameter("userIdIn");
			Organization org = orgDao.getByCode("TESCUS1");
			
			newasn = new ASN();
			newasn.setCode("TESCUS1"+(new Date().toString()));
			//newasn = asnDao.add(newasn);
			newasn.setTenant(org);
			//newasn = asnDao.update(newasn);
			//Map<String, Object> asnParamsOut = new HashMap<String, Object>();
			//asnParamsOut.put("asn", newasn);
			
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("asnParamsOut",newasn);
			manager.completeWorkItem(workItem.getId(), results);
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("InitNewASNWIH:\r\n"+stacktrace, e);
		}	
		catch (Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalStateException("InitNewASNWIH:\r\n"+stacktrace, e);			
		}			
/*
			ut.commit();
		} catch (Exception e) {
			try {
				ut.rollback();
			} catch (Exception e1) {
				throw new RuntimeException(e);
			}
			throw new RuntimeException(e);
		}
		*/
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

}
