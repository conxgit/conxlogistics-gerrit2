/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model;

import java.util.Map;

import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingException;

public interface Mappable {

    Map<String, Object> getDataMap();
    
    void setDataMap(Map<String, Object> dataMap) throws FormEncodingException;
}
