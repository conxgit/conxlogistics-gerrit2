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

import com.conx.logistics.app.whse.rcv.rcv.domain.types.ARRIVALRECEIPTLINESTATUS;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="wharrivalreceiptline")
public class ArrivalReceiptLine extends MultitenantBaseEntity {
	@OneToOne(targetEntity = CommercialRecord.class, fetch = FetchType.EAGER)
    @JoinColumn
    private CommercialRecord commercialRecord;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReferenceNumber> refNumbers = new java.util.HashSet<ReferenceNumber>();
        
    @OneToOne(targetEntity = ReceiveLine.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ReceiveLine receiveLine;
    
    @ManyToOne(targetEntity = ArrivalReceipt.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ArrivalReceipt parentArrivalReceipt;    

    @Enumerated(EnumType.STRING)
    private ARRIVALRECEIPTLINESTATUS status;
}