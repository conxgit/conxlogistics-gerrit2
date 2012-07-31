package com.conx.logistics.kernel.metamodel.domain;

import javax.persistence.Entity;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

/**
 * Subclass used to simply instantiation of singular attributes representing an entity's
 * identifier.
 */

@Entity
public class IdentifierAttribute extends SingularAttribute {
	public IdentifierAttribute(){
	}
	
	public IdentifierAttribute(
			String name,
			Class javaType,
			PersistenceType attributeType,
			PersistentAttributeType persistentAttributeType) {
		super( name, javaType, true, false, false, attributeType );
	}
}