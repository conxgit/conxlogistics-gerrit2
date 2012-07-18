package com.conx.logistics.data.uat.sprint1.data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.app.whse.dao.services.IDockTypeDAOService;
import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDAOService;
import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNDropOffDAOService;
import com.conx.logistics.app.whse.rcv.asn.dao.services.IASNPickupDAOService;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.mdm.dao.services.IAddressDAOService;
import com.conx.logistics.mdm.dao.services.IContactDAOService;
import com.conx.logistics.mdm.dao.services.ICountryDAOService;
import com.conx.logistics.mdm.dao.services.ICountryStateDAOService;
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.dao.services.IOrganizationDAOService;
import com.conx.logistics.mdm.dao.services.IUnlocoDAOService;
import com.conx.logistics.mdm.dao.services.currency.ICurrencyUnitDAOService;
import com.conx.logistics.mdm.dao.services.documentlibrary.IDocTypeDAOService;
import com.conx.logistics.mdm.dao.services.product.IDimUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IPackUnitDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductDAOService;
import com.conx.logistics.mdm.dao.services.product.IProductTypeDAOService;
import com.conx.logistics.mdm.dao.services.product.IWeightUnitDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import com.conx.logistics.mdm.dao.services.referencenumber.IReferenceNumberTypeDAOService;
import com.conx.logistics.mdm.domain.constants.AddressCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.DimUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.PackUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.ProductTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.ReferenceNumberTypeCustomCONSTANTS;
import com.conx.logistics.mdm.domain.constants.WeightUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.geolocation.Country;
import com.conx.logistics.mdm.domain.geolocation.CountryState;
import com.conx.logistics.mdm.domain.geolocation.Unloco;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.organization.Contact;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;

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
	private ICurrencyUnitDAOService currencyUnitDAOService;
	private IASNDAOService asnDaoService;
	private IASNPickupDAOService asnPickupDAOService;
	private IASNDropOffDAOService asnDropOffDAOService;
	
	private IContactDAOService contactDAOService;
	private IDocTypeDAOService docTypeDOAService;
	private IDockTypeDAOService dockTypeDOAService;
	private IEntityMetadataDAOService entityMetadataDAOService;
	
	private IReferenceNumberTypeDAOService referenceNumberTypeDaoService;
	private IReferenceNumberDAOService referenceNumberDaoService;
	
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
	public void setReferenceNumberDaoService(
			IReferenceNumberDAOService referenceNumberDaoService) {
		this.referenceNumberDaoService = referenceNumberDaoService;
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
	public void setCurrencyUnitDaoService(
			ICurrencyUnitDAOService currencyUnitDAOService) {
		this.currencyUnitDAOService = currencyUnitDAOService;
	}
	
	public void setContactDAOService(IContactDAOService contactDAOService) {
		this.contactDAOService = contactDAOService;
	}
	
	public void setDocTypeDOAService(IDocTypeDAOService docTypeDOAService) {
		this.docTypeDOAService = docTypeDOAService;
	}
	
	public void setDockTypeDOAService(IDockTypeDAOService dockTypeDOAService) {
		this.dockTypeDOAService = dockTypeDOAService;
	}
	
	public void setEntityMetadataDAOService(
			IEntityMetadataDAOService entityMetadataDAOService) {
		this.entityMetadataDAOService = entityMetadataDAOService;
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
			logger.info("Checking Sprint # 1 UAT data");
			if (record == null)
			{
				logger.info("Generating Sprint # 1 UAT ");
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
				DefaultEntityMetadata orgEMD = entityMetadataDAOService.getByClass(Organization.class);
				//------------ 1.0-TESCUS1:
				Organization tescus1 = new Organization();
				tescus1.setName("Test Customer 1");
				tescus1.setCode("TESCUS1");
				tescus1 = this.orgDaoService.add(tescus1);
				
				Address tescus1_addr = addressDaoService.provide(Organization.class.getName(),tescus1.getId(),"123 Main St	Suite 1",null,null,null,"USDFW",null,us.getCode(),us.getName(),null,null);
				tescus1.setMainAddress(tescus1_addr);
				
				Contact tescus1_contact = new Contact();
				tescus1_contact.setFirstName("Aaron");
				tescus1_contact.setLastName("Anderson");
				tescus1_contact = contactDAOService.provide(orgEMD, tescus1.getId(), tescus1_contact);
				tescus1.setMainContact(tescus1_contact);
				
				tescus1 = this.orgDaoService.update(tescus1);

				//------------ 4.0-TESCAR1:		
				Organization tescar1 = new Organization();
				tescar1.setName("Test Carrier 1");
				tescar1.setCode("TESCAR1");
				tescar1 = this.orgDaoService.add(tescar1);
				
				Address tescar1_addr = addressDaoService.provide(Organization.class.getName(),tescar1.getId(),"123 Main St	Suite 1",null,null,null,"USDFW",null,us.getCode(),us.getName(),null,null);
				tescar1.setMainAddress(tescar1_addr);
				
				Contact tescar1_contact = new Contact();
				tescar1_contact.setFirstName("Don");
				tescar1_contact.setLastName("Davis");
				tescar1_contact = contactDAOService.provide(orgEMD, tescar1.getId(), tescar1_contact);
				tescar1.setMainContact(tescar1_contact);		
				
				tescar1 = this.orgDaoService.update(tescar1);
				
				//------------ 6.0-TESLOC1:		
				Organization tesloc1 = new Organization();
				tesloc1.setName("Test Location 1");
				tesloc1.setCode("TESLOC1");
				tesloc1 = this.orgDaoService.add(tesloc1);
				
				Address tesloc1_addr = addressDaoService.provide(Organization.class.getName(),tesloc1.getId(),"7 West Penn St",null,null,null,"USNTN",null,us.getCode(),us.getName(),null,null);
				tesloc1.setMainAddress(tesloc1_addr);
				
				Contact tesloc1_contact = new Contact();
				tesloc1_contact.setFirstName("Jon");
				tesloc1_contact.setLastName("Drews");
				tesloc1_contact = contactDAOService.provide(orgEMD, tesloc1.getId(), tesloc1_contact);
				tesloc1.setMainContact(tesloc1_contact);	
				
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
				currencyUnitDAOService.provideDefaults();
				docTypeDOAService.provideDefaults();
				dockTypeDOAService.provideDefaults();
				Product prd2 = productDaoService.provide("fooite1", "banana's",ProductTypeCustomCONSTANTS.TYPE_Food_Item,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				Product prd3 = productDaoService.provide("hazmat1", "Jet Fuel",ProductTypeCustomCONSTANTS.TYPE_Hazardous_Material,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				Product prd4 = productDaoService.provide("textil1", "Clothing",ProductTypeCustomCONSTANTS.TYPE_Textiles,PackUnitCustomCONSTANTS.TYPE_PCE,WeightUnitCustomCONSTANTS.TYPE_LB,DimUnitCustomCONSTANTS.TYPE_FT,DimUnitCustomCONSTANTS.TYPE_CF,"GEN",null);
				
				/**
				 * 
				 * Sample ASN
				 * 
				 */
				DefaultEntityMetadata asnEMD = entityMetadataDAOService.getByClass(ASN.class);
				//- Ref Numbers
				ReferenceNumberType fedexRefType = referenceNumberTypeDaoService.getByCode(ReferenceNumberTypeCustomCONSTANTS.TYPE_FEDEX);
				ReferenceNumber rn1 = new ReferenceNumber();
				rn1.setCode("122345678899");
				rn1.setValue("122345678899");
				rn1.setType(fedexRefType);
				rn1.setEntityMetadata(asnEMD);
				rn1  = referenceNumberDaoService.add(rn1);
				
				ReferenceNumber rn2 = new ReferenceNumber();
				rn2.setCode("998877665544332211");
				rn2.setValue("122345678899");
				rn2.setType(fedexRefType);
				rn2.setEntityMetadata(asnEMD);
				rn2  = referenceNumberDaoService.add(rn2);				
				
				Set<ReferenceNumber> refNumList = new HashSet<ReferenceNumber>();
				refNumList.add(rn1);
				refNumList.add(rn2);
				
				ASNLine al1 = new ASNLine();
				al1.setCode("AL1");
				al1.setProduct(prd2);
				al1.setRefNumber(rn1);
				al1.setLineNumber(0);
				al1.setExpectedInnerPackCount(8);
				al1.setExpectedOuterPackCount(12);
				al1.setExpectedTotalWeight(28.0);
				al1.setExpectedTotalVolume(12.54);
				al1.setExpectedTotalLen(9.3);
				al1.setExpectedTotalWidth(1.0);
				al1.setExpectedTotalHeight(1.39);
				al1.setDescription("A90234708-3292389 Laptop Package");
				
				ASNLine al2 = new ASNLine();
				al2.setCode("AL2");
				al2.setProduct(prd3);
				al2.setRefNumber(rn2);
				al2.setLineNumber(0);
				al2.setExpectedInnerPackCount(18);
				al2.setExpectedOuterPackCount(2);
				al2.setExpectedTotalWeight(28.0);
				al2.setExpectedTotalVolume(12.54);
				al2.setExpectedTotalLen(9.3);
				al2.setExpectedTotalWidth(1.0);
				al2.setExpectedTotalHeight(1.39);
				al2.setDescription("AODK-DLKDJ WKIWKWI");
				
				Set<ASNLine> asnLineList = new HashSet<ASNLine>();
				asnLineList.add(al1);
				asnLineList.add(al2);
				
				ASNPickup pickup1 = new ASNPickup();
				ASNDropOff dropOff1 = new ASNDropOff();
				
				pickup1.setCode("PKUP1");
				pickup1.setPickUpFrom(tescus1);
				pickup1.setPickUpFromAddress(tescus1_addr);
				pickup1.setLocalTrans(tescar1);
				pickup1.setLocalTransAddress(tescar1_addr);
				pickup1.setDriverId("DRV001");
				pickup1.setVehicleId("DRV001");
				pickup1.setBolNumber("DRV001");
				pickup1.setVehicleId("DRV001");
				pickup1.setEstimatedPickup(new Date());
				
				pickup1 = asnPickupDAOService.add(pickup1);
				
				
				dropOff1.setDropOffAt(tescus1);
				dropOff1.setCode("DRPOF1");
				dropOff1.setDropOffAtAddress(tescus1_addr);
				dropOff1.setEstimatedDropOff(new Date());
				dropOff1 = asnDropOffDAOService.add(dropOff1);
				
				ASN asn1 = new ASN();
				asn1.setCode("ASN1");
				asn1 = asnDaoService.add(asn1);
				
				rn1.setEntityPK(asn1.getId());
				rn2.setEntityPK(asn1.getId());
				referenceNumberDaoService.update(rn1);
				referenceNumberDaoService.update(rn2);
				
				asnDaoService.addRefNums(asn1.getId(), refNumList);
				asnDaoService.addLines(asn1.getId(), asnLineList);
				asnDaoService.addLocalTrans(asn1.getId(), pickup1, dropOff1);
				
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
	public IASNDAOService getAsnDaoService() {
		return asnDaoService;
	}
	public void setAsnDaoService(IASNDAOService asnDaoService) {
		this.asnDaoService = asnDaoService;
	}
	public IASNPickupDAOService getAsnPickupDAOService() {
		return asnPickupDAOService;
	}
	public void setAsnPickupDAOService(IASNPickupDAOService asnPickupDAOService) {
		this.asnPickupDAOService = asnPickupDAOService;
	}
	public IASNDropOffDAOService getAsnDropOffDAOService() {
		return asnDropOffDAOService;
	}
	public void setAsnDropOffDAOService(IASNDropOffDAOService asnDropOffDAOService) {
		this.asnDropOffDAOService = asnDropOffDAOService;
	}
}
