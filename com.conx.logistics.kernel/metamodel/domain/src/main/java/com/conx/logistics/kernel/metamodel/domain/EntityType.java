package com.conx.logistics.kernel.metamodel.domain;

import javax.persistence.Entity;

@Entity
public class EntityType extends AbstractIdentifiableType {
	private  String jpaEntityName;
	
	public EntityType() {
		super();
	}
	
	public EntityType(String name, Class javaType,
			AbstractManagedType superType,
			SingularAttribute id, SingularAttribute version,
			String jpaEntityName) {
		super(name, javaType, superType, id, version);
		this.jpaEntityName = jpaEntityName;
	}

	public EntityType(String name, Class javaType, 
			AbstractManagedType superType,
			String jpaEntityName) {
		super(name, javaType,superType);
		this.jpaEntityName = jpaEntityName;
	}

	public String getJpaEntityName() {
		return jpaEntityName;
	}

	public void setJpaEntityName(String jpaEntityName) {
		this.jpaEntityName = jpaEntityName;
	}
}
