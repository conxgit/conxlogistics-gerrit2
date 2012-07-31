package com.conx.logistics.kernel.metamodel.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="sysmmtype")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class AbstractType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateLastUpdated = new Date();
    
	protected String name;
	protected transient Class javaType;
	
	protected String entityJavaType;
    protected String entityJavaSimpleType;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getJavaType() {
		return javaType;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public String getEntityJavaType() {
		return entityJavaType;
	}

	public void setEntityJavaType(String entityJavaType) {
		this.entityJavaType = entityJavaType;
	}

	public String getEntityJavaSimpleType() {
		return entityJavaSimpleType;
	}

	public void setEntityJavaSimpleType(String entityJavaSimpleType) {
		this.entityJavaSimpleType = entityJavaSimpleType;
	}
	
	public AbstractType() {
		super();
	}	

	public AbstractType(String name, Class javaType, String entityJavaType,
			String entityJavaSimpleType) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.entityJavaType = entityJavaType;
		this.entityJavaSimpleType = entityJavaSimpleType;
	}
	
	
}
