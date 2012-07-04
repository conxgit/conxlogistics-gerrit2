package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

public class AcceptASNWIH implements WorkItemHandler {
	private static final Logger logger = LoggerFactory.getLogger(AcceptASNWIH.class);
	
	private EntityManagerFactory conxlogisticsEMF;
	private JndiTemplate jndiTemplate;
	private IASNDAOService asnDao;
	private UserTransaction userTransaction;
	private IReferenceNumberDAOService referenceNumberDao;

	public void setAsnDao(IASNDAOService asnDao) {
		this.asnDao = asnDao;
	}
	
	public IASNDAOService getAsnDao() {
		return asnDao;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
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
/*			ASN asnParamsIn = (ASN)workItem.getParameter("asnParamsIn");
			Set<ReferenceNumber> refNumsCollectionIn = (Set<ReferenceNumber>)workItem.getParameter("refNumsCollectionIn");
			Set<ASNLine> asnLinesCollectionIn = (Set<ASNLine>)workItem.getParameter("asnLinesCollectionIn");
			ASNPickup asnPickupIn = (ASNPickup)workItem.getParameter("asnPickupIn");
			ASNDropOff asnDropoffIn = (ASNDropOff)workItem.getParameter("asnDropoffIn");
			Set<Product> productsCollectionIn = (Set<Product>)workItem.getParameter("productsCollectionIn");*/
			
			
			HashMap varsIn = (HashMap)workItem.getParameter("asnVarMapIn");	
			
			ASN asnParamsIn = (ASN)varsIn.get("asn");
			Set<ReferenceNumber> refNumsCollectionIn = (Set<ReferenceNumber>)varsIn.get("refNumsCollection");
			Set<ASNLine> asnLinesCollectionIn = (Set<ASNLine>)varsIn.get("asnLinesCollection");
			ASNPickup asnPickupIn = (ASNPickup)varsIn.get("asnPickup");
			ASNDropOff asnDropoffIn = (ASNDropOff)varsIn.get("asnDropoff");
			Set<Product> productsCollectionIn = (Set<Product>)varsIn.get("productsCollection");

			/**
			 * 
			 * Save ASN
			 * 
			 */
			Map<String, Object> varsOut = new HashMap<String, Object>();
			
			ASN asn = asnDao.add(asnParamsIn);
			varsOut.put("asn", asn);
			Map<String, Object> output = new HashMap<String, Object>();
			output.put("asnVarMapOut",varsOut);
			
			if (Validator.isNotNull(refNumsCollectionIn))
			{
				asn = asnDao.addRefNums(asn.getId(), refNumsCollectionIn);
				Set<ReferenceNumber> refNumsCollectionOut = asn.getRefNumbers();
				varsOut.put("refNumsCollectionOut",refNumsCollectionOut);
			}
			
			if (Validator.isNotNull(asnLinesCollectionIn))
			{
				asn = asnDao.addLines(asn.getId(), asnLinesCollectionIn);
				Set<ASNLine> asnLinesCollectionOut = asn.getAsnLines();
				varsOut.put("asnLinesCollectionOut",asnLinesCollectionOut);
			}

			if (Validator.isNotNull(asnPickupIn) && Validator.isNotNull(asnDropoffIn))
			{
				asn = asnDao.addLocalTrans(asn.getId(), asnPickupIn,asnDropoffIn);
				ASNPickup asnPickupOut = asn.getPickup();
				ASNDropOff asnDropoffOut = asn.getDropOff();
				varsOut.put("asnPickupOut",asnPickupOut);
				varsOut.put("asnDropoffOut",asnDropoffOut);
			}
			manager.completeWorkItem(workItem.getId(), output);
			//WIUtils.waitTillCompleted(workItem,1000L);
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
