package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;

@MappedSuperclass
public class AbstractLayoutRepresentation extends EntityEditorComponentRepresentation {
	
	@ManyToOne
	protected MarginInfo margins = new MarginInfo(false);
}
