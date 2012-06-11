package com.conx.logistics.testingframework.tools.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

/**
 * Locator for artifacts found in the local Eclipse plugins workspace and
 * target. Does <strong>not</strong> use Eclipse PDE, it rather
 * uses the Eclipse patterns and conventions to identify the artifacts.
 * 
 * It gives priority to workspace bundle over target bundles.
 */
public class EclipseWorkspaceArtifactLocator implements ArtifactLocator {

    private static final Logger log = LoggerFactory.getLogger(EclipseWorkspaceArtifactLocator.class);
    
    private EclipseArtifactFinder m_ArtifactFinder = null;
    
    public EclipseWorkspaceArtifactLocator(String pluginDirPath)
    {
    	m_ArtifactFinder = new EclipseArtifactFinder(pluginDirPath);
    }
    
    /**
     * Find an artifact in the list of bundles from current Eclipse Workspace.
     * 
     * @param groupId
     *            - not used
     * @param artifactId
     *            - the artifact id of the bundle (required)
     * @param version
     *            - the version of the bundle (can be null)
     * @return the String representing the URL location of this bundle
     */
    public Resource locateArtifact(String groupId, String artifactId,
            String version) {
        return locateArtifact(groupId, artifactId, version,
                DEFAULT_ARTIFACT_TYPE);
    }

    public Resource locateArtifact(String groupId, String artifactId,
            String version, String type) {
        return localEclipseWorkspaceArtifact(artifactId, version);
    }

    /**
     * Locate an artifact in an Eclipse Workspace
     * 
     * @param artifactId
     *            - the artifact id of the bundle (required)
     * @param version
     *            - the version of the bundle (can be null)
     * @return Resource corresponding to the located Eclipse bundle
     */
    private Resource localEclipseWorkspaceArtifact(String artifactId, String version) {
        Resource res = m_ArtifactFinder.findArtifact(artifactId, version);
        if (res != null && log.isDebugEnabled()) {
            log.debug("[" + artifactId + "|" + version + "] resolved to "
                    + res.getDescription() + " as a Eclipse artifact");
        }
        return res;
    }

}
