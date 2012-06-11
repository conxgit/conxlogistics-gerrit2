package com.conx.logistics.kernel.persistence.datasource.tests;

import javax.sql.DataSource;

import org.junit.Test;

import com.conx.logistics.testingframework.tools.AbstractSpringDMTest;

public class PooledDataSourceTest extends AbstractSpringDMTest {
	
	private DataSource pooledDataSource;
	
	public PooledDataSourceTest()
	{
		setPluginsDirPath("../../com.conx.logistics.distribution/com.conx.logistics.distribution.common-osgi-bundles");
	}

    @Test
    public void testPooledDS() {
        assertNotNull(pooledDataSource);
    }

	public void setPooledDataSource(DataSource pooledDataSource) {
		this.pooledDataSource = pooledDataSource;
	}
    
	
	@Override
	protected String getManifestLocation() {
		return "classpath:META-INF/MANIFEST.MF";
	}
    
}
