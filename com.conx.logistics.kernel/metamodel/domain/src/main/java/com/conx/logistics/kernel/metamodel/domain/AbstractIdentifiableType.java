package com.conx.logistics.kernel.metamodel.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public abstract class AbstractIdentifiableType extends AbstractManagedType {

	@OneToOne
	private SingularAttribute idAttribute;

	@OneToOne
	private SingularAttribute versionAttribute;

	@OneToMany
	private Set<SingularAttribute> idClassAttributes = new HashSet<SingularAttribute>();

	
	public AbstractIdentifiableType() {
		super();
	}
	
	public AbstractIdentifiableType(String name, Class javaType,
			AbstractManagedType superType) {
		super(name, javaType, superType);
	}
	
	public AbstractIdentifiableType(String name, Class javaType,
			AbstractManagedType superType,
			SingularAttribute id, SingularAttribute version) {
		super(name, javaType, superType);
		this.idAttribute = id;
		this.versionAttribute = version;		
	}


}
