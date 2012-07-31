package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;

@MappedSuperclass
public class AbstractSplitPanelRepresentation extends AbstractLayoutRepresentation {
	@OneToOne	
    private EntityEditorComponentRepresentation firstComponent;

    @OneToOne
    private EntityEditorComponentRepresentation secondComponent;

    private int pos = 50;

    private int posUnit = Sizeable.UNITS_PERCENTAGE;

    private boolean posReversed = false;

    private boolean locked = false;

	public EntityEditorComponentRepresentation getFirstComponent() {
		return firstComponent;
	}

	public void setFirstComponent(EntityEditorComponentRepresentation firstComponent) {
		this.firstComponent = firstComponent;
	}

	public EntityEditorComponentRepresentation getSecondComponent() {
		return secondComponent;
	}

	public void setSecondComponent(
			EntityEditorComponentRepresentation secondComponent) {
		this.secondComponent = secondComponent;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getPosUnit() {
		return posUnit;
	}

	public void setPosUnit(int posUnit) {
		this.posUnit = posUnit;
	}

	public boolean isPosReversed() {
		return posReversed;
	}

	public void setPosReversed(boolean posReversed) {
		this.posReversed = posReversed;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
