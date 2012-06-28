package com.conx.logistics.app.whse.rcv.asn.workitems;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.jndi.JndiTemplate;

import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;

public class AcceptASNLocalTransWIH implements WorkItemHandler {
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
		ASN newasn = new ASN();
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("newasn",newasn);
		manager.completeWorkItem(workItem.getId(), output);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
