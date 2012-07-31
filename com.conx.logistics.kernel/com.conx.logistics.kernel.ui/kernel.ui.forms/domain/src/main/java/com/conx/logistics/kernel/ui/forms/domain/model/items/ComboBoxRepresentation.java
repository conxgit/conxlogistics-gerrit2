/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.forms.domain.model.FBScript;
import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingException;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingFactory;
import com.conx.logistics.kernel.ui.forms.shared.form.FormRepresentationDecoder;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysfmcomboboxrepresentation")
public class ComboBoxRepresentation extends FormItemRepresentation {

    private List<OptionRepresentation> elements;
    private FBScript elementsPopulationScript;
    private String name;
    private String id;

    public ComboBoxRepresentation() {
        super("comboBox");
    }
    
    public List<OptionRepresentation> getElements() {
        return elements;
    }

    public void setElements(List<OptionRepresentation> elements) {
        this.elements = elements;
    }

    public FBScript getElementsPopulationScript() {
        return elementsPopulationScript;
    }

    public void setElementsPopulationScript(FBScript elementsPopulationScript) {
        this.elementsPopulationScript = elementsPopulationScript;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        data.put("name", this.name);
        data.put("id", this.id);
        List<Map<String, Object>> elementsAsMap = new ArrayList<Map<String, Object>>();
        if (this.elements != null) {
            for (OptionRepresentation option : this.elements) {
                elementsAsMap.add(option.getDataMap());
            }
        }
        data.put("elements", elementsAsMap);
        data.put("elementsPopulationScript", this.elementsPopulationScript == null ? null : this.elementsPopulationScript.getDataMap());
        return data;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void setDataMap(Map<String, Object> data) throws FormEncodingException {
        super.setDataMap(data);
        this.name = (String) data.get("name");
        this.id = (String) data.get("id");
        this.elements = new ArrayList<OptionRepresentation>();
        List<Map<String, Object>> elems = (List<Map<String, Object>>) data.get("elements");
        FormRepresentationDecoder decoder = FormEncodingFactory.getDecoder();
        if (elems != null) {
            for (Map<String, Object> map : elems) {
                this.elements.add((OptionRepresentation) decoder.decode(map));
            }
        }
        this.elementsPopulationScript = (FBScript) decoder.decode((Map<String, Object>) data.get("elementsPopulationScript"));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ComboBoxRepresentation)) return false;
        ComboBoxRepresentation other = (ComboBoxRepresentation) obj;
        boolean equals = (this.elements == null && other.elements == null) || 
            (this.elements != null && this.elements.equals(other.elements));
        if (!equals) return equals;
        equals = (this.elementsPopulationScript == null && other.elementsPopulationScript == null) || 
            (this.elementsPopulationScript != null && this.elementsPopulationScript.equals(other.elementsPopulationScript));
        if (!equals) return equals;
        equals = (this.name == null && other.name == null) || (this.name != null && this.name.equals(other.name));
        if (!equals) return equals;
        equals = (this.id == null && other.id == null) || (this.id != null && this.id.equals(other.id));
        return equals;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.elements == null ? 0 : this.elements.hashCode();
        result = 37 * result + aux;
        aux = this.elementsPopulationScript == null ? 0 : this.elementsPopulationScript.hashCode();
        result = 37 * result + aux;
        aux = this.name == null ? 0 : this.name.hashCode();
        result = 37 * result + aux;
        aux = this.id == null ? 0 : this.id.hashCode();
        result = 37 * result + aux;
        return result;
    }
}
