/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model.items;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingException;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingFactory;
import com.conx.logistics.kernel.ui.forms.shared.form.FormRepresentationDecoder;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="uiButtonRepresentation")
public class ButtonRepresentation extends FormItemRepresentation {

    private String text;
    private String name;
    private String id;
    private Map<String, String> i18n = new HashMap<String, String>();
    private String format;
    
    public ButtonRepresentation() {
        super("button");

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Map<String, String> getI18n() {
        return i18n;
    }
    
    public void setI18n(Map<String, String> i18n) {
        this.i18n = i18n;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("text", this.text);
        data.put("name", this.name);
        data.put("id", this.id);
        data.put("i18n", this.i18n);
        data.put("format", this.format);
        return data;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void setDataMap(Map<String, Object> data) throws FormEncodingException {
        super.setDataMap(data);
        this.text = (String) data.get("text");
        this.name = (String) data.get("name");
        this.id = (String) data.get("id");
        Map<String, String> i18nMap = (Map<String, String>) data.get("i18n");
        if (i18nMap != null) {
            this.i18n = new HashMap<String, String>();
            this.i18n.putAll(i18nMap);
        }
        this.format = (String) data.get("format");
        FormRepresentationDecoder decoder = FormEncodingFactory.getDecoder();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ButtonRepresentation)) return false;
        ButtonRepresentation other = (ButtonRepresentation) obj;
        boolean equals = (this.text == null && other.text == null) || (this.text != null && this.text.equals(other.text));
        if (!equals) return equals;
        equals = (this.name == null && other.name == null) || (this.name != null && this.name.equals(other.name));
        if (!equals) return equals;
        equals = (this.id == null && other.id == null) || (this.id != null && this.id.equals(other.id));
        if (!equals) return equals;
        if (!equals) return equals;
        equals = (this.i18n == null && other.i18n == null) || (this.i18n != null && this.i18n.entrySet().equals(other.i18n.entrySet()));
        if (!equals) return equals;
        equals = (this.format == null && other.format == null) || (this.format != null && this.format.equals(other.format));
        return equals;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.text == null ? 0 : this.text.hashCode();
        result = 37 * result + aux;
        aux = this.name == null ? 0 : this.name.hashCode();
        result = 37 * result + aux;
        aux = this.id == null ? 0 : this.id.hashCode();
        result = 37 * result + aux;
        result = 37 * result + aux;
        aux = this.i18n == null ? 0 : this.i18n.hashCode();
        result = 37 * result + aux;
        aux = this.format == null ? 0 : this.format.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
