package com.conx.logistics.app.whse.domain.warehouse;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.conx.logistics.app.whse.domain.types.WAREHOUSETYPE;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Organization;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whwarehouse")
public class Warehouse extends MultitenantBaseEntity {
    @Enumerated(EnumType.STRING)
    private WAREHOUSETYPE whseType;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn
    private Address address;

    @ManyToOne(targetEntity = Organization.class)
    @JoinColumn
    private Organization partner;
}