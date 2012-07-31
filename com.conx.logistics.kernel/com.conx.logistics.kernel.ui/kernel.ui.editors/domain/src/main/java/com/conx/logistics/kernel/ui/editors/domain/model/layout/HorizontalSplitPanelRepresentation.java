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
@Table(name="sysedhorizontalsplitpanelrepresentation")
public class HorizontalSplitPanelRepresentation extends AbstractSplitPanelRepresentation {
	public HorizontalSplitPanelRepresentation()
	{
	}
}
