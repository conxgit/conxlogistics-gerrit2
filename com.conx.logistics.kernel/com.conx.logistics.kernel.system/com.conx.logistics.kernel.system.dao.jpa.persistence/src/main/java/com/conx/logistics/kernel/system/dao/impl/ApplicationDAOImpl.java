package com.conx.logistics.kernel.system.dao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.system.dao.services.application.IFeatureDAOService;
import com.conx.logistics.kernel.system.dao.services.application.IFeatureSetDAOService;
import com.conx.logistics.mdm.domain.application.Application;
import com.conx.logistics.mdm.domain.application.Feature;

/**
 * Implementation of {@link Application} that uses JPA for persistence.<p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for this module, enables AspectJ weaving for
 * adding transaction demarcation to classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class ApplicationDAOImpl implements IApplicationDAOService {	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
    @Autowired
    private IFeatureDAOService featureDaoService;
    
    @Autowired
    private IFeatureSetDAOService featureSetDaoService;    

	public List<Application> getApplications() {
		return em.createQuery("select o from Application o order by o.id",Application.class).getResultList();
	}
	
	public Application findApplicationByCode(String code) {
		Application org = null;
		
		try
		{
			TypedQuery<Application> q = em.createQuery("select o from Application o WHERE o.code = :code",Application.class);
			q.setParameter("code", code);
						
			org = q.getSingleResult();
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return org;
	}
	

	@Override
	public Application addApplication(Application organization) {

		organization = em.merge(organization);
		
		return organization;
	}
	
	@Override
	public Application provideControlPanelApplication() {
		Application cpApp = findApplicationByCode(IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_CODE);
		if (Validator.isNull(cpApp))
		{
			/**
			 * 
			 * Control Panel App
			 * 
			 */
			cpApp = new Application(IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_CODE);
			cpApp.setName(IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_NAME);
			cpApp.setThemeIconPath("");
			
			cpApp = addApplication(cpApp);
			
			/**
			 * Modules Featureset
			 */
			Feature fs = new Feature(cpApp,null,IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_MODMNGMT_CODE);
			fs.setName(IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_MODMNGMT_NAME);
			fs = featureDaoService.addFeature(fs);
			cpApp.getFeatures().add(fs);
			
			Feature ft = new Feature(cpApp,fs, IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_MODMNGMT_MODS_SEARCH_CODE);
			ft.setName(SYSTEM_CONTROL_PANEL_APP_MODMNGMT_MODS_SEARCH_NAME);
			fs = featureDaoService.addFeature(fs);
			fs.getChildFeatures().add(ft);
			
			/**
			 * Workflow Management 
			 */
			
			/**
			 * ==== Work Task Defs Featureset
			 */
			fs = new Feature(cpApp,null,IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_WFMNGMT_CODE);
			fs.setName(IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_WFMNGMT_NAME);
			fs = featureDaoService.addFeature(fs);
			cpApp.getFeatures().add(fs);
			
			ft = new Feature(cpApp,fs,IApplicationDAOService.SYSTEM_CONTROL_PANEL_APP_WFMNGMT_WTDFS_SEARCH_CODE);
			ft.setName(SYSTEM_CONTROL_PANEL_APP_WFMNGMT_WTDFS_SEARCH_NAME);
			ft = featureDaoService.addFeature(ft);
			fs.getChildFeatures().add(ft);			
			
			cpApp = updateApplication(cpApp);
			
			
			/**
			 *
			 * Warehouse App
			 * 
			 **/
			Application whseApp = new Application(IApplicationDAOService.WAREHOUSE_APP_CODE);
			whseApp.setName(IApplicationDAOService.WAREHOUSE_APP_NAME);
			whseApp.setThemeIconPath("");
			
			whseApp = addApplication(whseApp);
			
			/**
			 * Receiving Featureset
			 */
			Feature mfs = new Feature(whseApp,null,IApplicationDAOService.WAREHOUSE_APP_RECEIVING_CODE);
			mfs.setName(IApplicationDAOService.WAREHOUSE_APP_RECEIVING_NAME);
			mfs = featureDaoService.addFeature(mfs);
			whseApp.getFeatures().add(mfs);
			
			Feature smfs = new Feature(whseApp,mfs,IApplicationDAOService.WAREHOUSE_APP_RECEIVING_ASN_CODE);
			smfs.setName(IApplicationDAOService.WAREHOUSE_APP_RECEIVING_ASN_NAME);
			smfs.setFeatureSet(true);
			smfs = featureDaoService.addFeature(smfs);
			mfs.getChildFeatures().add(smfs);
			mfs = featureDaoService.updateFeature(mfs);		
			
			Feature searchFt = new Feature(whseApp,smfs, IApplicationDAOService.WAREHOUSE_APP_RECEIVING_ASN_SEARCH_CODE);
			searchFt.setName(WAREHOUSE_APP_RECEIVING_ASN_SEARCH_NAME);
			searchFt = featureDaoService.addFeature(searchFt);
			smfs.getChildFeatures().add(searchFt);
			
			ft = new Feature(whseApp,smfs, IApplicationDAOService.WAREHOUSE_APP_RECEIVING_ASN_NEW_CODE);
			ft.setName(WAREHOUSE_APP_RECEIVING_ASN_NEW_NAME);
			ft.setTaskFeature(true);
			ft.setOnCompletionFeature(searchFt);
			ft.setCode("whse.rcv.asn.CreateNewASNByOrgV1.0");
			ft.setExternalCode("KERNEL.PAGEFLOW.STARTTASK");
			ft.setName("New");
			fs = featureDaoService.addFeature(fs);
			smfs.getChildFeatures().add(ft);	
			
			smfs = featureDaoService.updateFeature(smfs);

			whseApp = updateApplication(whseApp);					
			try {
				//em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return cpApp;
	}	

	@Override
	public void deleteApplication(Application app) {
		em.remove(app);
	}

	@Override
	public Application updateApplication(Application app) {
		return em.merge(app);
	}

	@Override
	public Application getApplication(long id) {
		return em.getReference(Application.class, id);
	}
}
