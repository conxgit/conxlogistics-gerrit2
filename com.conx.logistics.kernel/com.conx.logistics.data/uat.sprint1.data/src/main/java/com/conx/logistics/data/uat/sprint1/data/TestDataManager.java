package com.conx.logistics.data.uat.sprint1.data;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.domain.organization.Organization;

public class TestDataManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	private EntityManagerFactory conxlogisticsEMF;

	private PlatformTransactionManager globalTransactionManager;
	
	private IOrganizationDAOService orgDaoService;
	
	public void setOrgDaoService(IOrganizationDAOService orgDaoService) {
		this.orgDaoService = orgDaoService;
	}

	public void setConxlogisticsEMF(EntityManagerFactory conxlogisticsEMF) {
		this.conxlogisticsEMF = conxlogisticsEMF;
	}

	public void setGlobalTransactionManager(
			PlatformTransactionManager globalTransactionManager) {
		this.globalTransactionManager = globalTransactionManager;
	}
	
	public void start() {
		EntityManager em = conxlogisticsEMF.createEntityManager();
		
		/**
		 * Org Data: TD ORG 1.0, 4.0, 6.0, 7.0
		 * 
		 * Prod Data: TD PRD 2.0, 3.0, 4.0
		 * 
		 * Ref IDs: TD RIDTYP 2.0, 3.0, 4.0
		 * 
		 */
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("uat.sprint1.data");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.globalTransactionManager.getTransaction(def);			
		try {
			
			/**
			 * Org Data: TD ORG 1.0, 4.0, 6.0, 7.0
			 */
			Organization record = new Organization();
			record.setName("Test Customer 1");
			record.setCode("TESCUS1");
			record = this.orgDaoService.provide(record);
			
			this.globalTransactionManager.commit(status);	
		} 
		catch (Exception e) 
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			this.globalTransactionManager.rollback(status);
		}
		

		
	}
	
	public void stop() {
		
	}
}
