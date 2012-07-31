package com.conx.logistics.kernel.metamodel.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

@Entity
public class BasicType extends AbstractType {
	
	@Enumerated(EnumType.STRING)
	protected PersistentAttributeType persistentAttributeType;	
	
	public BasicType(){
	}	

	public BasicType(String name, Class javaType,
			PersistentAttributeType persistentAttributeType) {
		super(name, javaType, javaType.getName(), javaType.getSimpleName());
		this.persistentAttributeType = persistentAttributeType;
	}

}
