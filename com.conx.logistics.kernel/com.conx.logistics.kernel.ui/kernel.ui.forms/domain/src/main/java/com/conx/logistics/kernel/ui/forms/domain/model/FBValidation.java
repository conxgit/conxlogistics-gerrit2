/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model;

public interface FBValidation extends Mappable {

    boolean isValid(Object obj);
    
    FBValidation cloneValidation();
    
    String getValidationId();
}
