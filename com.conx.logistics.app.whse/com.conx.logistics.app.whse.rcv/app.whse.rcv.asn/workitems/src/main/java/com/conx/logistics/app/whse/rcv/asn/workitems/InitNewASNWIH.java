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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.domain.organization.Organization;

@Transactional
@Repository
public class InitNewASNWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory.getLogger(InitNewASNWIH.class);
	
	@Autowired
	private IASNDAOService asnDao;
	@Autowired
	private IOrganizationDAOService orgDao;


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
			Map<String, Object> asnVarMapOut = new HashMap<String, Object>();
			asnVarMapOut.put("asnOut",newasn);
			//results.put("asnVarMapOut",asnVarMapOut);
			manager.completeWorkItem(workItem.getId(), asnVarMapOut);
			//WIUtils.waitTillCompleted(workItem,1000L);
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
