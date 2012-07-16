/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model.items;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingException;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="uiCanvasRepresentation")
public class CanvasRepresentation extends FormItemRepresentation {

    private String cssClassName;
    private String dataType;
    private String id;
    private String fallbackUrl;

    public CanvasRepresentation() {
        super("canvas");
    }

    public String getCssClassName() {
        return cssClassName;
    }

    public void setCssClassName(String cssClassName) {
        this.cssClassName = cssClassName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("cssClassName", this.cssClassName);
        data.put("dataType", this.dataType);
        data.put("id", this.id);
        data.put("fallbackUrl", this.fallbackUrl);
        return data;
    }

    @Override
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);
        this.fallbackUrl = (String) data.get("fallbackUrl");
        this.cssClassName = (String) data.get("cssClassName");
        this.dataType = (String) data.get("dataType");
        this.id = (String) data.get("id");
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof CanvasRepresentation))
            return false;
        CanvasRepresentation other = (CanvasRepresentation) obj;
        boolean equals = (this.fallbackUrl == null && other.fallbackUrl == null)
                || (this.fallbackUrl != null && this.fallbackUrl
                        .equals(other.fallbackUrl));
        if (!equals)
            return equals;
        equals = (this.cssClassName == null && other.cssClassName == null)
                || (this.cssClassName != null && this.cssClassName
                        .equals(other.cssClassName));
        if (!equals)
            return equals;
        equals = (this.dataType == null && other.dataType == null)
                || (this.dataType != null && this.dataType
                        .equals(other.dataType));
        if (!equals)
            return equals;
        equals = (this.id == null && other.id == null)
                || (this.id != null && this.id.equals(other.id));
        return equals;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.fallbackUrl == null ? 0 : this.fallbackUrl.hashCode();
        result = 37 * result + aux;
        aux = this.cssClassName == null ? 0 : this.cssClassName.hashCode();
        result = 37 * result + aux;
        aux = this.dataType == null ? 0 : this.dataType.hashCode();
        result = 37 * result + aux;
        aux = this.id == null ? 0 : this.id.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
