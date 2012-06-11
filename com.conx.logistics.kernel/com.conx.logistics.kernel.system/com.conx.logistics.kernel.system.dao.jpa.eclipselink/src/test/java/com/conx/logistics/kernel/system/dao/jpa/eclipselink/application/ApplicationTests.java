package com.conx.logistics.kernel.system.dao.jpa.eclipselink.application;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.system.dao.services.application.IFeatureDAOService;
import com.conx.logistics.kernel.system.dao.services.application.IFeatureSetDAOService;
import com.conx.logistics.mdm.domain.application.Application;
import com.conx.logistics.mdm.domain.application.Feature;
import com.conx.logistics.mdm.domain.application.FeatureSet;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/module-context.xml","classpath:/META-INF/spring/test-context.xml" })
@TestExecutionListeners(value = DependencyInjectionTestExecutionListener.class)
public class ApplicationTests {
    @Autowired
    private IApplicationDAOService applicationDao;
    
    @Autowired
    private IFeatureSetDAOService featureSetDAOService;
    
    @Autowired
    private IFeatureDAOService featureDAOService;    
    
    @Test
    public void testApplicationBasics() {  
    	Application app = new Application();
    	app = applicationDao.addApplication(app);
    	Assert.assertNotNull(app);
    	
    	//app = applicationDao.provideControlPanelApplication();
    	//Assert.assertNotNull(app);
    }
    
    @Test
    public void testFeaturesetBasics() {  
    	Application app = new Application("TST");
    	app.setName("Test");
    	app = applicationDao.addApplication(app);
    	Assert.assertNotNull(app);
    	
    	Feature fs = new Feature(app,null, "FS1");
    	fs.setName("FS1");
    	fs = featureDAOService.addFeature(fs);
    	Assert.assertEquals("TST.FS1",fs.getCode());
    	
		Feature ft = new Feature(app,fs,"FS1F1");
		ft.setName("FS1F1");
		fs.getChildFeatures().add(ft);    
		ft = featureDAOService.addFeature(ft);
		Assert.assertEquals("TST.FS1.FS1F1",ft.getCode());
    }    
}

