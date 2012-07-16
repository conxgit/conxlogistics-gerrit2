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

import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.form.FormEncodingException;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="uiImageRolodexRepresentation")
public class ImageRolodexRepresentation extends FormItemRepresentation {

    private List<String> imageUrls = new ArrayList<String>();
    private boolean animated = true;
    private int selectedIndex = 0;
    
    public ImageRolodexRepresentation() {
        super("imageRolodex");
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setDataMap(Map<String, Object> data)
            throws FormEncodingException {
        super.setDataMap(data);
        List<Object> imagesMap = (List<Object>) data.get("imageUrls");
        imageUrls.clear();
        if (imagesMap != null) {
            for (Object obj : imagesMap) {
                imageUrls.add(obj.toString());
            }
        }
        Boolean anim = (Boolean) data.get("animated");
        this.animated = anim != null && anim;
        Integer ind = (Integer) data.get("selectedIndex");
        this.selectedIndex = ind == null ? 0 : ind;
    }
    
    @Override
    public Map<String, Object> getDataMap() {
        Map<String, Object> data = super.getDataMap();
        List<Object> imagesMap = new ArrayList<Object>();
        for (String imageUrl : imageUrls) {
            imagesMap.add(imageUrl);
        }
        data.put("imageUrls", imagesMap);
        data.put("animated", Boolean.valueOf(animated));
        data.put("selectedIndex", Integer.valueOf(selectedIndex));
        return data;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ImageRolodexRepresentation)) return false;
        ImageRolodexRepresentation other = (ImageRolodexRepresentation) obj;
        boolean equals = (this.imageUrls == null && other.imageUrls == null) || 
            (this.imageUrls != null && this.imageUrls.equals(other.imageUrls));
        if (!equals) return equals;
        equals = (this.animated == other.animated);
        if (!equals) return equals;
        equals = this.selectedIndex == other.selectedIndex;
        return equals;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        int aux = this.imageUrls == null ? 0 : this.imageUrls.hashCode();
        result = 37 * result + aux;
        aux = this.animated ? 0 : 1;
        result = 37 * result + aux;
        result = 37 * result + this.selectedIndex;
        return result;
    }
}
