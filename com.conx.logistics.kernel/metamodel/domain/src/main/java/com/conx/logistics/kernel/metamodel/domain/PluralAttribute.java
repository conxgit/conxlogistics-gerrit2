package com.conx.logistics.kernel.metamodel.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public abstract class PluralAttribute
		extends AbstractAttribute
		implements Serializable {

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssociation() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		return true;
	}
	
	public PluralAttribute(){
		super(null);
	}
	
	public PluralAttribute(String name, EntityType elementType, EntityType parentEntityType) {
		super(name);
		setEntityType(elementType);
		setParentEntityType(parentEntityType);
	}	
}
