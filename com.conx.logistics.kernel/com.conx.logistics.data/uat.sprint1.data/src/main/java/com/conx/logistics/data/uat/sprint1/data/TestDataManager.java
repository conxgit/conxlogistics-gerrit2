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

import com.conx.logistics.mdm.dao.services.IAddressDAOService;
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.dao.services.ICountryStateDAOService;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.dao.services.IUnlocoDAOService;
import com.conx.logistics.mdm.dao.services.product.IDimUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IPackUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductTypeDAOService;
import com.conx.logistics.mdm.dao.services.product.IWeightUnitDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberTypeDAOService;
import com.conx.logistics.mdm.domain.constants.DimUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.PackUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.ProductTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.WeightUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.conx.logistics.mdm.domain.product.Product;

public class TestDataManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	private EntityManagerFactory conxlogisticsEMF;

	private PlatformTransactionManager globalTransactionManager;
	
	private IOrganizationDAOService orgDaoService;
	private ICountryDAOService countryDaoService;
	private ICountryStateDAOService countryStateDaoService;
	private IUnlocoDAOService unlocoDaoService;
	private IAddressDAOService addressDaoService;
	
	private IPackUnitDAOService packUnitDaoService;
	private IDimUnitDAOService dimUnitDaoService;
	private IWeightUnitDAOService weightUnitDaoService;
	private IProductTypeDAOService productTypeDaoService;
	private IProductDAOService productDaoService;
	
	private IReferenceNumberTypeDAOService referenceNumberTypeDaoService;
	
	public void setOrgDaoService(IOrganizationDAOService orgDaoService) {
		this.orgDaoService = orgDaoService;
	}
	public void setCountryDaoService(ICountryDAOService countryDaoService) {
		this.countryDaoService = countryDaoService;
	}
	public void setCountryStateDaoService(
			ICountryStateDAOService countryStateDaoService) {
		this.countryStateDaoService = countryStateDaoService;
	}
	public void setUnlocoDaoService(IUnlocoDAOService unlocoDaoService) {
		this.unlocoDaoService = unlocoDaoService;
	}
	public void setAddressDaoService(IAddressDAOService addressDaoService) {
		this.addressDaoService = addressDaoService;
	}
	
	public void setProductDaoService(IProductDAOService productDaoService) {
		this.productDaoService = productDaoService;
	}
	public void setReferenceNumberTypeDaoService(
			IReferenceNumberTypeDAOService referenceNumberTypeDaoService) {
		this.referenceNumberTypeDaoService = referenceNumberTypeDaoService;
	}
	
	public void setPackUnitDaoService(IPackUnitDAOService packUnitDaoService) {
		this.packUnitDaoService = packUnitDaoService;
	}
	public void setDimUnitDaoService(IDimUnitDAOService dimUnitDaoService) {
		this.dimUnitDaoService = dimUnitDaoService;
	}
	public void setWeightUnitDaoService(IWeightUnitDAOService weightUnitDaoService) {
		this.weightUnitDaoService = weightUnitDaoService;
	}
	public void setProductTypeDaoService(
			IProductTypeDAOService productTypeDaoService) {
		this.productTypeDaoService = productTypeDaoService;
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
			Organization record = this.orgDaoService.getByCode("TESCUS1");
			if (record == null)
			{
				/**
				 * Org Data: TD ORG 1.0, 4.0, 6.0, 7.0
				 */
				//-- Unlocos: 
				Country de = countryDaoService.provide("DE", "Germany");
				CountryState csdefra = countryStateDaoService.provide("HE","Hessen", de.getId());
				Unloco defra = unlocoDaoService.provide("DEFRA", "", "Frankfurt am Main", de.getId(), csdefra.getId());
				
				Country gb = countryDaoService.provide("GB", "United Kingdom");
				CountryState csgbhlr = countryStateDaoService.provide("GTL","Greater London", de.getId());
				Unloco gbhlr = unlocoDaoService.provide("GBLHR", "", "Heathrow Apt/London	LHR", de.getId(), csdefra.getId());				
				
				Country us = countryDaoService.provide("US", "United States");
				CountryState csusbwi = countryStateDaoService.provide("MD","Maryland", us.getId());
				Unloco usbwi = unlocoDaoService.provide("USBWI", "", "Washington-Baltimore Int Apt", de.getId(), csdefra.getId());				
				
				CountryState csusdfw = countryStateDaoService.provide("TX","Texas", us.getId());
				Unloco usdfw = unlocoDaoService.provide("USDFW", "", "Dallas-Fort Worth Int Apt", de.getId(), csdefra.getId());	
				
				CountryState csusntn = countryStateDaoService.provide("MA","Massachusetts", us.getId());
				Unloco usntn = unlocoDaoService.provide("USNTN", "", "Newton", de.getId(), csdefra.getId());					
				
				CountryState csussfo = countryStateDaoService.provide("CA","California", us.getId());
				Unloco ussfo = unlocoDaoService.provide("USSFO", "", "San Francisco", de.getId(), csdefra.getId());
				
				//-- Orgs:
				//------------ 1.0-TESCUS1:
				Organization tescus1 = new Organization();
				tescus1.setName("Test Customer 1");
				tescus1.setCode("TESCUS1");
				tescus1 = this.orgDaoService.add(tescus1);
				Address tescus1_addr = addressDaoService.provide(Organization.class.getName(),tescus1.getId(),"123 Main St	Suite 1",null,null,null,"USDFW",null,us.getCode(),us.getName(),null,null);
				tescus1.setMainAddress(tescus1_addr);
				tescus1 = this.orgDaoService.update(tescus1);

				//------------ 4.0-TESCAR1:		
				Organization tescar1 = new Organization();
				tescar1.setName("Test Carrier 1");
				tescar1.setCode("TESCAR1");
				tescar1 = this.orgDaoService.add(tescar1);
				Address tescar1_addr = addressDaoService.provide(Organization.class.getName(),tescar1.getId(),"123 Main St	Suite 1",null,null,null,"USDFW",null,us.getCode(),us.getName(),null,null);
				tescar1.setMainAddress(tescar1_addr);
				tescar1 = this.orgDaoService.update(tescar1);
				
				//------------ 6.0-TESLOC1:		
				Organization tesloc1 = new Organization();
				tesloc1.setName("Test Location 1");
				tesloc1.setCode("TESLOC1");
				tesloc1 = this.orgDaoService.add(tesloc1);
				Address tesloc1_addr = addressDaoService.provide(Organization.class.getName(),tesloc1.getId(),"7 West Penn St",null,null,null,"USNTN",null,us.getCode(),us.getName(),null,null);
				tesloc1.setMainAddress(tesloc1_addr);
				tesloc1 = this.orgDaoService.update(tesloc1);	
				
				
				/**
				 * Prod Data: TD PRD 2.0, 3.0, 4.0
				 */
				//-- PRD 2.0
				packUnitDaoService.provideDefaults();
				dimUnitDaoService.provideDefaults();
				weightUnitDaoService.provideDefaults();
				productTypeDaoService.provideDefaults();
				referenceNumberTypeDaoService.provideDefaults();
				Product prd2 = productDaoService.provide("fooite1", "banana's",ProductTypeCustomCONSTANTS.TYPE_Food_Item,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				Product prd3 = productDaoService.provide("hazmat1", "Jet Fuel",ProductTypeCustomCONSTANTS.TYPE_Hazardous_Material,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				Product prd4 = productDaoService.provide("textil1", "Clothing",ProductTypeCustomCONSTANTS.TYPE_Textiles,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				
				/**
				 * Ref IDs: TD RIDTYP 2.0, 3.0, 4.0
				 */
				referenceNumberTypeDaoService.provideDefaults();
				
				this.globalTransactionManager.commit(status);
			}
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
