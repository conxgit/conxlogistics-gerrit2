/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.shared.menu;

import java.util.HashMap;
import java.util.Map;

import com.conx.logistics.kernel.ui.forms.domain.model.Mappable;




public class FormEffectDescription implements Mappable {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("className", this.className);
        return dataMap;
    }
    
    @Override
    public void setDataMap(Map<String, Object> dataMap) {
        this.className = (String) dataMap.get("className");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FormEffectDescription other = (FormEffectDescription) obj;
        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;
        return true;
    }
}
