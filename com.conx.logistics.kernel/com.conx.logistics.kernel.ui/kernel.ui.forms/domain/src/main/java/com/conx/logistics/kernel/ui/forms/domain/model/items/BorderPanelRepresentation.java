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



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="uiBorderPanelRepresentation")
public class BorderPanelRepresentation extends FormItemRepresentation {

    public static enum Position {
        SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, CENTER;
    }

    private Map<Position, FormItemRepresentation> items = new HashMap<Position, FormItemRepresentation>();

    public BorderPanelRepresentation() {
        super("borderPanel");
    }

    public Map<Position, FormItemRepresentation> getItems() {
        return items;
    }

    public void setItems(Map<Position, FormItemRepresentation> items) {
        this.items = items;
    }

    public FormItemRepresentation putItem(Position key,
            FormItemRepresentation value) {
        return items.put(key, value);
    }

}
