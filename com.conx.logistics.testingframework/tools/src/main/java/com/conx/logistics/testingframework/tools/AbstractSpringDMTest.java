package com.conx.logistics.testingframework.tools;

import org.osgi.framework.BundleContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.test.platform.OsgiPlatform;
import org.springframework.osgi.test.provisioning.ArtifactLocator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.conx.logistics.testingframework.tools.utils.EclipseWorkspaceArtifactLocator;

/**
 * Abstract Base Class for JUnit Tests in a OSGI Environment
 */
public abstract class AbstractSpringDMTest extends AbstractConfigurableBundleCreatorTests {
    private static final String TEST_FRAMEWORK_BUNDLES_CONF_FILE = "/boot-bundles.properties";
    private ArtifactLocator     m_MavenArtifactLocator, m_EclipseArtifactLocator;
    
    private String pluginsDirPath = null;
    
    
    public void setPluginsDirPath(String pluginsDirPath) {
		this.pluginsDirPath = pluginsDirPath;
	}

	@Override
    protected boolean createManifestOnlyFromTestClass() {
        return false;
    }
    
    @Override
    protected void preProcessBundleContext(BundleContext platformBundleContext) throws Exception {
        super.preProcessBundleContext( platformBundleContext );
    }
    
    @Override
    protected void postProcessBundleContext(BundleContext context) throws Exception {
        super.postProcessBundleContext( context );
    }

    @Override
    /*
     * define OSGI/Equinox Properties which are set while starting up OSGI
     */
    protected OsgiPlatform createPlatform() {
        System.setProperty( "osgi.console",
                            "9000" );
        System.setProperty( "osgi.framework.extensions",
                            "osgi.framework.extensions" );
        return super.createPlatform();
    }

    @Override
    protected Resource getTestingFrameworkBundlesConfiguration() {
        return new InputStreamResource( AbstractSpringDMTest.class.getResourceAsStream( TEST_FRAMEWORK_BUNDLES_CONF_FILE ) );
    }

    /**
     * Use Eclipse artifact locator as default, falls back on Maven artifact
     * locator in artifact is not found.
     */
    protected Resource locateBundle(String bundleId) {
        Assert.hasText( bundleId,
                        "bundleId should not be empty" );
        
        Resource result = null;

        // parse the String
        String[] artifactId = StringUtils.commaDelimitedListToStringArray( bundleId );
        
        Assert.isTrue( artifactId.length >= 3,
                       "the CSV string " + bundleId + " contains too few values" );
        // TODO: add a smarter mechanism which can handle 1 or 2 values CSVs
        for ( int i = 0; i < artifactId.length; i++ ) {
            artifactId[i] = StringUtils.trimWhitespace( artifactId[i] );
        }

        if ( m_EclipseArtifactLocator == null ) {
            m_EclipseArtifactLocator = new EclipseWorkspaceArtifactLocator(this.pluginsDirPath);
        }
        result = (artifactId.length == 3 ? m_EclipseArtifactLocator.locateArtifact( artifactId[0],
                                                                                    artifactId[1],
                                                                                    artifactId[2] ) : m_EclipseArtifactLocator.locateArtifact( artifactId[0],
                                                                                                                                               artifactId[1],
                                                                                                                                               artifactId[2],
                                                                                                                                               artifactId[3] ));

        if ( result == null ) {
            if ( m_MavenArtifactLocator == null ) {
                m_MavenArtifactLocator = getLocator();
            }
            result = (artifactId.length == 3 ? m_MavenArtifactLocator.locateArtifact( artifactId[0],
                                                                                      artifactId[1],
                                                                                      artifactId[2] ) : m_MavenArtifactLocator.locateArtifact( artifactId[0],
                                                                                                                                               artifactId[1],
                                                                                                                                               artifactId[2],
                                                                                                                                               artifactId[3] ));
        }

        if ( result == null ) {
            throw new IllegalStateException( bundleId + " not found" );
        }

        return result;
    }

}
