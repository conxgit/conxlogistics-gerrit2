/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model;

/**
 * Helper to create visually attractive components for 
 * generating javascript editors
 */
public interface FBScriptHelper {

    /**
     * Transform any UI loaded content into a script implementation
     * @return a script
     */
    String asScriptContent();
    
    /**
     * Returns a UI component that implements visual contents
     * @return
     */
    //Widget draw();
    
    /**
     * Returns the name of the UI script helper
     */
    String getName();
    
    /**
     * For initialization purposes, the represented script.
     * @param script
     */
    void setScript(FBScript script);
}
