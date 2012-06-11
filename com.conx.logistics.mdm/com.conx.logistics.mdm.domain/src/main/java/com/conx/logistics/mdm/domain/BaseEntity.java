package com.conx.logistics.mdm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@MappedSuperclass
public class BaseEntity  extends SuperBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    	
    @Version
    @Column(name = "version")
    private Integer version;
    
    
    private long ownerOrgId;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdated = new Date();
    
    private Boolean active = true;

    private String externalName;

    private String externalCode;

    private String externalRefId;
    
	private String parentRefId;

    private String name;

    @Column(unique = true)
    private String code;

    private String refId;
    
    private String description;
    
    private String portalId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}    

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}    

	public long getOwnerOrgId() {
		return ownerOrgId;
	}

	public void setOwnerOrgId(long ownerOrgId) {
		this.ownerOrgId = ownerOrgId;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getParentRefId() {
		return parentRefId;
	}

	public void setParentRefId(String parentRefId) {
		this.parentRefId = parentRefId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
    
}