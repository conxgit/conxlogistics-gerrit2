package com.conx.logistics.app.whse.domain.location;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.conx.logistics.app.whse.domain.types.WAREHOUSETYPE;
import com.conx.logistics.app.whse.domain.warehouse.Warehouse;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Organization;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whlocation")
public class Location extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Warehouse warehouse;

    private Integer locRow;

    private Integer locCol;

    private String locLevel;
}