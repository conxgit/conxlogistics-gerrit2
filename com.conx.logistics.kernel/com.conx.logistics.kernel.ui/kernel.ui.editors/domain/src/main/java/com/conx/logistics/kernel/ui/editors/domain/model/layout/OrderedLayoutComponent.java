package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysedcomponentexpandratio")
public class OrderedLayoutComponent extends MultitenantBaseEntity {
    @ManyToOne
    private EntityEditorComponentRepresentation component;
    
    @OneToOne
    private Alignment alignment;
    
    private Float ratio;        
    
    private int index;

	public OrderedLayoutComponent(
			EntityEditorComponentRepresentation component, Alignment alignment,
			Float ratio, int index) {
		super();
		this.component = component;
		this.alignment = alignment;
		this.ratio = ratio;
		this.index = index;
	}
	
	
    public EntityEditorComponentRepresentation getComponent() {
		return component;
	}



	public void setComponent(EntityEditorComponentRepresentation component) {
		this.component = component;
	}



	public Alignment getAlignment() {
		return alignment;
	}



	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}



	public Float getRatio() {
		return ratio;
	}



	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderedLayoutComponent)) {
            return false;
        }

        OrderedLayoutComponent other = (OrderedLayoutComponent)obj;
        boolean equals = (this.component == null && other.component == null)
                || (this.component != null && this.component
                        .equals(other.component));
        
        if (!equals)
            return equals;
        
        equals = (this.alignment == null && other.alignment == null)
                || (this.alignment != null && this.alignment.equals(other.alignment));
        
        if (!equals)
            return equals;
        
        equals = (this.ratio == null && other.ratio == null)
                || (this.ratio != null && this.ratio.equals(other.ratio));    
        
        equals = this.index == other.index;

        return equals;       
    }	
    
    
}
