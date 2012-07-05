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
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;

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
			ASN asnIn = (ASN)workItem.getParameter("asnIn");
			
			Map<String, Object> asnRefNumMapIn = (Map<String, Object>)workItem.getParameter("asnRefNumMapIn");
			Set<ReferenceNumber> refNumsCollectionIn = (Set<ReferenceNumber>)asnRefNumMapIn.get("asnRefNumCollection");
			Set<ReferenceNumberType> refNumTypesCollectionIn = (Set<ReferenceNumberType>)asnRefNumMapIn.get("asnRefNumTypeCollection");
			
			Map<String, Object> asnASNLineProductMapIn = (Map<String, Object>)workItem.getParameter("asnASNLineProductMapIn");			
			Set<ASNLine> asnLinesCollectionIn = (Set<ASNLine>)asnASNLineProductMapIn.get("asnLinesCollection");
			Set<Product> productsCollection = (Set<Product>)asnASNLineProductMapIn.get("productsCollection");
			
			Map<String, Object> asnLocalTransMapIn = (Map<String, Object>)workItem.getParameter("asnLocalTransMapIn");			
			ASNPickup asnPickupIn = (ASNPickup)asnLocalTransMapIn.get("asnPickup");
			ASNDropOff asnDropoffIn = (ASNDropOff)asnLocalTransMapIn.get("asnDropoff");

			/**
			 * 
			 * Save ASN
			 * 
			 */
			Map<String, Object> varsOut = new HashMap<String, Object>();
			
			ASN asn = asnDao.add(asnIn);
			varsOut.put("asn", asn);
			Map<String, Object> output = new HashMap<String, Object>();
			output.put("asnOut",asn);
			
			if (Validator.isNotNull(asnRefNumMapIn) && asnRefNumMapIn.size() > 0)
			{
				Map<String, Object> asnRefNumMapOut = new HashMap<String, Object>();
				asn = asnDao.addRefNums(asn.getId(), refNumsCollectionIn);
				Set<ReferenceNumber> refNumsCollectionOut = asn.getRefNumbers();
				asnRefNumMapOut.put("asnRefNumCollection", refNumsCollectionOut);
				varsOut.put("refNumsCollectionOut",asnRefNumMapOut);
			}
			
			if (Validator.isNotNull(asnASNLineProductMapIn) && asnASNLineProductMapIn.size() > 0)
			{
				Map<String, Object> asnASNLineProductMapOut = new HashMap<String, Object>();
				asn = asnDao.addLines(asn.getId(), asnLinesCollectionIn);
				Set<ASNLine> asnLinesCollectionOut = asn.getAsnLines();
				asnASNLineProductMapOut.put("asnLinesCollection", asnLinesCollectionOut);
				
				varsOut.put("asnASNLineProductMapOut",asnASNLineProductMapOut);
			}

			if (Validator.isNotNull(asnLocalTransMapIn) && asnLocalTransMapIn.size() > 0)
			{
				Map<String, Object> asnLocalTransMapOut = new HashMap<String, Object>();
				asn = asnDao.addLocalTrans(asn.getId(), asnPickupIn,asnDropoffIn);
				ASNPickup asnPickupOut = asn.getPickup();
				ASNDropOff asnDropoffOut = asn.getDropOff();
				asnLocalTransMapOut.put("asnPickup",asnPickupOut);
				asnLocalTransMapOut.put("asnDropoff",asnDropoffOut);
				
				varsOut.put("asnLocalTransMapOut",asnLocalTransMapOut);
			}
			manager.completeWorkItem(workItem.getId(), varsOut);
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
