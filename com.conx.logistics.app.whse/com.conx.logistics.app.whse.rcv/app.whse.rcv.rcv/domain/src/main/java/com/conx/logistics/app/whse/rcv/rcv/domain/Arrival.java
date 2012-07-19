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

import com.conx.logistics.app.tms.domain.types.TRANSMODE;
import com.conx.logistics.app.whse.rcv.rcv.domain.types.ARRIVALSTATUS;
import com.conx.logistics.app.whse.rcv.rcv.domain.types.ARRIVALTYPE;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.organization.Organization;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="wharrival")
public class Arrival extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization actualLocalTrans;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization actualShipper;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization actualShippedFrom;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReceiveLineStockItemSet> rlStockItems = new java.util.HashSet<ReceiveLineStockItemSet>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArrivalReceipt> receipts = new java.util.HashSet<ArrivalReceipt>();
    
    @OneToOne(targetEntity = Receive.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Receive receive;

    private String bolNumber;

    private String vehicleNumber;

    private String driverName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date arrivalTime;

    private Long bolImageId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date weekEndingDate;

    private String weekEnding;

    @Enumerated(EnumType.STRING)
    private ARRIVALTYPE arvlType;
    
    @Enumerated(EnumType.STRING)
    private TRANSMODE mode;

    @Enumerated(EnumType.STRING)
    private ARRIVALSTATUS arrvlStatus;
}