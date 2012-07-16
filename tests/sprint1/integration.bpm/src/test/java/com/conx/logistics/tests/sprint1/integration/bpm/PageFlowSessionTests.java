package com.conx.logistics.tests.sprint1.integration.bpm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


//@Test(groups = { "integration" })
@ContextConfiguration(locations = { "/META-INF/tm.jta-module-context.xml",
		                            "/META-INF/persistence.datasource-module-context.xml",
		                            "/META-INF/jbpm.persistence.datasource-module-context.xml",
		                            "/META-INF/persistence.dynaconfiguration-module-context.xml",
		                            "/META-INF/mdm.dao.services.impl-module-context.xml",
		                            "/META-INF/app.whse.dao.jpa.persistence-module-context.xml",
		                            "/META-INF/app.whse.rcv.asn.dao.jpa.persistence-module-context.xml",
		                            "/META-INF/data.uat.sprint1.data-module-context.xml"})
public class PageFlowSessionTests extends AbstractTestNGSpringContextTests {
	@Autowired
    private ApplicationContext applicationContext;

    @BeforeClass
    protected void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
    }

    @Test
    public void testNothing() {

    }

}
