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
public class ASNPickup extends MultitenantBaseEntity implements Serializable {

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization cfs;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Address cfsAddress;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization localTrans;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Address localTransAddress;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization pickUpFrom;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Address pickUpFromAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date estimatedPickup;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date localTransAdvised;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date actualPickup;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date pickupRequiredBy;
    
    @ManyToOne(targetEntity = DockType.class, cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn
    private DockType dockType;

    private String shippersRef;
    
    private String driverId;
    
    private String vehicleId;
    
    private String bolNumber;
    
    private String sealNumber;

    @Enumerated(EnumType.STRING)
    private DROPMODE dropMode;

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

	public Organization getLocalTrans() {
		return localTrans;
	}

	public void setLocalTrans(Organization localTrans) {
		this.localTrans = localTrans;
	}

	public Address getLocalTransAddress() {
		return localTransAddress;
	}

	public void setLocalTransAddress(Address localTransAddress) {
		this.localTransAddress = localTransAddress;
	}

	public Organization getPickUpFrom() {
		return pickUpFrom;
	}

	public void setPickUpFrom(Organization pickUpFrom) {
		this.pickUpFrom = pickUpFrom;
	}

	public Address getPickUpFromAddress() {
		return pickUpFromAddress;
	}

	public void setPickUpFromAddress(Address pickUpFromAddress) {
		this.pickUpFromAddress = pickUpFromAddress;
	}

	public Date getEstimatedPickup() {
		return estimatedPickup;
	}

	public void setEstimatedPickup(Date estimatedPickup) {
		this.estimatedPickup = estimatedPickup;
	}

	public Date getLocalTransAdvised() {
		return localTransAdvised;
	}

	public void setLocalTransAdvised(Date localTransAdvised) {
		this.localTransAdvised = localTransAdvised;
	}

	public Date getActualPickup() {
		return actualPickup;
	}

	public void setActualPickup(Date actualPickup) {
		this.actualPickup = actualPickup;
	}

	public Date getPickupRequiredBy() {
		return pickupRequiredBy;
	}

	public void setPickupRequiredBy(Date pickupRequiredBy) {
		this.pickupRequiredBy = pickupRequiredBy;
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

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getBolNumber() {
		return bolNumber;
	}

	public void setBolNumber(String bolNumber) {
		this.bolNumber = bolNumber;
	}

	public String getSealNumber() {
		return sealNumber;
	}

	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	public DockType getDockType() {
		return dockType;
	}

	public void setDockType(DockType dockType) {
		this.dockType = dockType;
	}
}
