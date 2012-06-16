package com.conx.logistics.app.whse.rcv.asn.domain;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.conx.logistics.app.whse.rcv.asn.shared.type.DROPMODE;
import com.conx.logistics.mdm.domain.BaseEntity;
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

    private String shippersRef;

    @Enumerated(EnumType.STRING)
    private DROPMODE dropMode;
}
