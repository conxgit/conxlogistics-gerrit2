package com.conx.logistics.mdm.domain.metadata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="mdmentitymetadata")
public class DefaultEntityMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateLastUpdated;

    private String entityJavaType;

    private String entityJavaSimpleType;

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
}
