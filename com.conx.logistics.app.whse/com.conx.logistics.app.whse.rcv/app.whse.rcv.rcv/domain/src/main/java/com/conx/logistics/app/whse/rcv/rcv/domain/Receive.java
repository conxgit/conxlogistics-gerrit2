package com.conx.logistics.app.whse.rcv.rcv.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.conx.logistics.app.tms.domain.types.DROPMODE;
import com.conx.logistics.app.tms.domain.types.TRANSMODE;
import com.conx.logistics.app.whse.domain.warehouse.Warehouse;
import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.rcv.domain.types.RECEIVESTATUS;
import com.conx.logistics.app.whse.rcv.rcv.domain.types.RECEIVETYPE;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.conx.logistics.mdm.domain.product.DimUnit;
import com.conx.logistics.mdm.domain.product.PackUnit;
import com.conx.logistics.mdm.domain.product.WeightUnit;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whreceive")
public class Receive extends MultitenantBaseEntity {
    @OneToOne(targetEntity = PackUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private PackUnit outerPackUnit;

    @OneToOne(targetEntity = WeightUnit.class, fetch = FetchType.LAZY)
    @JoinColumn
    private WeightUnit weightUnit;

    @OneToOne(targetEntity = DimUnit.class, fetch = FetchType.LAZY)
    @JoinColumn
    private DimUnit dimUnit;

    @OneToOne(targetEntity = DimUnit.class, fetch = FetchType.LAZY)
    @JoinColumn
    private DimUnit volUnit;

    @OneToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization shippedFrom;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization billTo;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization consignee;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Address consigneeDocAddress;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Address consigneeDelAddress;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization consignor;

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Address shipperAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReceiveLine> rcvLines = new java.util.HashSet<ReceiveLine>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Arrival> arrivals = new java.util.HashSet<Arrival>();

    @OneToOne(targetEntity = CommercialRecord.class, fetch = FetchType.LAZY)
    @JoinColumn
    private CommercialRecord commercialRecord;

    private Integer expectedTotalOuterPackCount;

    private Double expectedTotalweight;

    private Double expectedTotalLen;

    private Double expectedTotalWidth;

    private Double expectedTotalHeight;

    private Double expectedTotalVolume;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date estimatedFirstArrival;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date actualFirstArrival;

    @Enumerated(EnumType.STRING)
    private TRANSMODE mode;

    @Enumerated(EnumType.STRING)
    private RECEIVETYPE rcvType;

    private Long dlFolderId;

    private String asnId;

    @Enumerated(EnumType.STRING)
    private RECEIVESTATUS status;

    private boolean master;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateImported;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateLastImportUpdated;


    /**
     * Pickup and Delivery
     */
    @OneToOne(targetEntity = ASN.class, fetch = FetchType.LAZY)
    @JoinColumn
    private ASN asn;

    /**
     * Booking Info
     */
    @OneToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization bookingBranch;

    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Warehouse warehouse;

    private String bookedByUserCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date bookingDate;

    private String lastUpdatedByUserCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date lastUpdatedDate;

    @Enumerated(EnumType.STRING)
    private DROPMODE dropMode;	
}
