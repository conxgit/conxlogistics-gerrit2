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
@Table(name="uiAudioRepresentation")
public class AudioRepresentation extends FormItemRepresentation {

    private String cssClassName;
    private String id;
    private String dataType;
    private String audioUrl;

    public AudioRepresentation() {
        super("audio");
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

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("audioUrl", this.audioUrl);
        data.put("cssClassName", this.cssClassName);
        data.put("dataType", this.dataType);
        data.put("id", this.id);
        return data;
    }

    @Override
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);
        this.audioUrl = (String) data.get("audioUrl");
        this.cssClassName = (String) data.get("cssClassName");
        this.dataType = (String) data.get("dataType");
        this.id = (String) data.get("id");
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof AudioRepresentation))
            return false;
        AudioRepresentation other = (AudioRepresentation) obj;
        boolean equals = (this.audioUrl == null && other.audioUrl == null)
                || (this.audioUrl != null && this.audioUrl
                        .equals(other.audioUrl));
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
        int aux = this.audioUrl == null ? 0 : this.audioUrl.hashCode();
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
