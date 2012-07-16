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
@Table(name="uiClientScriptRepresentation")
public class ClientScriptRepresentation extends FormItemRepresentation {

    private String type;
    private String src;
    
    public ClientScriptRepresentation() {
        super("clientScript");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public void setDataMap(Map<String, Object> data) throws FormEncodingException {
        this.type = (String) data.get("type");
        this.src = (String) data.get("src");
        super.setDataMap(data);
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("src", this.src);
        data.put("type", this.type);
        return data;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ClientScriptRepresentation)) return false;
        ClientScriptRepresentation other = (ClientScriptRepresentation) obj;
        boolean equals = (this.src == null && other.src == null) || (this.src != null && this.src.equals(other.src));
        if (!equals) return equals;
        equals = (this.type == null && other.type == null) || (this.type != null && this.type.equals(other.type));
        return equals;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.src == null ? 0 : this.src.hashCode();
        result = 37 * result + aux;
        aux = this.type == null ? 0 : this.type.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
