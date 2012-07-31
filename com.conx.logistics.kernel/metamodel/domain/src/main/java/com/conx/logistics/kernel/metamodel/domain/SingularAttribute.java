package com.conx.logistics.kernel.metamodel.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class SingularAttribute
		extends AbstractAttribute
		implements Serializable {
	private boolean isIdentifier;
	private boolean isVersion;
	private boolean isOptional;
	private PersistenceType attributeType;
	
	public SingularAttribute(){
	}

	public SingularAttribute(
			String name,
			Class javaType,
			boolean isIdentifier,
			boolean isVersion,
			boolean isOptional,
			PersistenceType attributeType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		this.isIdentifier = isIdentifier;
		this.isVersion = isVersion;
		this.isOptional = isOptional;
		this.attributeType = attributeType;
	}
	
	public SingularAttribute(
			String name,
			Class javaType,
			boolean isIdentifier,
			boolean isVersion,
			boolean isOptional,
			PersistenceType attributeType,
			EntityType entityType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		super.setEntityType(entityType);
		this.isIdentifier = isIdentifier;
		this.isVersion = isVersion;
		this.isOptional = isOptional;
		this.attributeType = attributeType;
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
