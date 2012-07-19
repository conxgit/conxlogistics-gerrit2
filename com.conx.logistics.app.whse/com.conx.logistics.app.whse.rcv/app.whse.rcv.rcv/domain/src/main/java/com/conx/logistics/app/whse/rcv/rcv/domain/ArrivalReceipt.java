package com.conx.logistics.app.whse.rcv.rcv.domain;

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

import com.conx.logistics.app.whse.rcv.rcv.domain.types.ARRIVALRECEIPTSTATUS;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="wharrivalreceipt")
public class ArrivalReceipt extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Arrival.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Arrival parentArrival;
    
    @OneToOne(targetEntity = CommercialRecord.class, fetch = FetchType.LAZY)
    @JoinColumn
    private CommercialRecord commercialRecord;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReferenceNumber> refNumbers = new java.util.HashSet<ReferenceNumber>();
        
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArrivalReceiptLine> rcptLines = new java.util.HashSet<ArrivalReceiptLine>();
    
    
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization shipper;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization shippedFrom;
    
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization agent;      
    
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Organization consignee;  
    
    @Enumerated(EnumType.STRING)
    private ARRIVALRECEIPTSTATUS status;
}