package com.conx.logistics.kernel.metamodel.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class BasicAttribute
		extends AbstractAttribute
		implements Serializable {
	private boolean isIdentifier;
	private boolean isVersion;
	private boolean isOptional;
	private PersistenceType attributeType;
	
	public BasicAttribute() {
		super();
	}

	public BasicAttribute(
			String name,
			Class javaType,
			EntityType parentEntityType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		setParentEntityType(parentEntityType);
	}
	

	/**
	 * {@inheritDoc}
	 */
	public boolean isId() {
		return isIdentifier;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isVersion() {
		return isVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOptional() {
		return isOptional;
	}

	/**
	 * {@inheritDoc}
	 */
	public PersistenceType getType() {
		return attributeType;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssociation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		return false;
	}
}
