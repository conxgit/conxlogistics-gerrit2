package com.conx.logistics.kernel.metamodel.domain;

import javax.persistence.Entity;
import javax.persistence.metamodel.PluralAttribute.CollectionType;

@Entity
public class SetAttribute extends PluralAttribute {
	
	public SetAttribute(){
	}
	
	public SetAttribute(String name, EntityType elementType, EntityType parentEntityType) {
		super(name, elementType,parentEntityType);
	}

	/**
	 * {@inheritDoc}
	 */
	public CollectionType getCollectionType() {
		return CollectionType.SET;
	}
}
