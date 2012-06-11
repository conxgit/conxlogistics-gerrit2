package com.conx.logistics.kernel.bpm.impl.jbpm.bpmserver.integrationtests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.conx.logistics.kernel.bpm.impl.jbpm.bpmserver.BaseTest;


public class BasicInterfaceTest extends BaseTest {

	public BasicInterfaceTest() {
		super();
	}
	
    protected void onSetUp() throws Exception {
    	super.onSetUp();
    }

    protected void onTearDown() throws Exception {
        super.onTearDown();
    }
    
    
    @Override
    protected Resource[] getTestBundles()
    {
    	List<Resource> testBndlResources = new ArrayList<Resource>();

    	testBndlResources.add(super.locateBundle("javax.xml.soap,com.springsource.javax.xml.soap,1.3.0"));
    	testBndlResources.add(super.locateBundle("javax.xml.ws,com.springsource.javax.xml.ws,2.1.1"));    	
    	testBndlResources.add(super.locateBundle("javax.xml.rpc,com.springsource.javax.xml.rpc,1.1.0.v20110517"));
    	testBndlResources.add(super.locateBundle("javax.ejb,com.springsource.javax.ejb,3.0.0"));    	
    	testBndlResources.add(super.locateBundle("javax.annotation,javax.annotation,1.1.0.v201105051105"));  
    	testBndlResources.add(super.locateBundle("org.apache.coyote.springsource,com.springsource.org.apache.coyote.springsource,6.0.32.S2-r1673"));
    	testBndlResources.add(super.locateBundle("org.apache.juli.springsource,com.springsource.org.apache.juli.extras.springsource,6.0.32.S2-r1673"));    	
    	//testBndlResources.add(super.locateBundle("org.apache.catalina.springsource,com.springsource.org.apache.catalina.springsource,6.0.32.S2-r1673"));
    	//testBndlResources.add(super.locateBundle("org.springframework,org.springframework.instrument.classloading,2.5.6.SEC02"));
    	
    	//testBndlResources.add(super.locateBundle("org.apache.catalina,com.springsource.org.apache.catalina,7.0.26"));
    	testBndlResources.add(super.locateBundle("org.apache.catalina,com.springsource.org.apache.catalina,7.0.26"));
    	testBndlResources.add(super.locateBundle("org.apache.coyote,com.springsource.org.apache.coyote,7.0.26"));
    	testBndlResources.add(super.locateBundle("org.apache.juli,com.springsource.org.apache.juli.extras,7.0.26"));
    	testBndlResources.add(super.locateBundle("org.apache.tomcat,com.springsource.org.apache.tomcat.api,7.0.26"));
    	testBndlResources.add(super.locateBundle("org.apache.tomcat,com.springsource.org.apache.tomcat.util,7.0.26"));
    	
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.knowledge-api,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.knowledge-internal-api,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.drools-core,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.jbpm,jbpm-flow,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.drools,drools-compiler,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.jbpm,jbpm-flow-builder,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.drools,drools-templates,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.drools,drools-decisiontables,5.4.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("org.jbpm,jbpm-bpmn2,5.3.0-SNAPSHOT"));

    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.asm,3.3.1"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.com.google.protobuf,2.4.1"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.osgi.wrapper.jxls-reader,0.9.8"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.bitronix,2.1.2"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.com.mysql.jdbc,5.1.20"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.gwt-console-rpc,2.3.2-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.netty,3.2.7.Final"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.hornetq,2.2.5.Final"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jboss-arrai-all,1.1-M1"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jbpm-bam,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.gwt-console-server-integration,2.3.2-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jbpm-human-task,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jbpm-human-task-mina,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jbpm-persistence-jpa,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.jbpm-workitems,5.3.0-SNAPSHOT"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.commons-exec,1.0.1"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.commons-compress,1.0"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.vaadin-addon-contextmenu,3.1.0"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.vaadin-addon-jpacontainer-agpl-3.0,2.0.0"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.thirdparty.vaadin-addon-mvp,0.9.0"));    
    	
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.common.utils,1.0.0")); 
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.mdm.domain,1.0.0"));   
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.persistence.datasource,1.0.0"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.persistence.tm.jta,1.0.0"));
    	testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.shared,1.0.0"));
    	//testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.taskserver.persistence,1.0.0"));
    	//testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.taskserver,1.0.0"));
    	//testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.bpmserver.persistence,1.0.0"));
    	//testBndlResources.add(super.locateBundle("com.conx.logistics,com.conx.logistics.kernel.bpm.impl.jbpm.core,1.0.0"));

    	
    	return testBndlResources.toArray(new Resource[]{});
    }        

    @Test
    public void testServiceAvailability() throws Exception {
        assertNotNull(bpmServiceEMF);
    }

    
}
