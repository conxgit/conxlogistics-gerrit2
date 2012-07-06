package com.conx.logistics.app.whse.rcv.asn.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.conx.logistics.app.whse.domain.docktype.DockType;
import com.conx.logistics.app.whse.rcv.asn.shared.type.DROPMODE;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Organization;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whasnpickup")
public class ASNDropOff extends MultitenantBaseEntity implements Serializable {

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization cfs;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Address cfsAddress;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization dropOffAt;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Address dropOffAtAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date estimatedDropOff;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date actualDropOff;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dropOffRequiredBy;

    private String shippersRef;

    @Enumerated(EnumType.STRING)
    private DROPMODE dropMode;
    
    @ManyToOne(targetEntity = DockType.class, cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn
    private DockType dockType;

	public Organization getCfs() {
		return cfs;
	}

	public void setCfs(Organization cfs) {
		this.cfs = cfs;
	}

	public Address getCfsAddress() {
		return cfsAddress;
	}

	public void setCfsAddress(Address cfsAddress) {
		this.cfsAddress = cfsAddress;
	}

	public Organization getDropOffAt() {
		return dropOffAt;
	}

	public void setDropOffAt(Organization dropOffAt) {
		this.dropOffAt = dropOffAt;
	}

	public Address getDropOffAtAddress() {
		return dropOffAtAddress;
	}

	public void setDropOffAtAddress(Address dropOffAtAddress) {
		this.dropOffAtAddress = dropOffAtAddress;
	}

	public Date getEstimatedDropOff() {
		return estimatedDropOff;
	}

	public void setEstimatedDropOff(Date estimatedDropOff) {
		this.estimatedDropOff = estimatedDropOff;
	}

	public Date getActualDropOff() {
		return actualDropOff;
	}

	public void setActualDropOff(Date actualDropOff) {
		this.actualDropOff = actualDropOff;
	}

	public Date getDropOffRequiredBy() {
		return dropOffRequiredBy;
	}

	public void setDropOffRequiredBy(Date dropOffRequiredBy) {
		this.dropOffRequiredBy = dropOffRequiredBy;
	}

	public String getShippersRef() {
		return shippersRef;
	}

	public void setShippersRef(String shippersRef) {
		this.shippersRef = shippersRef;
	}

	public DROPMODE getDropMode() {
		return dropMode;
	}

	public void setDropMode(DROPMODE dropMode) {
		this.dropMode = dropMode;
	}

	public DockType getDockType() {
		return dockType;
	}

	public void setDockType(DockType dockType) {
		this.dockType = dockType;
	}
}
