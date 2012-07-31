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



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysfmimagerepresentation")
public class ImageRepresentation extends FormItemRepresentation {

    private String altText;
    private String url;
    private String id;
    private Map<String, String> i18n = new HashMap<String, String>();

    public ImageRepresentation() {
        super("image");
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public Map<String, String> getI18n() {
        return i18n;
    }

    public void setI18n(Map<String, String> i18n) {
        this.i18n = i18n;
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("altText", this.altText);
        data.put("url", this.url);
        data.put("id", this.id);
        data.put("i18n", this.i18n);
        return data;
    }

    @Override
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);
        this.altText = (String) data.get("altText");
        this.url = (String) data.get("url");
        this.id = (String) data.get("id");
        @SuppressWarnings("unchecked")
        Map<String, String> i18nMap = (Map<String, String>) data.get("i18n");
        if (i18nMap != null) {
            this.i18n = new HashMap<String, String>();
            this.i18n.putAll(i18nMap);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ImageRepresentation))
            return false;
        ImageRepresentation other = (ImageRepresentation) obj;
        boolean equals = (this.altText == null && other.altText == null)
                || (this.altText != null && this.altText.equals(other.altText));
        if (!equals)
            return equals;
        equals = (this.url == null && other.url == null)
                || (this.url != null && this.url.equals(other.url));
        if (!equals)
            return equals;
        equals = (this.id == null && other.id == null)
                || (this.id != null && this.id.equals(other.id));
        if (!equals)
            return equals;
        equals = (this.i18n == null && other.i18n == null)
                || (this.i18n != null && this.i18n.entrySet().equals(
                        other.i18n.entrySet()));
        return equals;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.altText == null ? 0 : this.altText.hashCode();
        result = 37 * result + aux;
        aux = this.url == null ? 0 : this.url.hashCode();
        result = 37 * result + aux;
        aux = this.id == null ? 0 : this.id.hashCode();
        result = 37 * result + aux;
        aux = this.i18n == null ? 0 : this.i18n.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
