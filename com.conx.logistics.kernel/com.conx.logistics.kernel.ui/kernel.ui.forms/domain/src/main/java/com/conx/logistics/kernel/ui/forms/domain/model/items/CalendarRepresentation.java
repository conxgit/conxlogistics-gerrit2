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
@Table(name="uiCalendarRepresentation")
public class CalendarRepresentation extends FormItemRepresentation {

    private String iconUrl;
    private String calendarCss;
    private String defaultValue;
    
    public CalendarRepresentation() {
        super("calendar");
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCalendarCss() {
        return calendarCss;
    }

    public void setCalendarCss(String calendarCss) {
        this.calendarCss = calendarCss;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("defaultValue", this.defaultValue);
        data.put("calendarCss", this.calendarCss);
        data.put("iconUrl", this.iconUrl);
        return data;
    }
    
    @Override
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);    
        this.defaultValue = (String) data.get("defaultValue");
        this.calendarCss = (String) data.get("calendarCss");
        this.iconUrl = (String) data.get("iconUrl");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof CalendarRepresentation)) return false;
        CalendarRepresentation other = (CalendarRepresentation) obj;
        boolean equals = (this.defaultValue == null && other.defaultValue == null) || 
            (this.defaultValue != null && this.defaultValue.equals(other.defaultValue));
        if (!equals) return equals;
        equals = (this.calendarCss == null && other.calendarCss == null) || 
            (this.calendarCss != null && this.calendarCss.equals(other.calendarCss));
        if (!equals) return equals;
        equals = (this.iconUrl == null && other.iconUrl == null) || (this.iconUrl != null && this.iconUrl.equals(other.iconUrl));
        return equals;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.defaultValue == null ? 0 : this.defaultValue.hashCode();
        result = 37 * result + aux;
        aux = this.calendarCss == null ? 0 : this.calendarCss.hashCode();
        result = 37 * result + aux;
        aux = this.iconUrl == null ? 0 : this.iconUrl.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
