package com.conx.logistics.mdm.domain.commercialrecord;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmcommercialrecord")
public class CommercialRecord extends MultitenantBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Version
    @Column(name = "version")
    private Integer version;
}