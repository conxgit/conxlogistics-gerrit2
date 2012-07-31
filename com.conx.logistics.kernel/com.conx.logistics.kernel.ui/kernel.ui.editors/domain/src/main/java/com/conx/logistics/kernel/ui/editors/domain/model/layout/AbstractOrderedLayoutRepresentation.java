package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;

@MappedSuperclass
public class AbstractOrderedLayoutRepresentation extends AbstractLayoutRepresentation {
	private boolean spacing = false;
	
	@OneToMany
	private Set<OrderedLayoutComponent> components = new HashSet<OrderedLayoutComponent>();
	
	public void addComponent(EntityEditorComponentRepresentation component)
	{
		OrderedLayoutComponent c = new OrderedLayoutComponent(component, Alignment.TOP_LEFT, 1.0f, components.size());
		components.add(c);
	}
}
