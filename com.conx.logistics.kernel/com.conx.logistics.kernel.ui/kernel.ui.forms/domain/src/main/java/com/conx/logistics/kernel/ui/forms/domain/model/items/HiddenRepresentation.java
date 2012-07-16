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
@Table(name="uiHiddenRepresentation")
public class HiddenRepresentation extends FormItemRepresentation {

    private String id;
    private String name;
    private String value;

    public HiddenRepresentation() {
        super("hidden");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("id", this.id);
        data.put("name", this.name);
        data.put("value", this.value);
        return data;
    }

    @Override
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);
        this.id = (String) data.get("id");
        this.name = (String) data.get("name");
        this.value = (String) data.get("value");

    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof HiddenRepresentation))
            return false;
        HiddenRepresentation other = (HiddenRepresentation) obj;
        boolean equals = (this.id == null && other.id == null)
                || (this.id != null && this.id.equals(other.id));
        if (!equals)
            return equals;
        equals = (this.name == null && other.name == null)
                || (this.name != null && this.name.equals(other.name));
        if (!equals)
            return equals;
        equals = (this.value == null && other.value == null)
                || (this.value != null && this.value.equals(other.value));
        return equals;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.id == null ? 0 : this.id.hashCode();
        result = 37 * result + aux;
        aux = this.name == null ? 0 : this.name.hashCode();
        result = 37 * result + aux;
        aux = this.value == null ? 0 : this.value.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
