package com.conx.logistics.mdm.domain.referencenumber;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.metadata.EntityMetadata;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdreferencenumber")
public class ReferenceNumber extends MultitenantBaseEntity implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReferenceNumber> childReferenceNumbers = new java.util.HashSet<ReferenceNumber>();

    @OneToOne(targetEntity = ReferenceNumber.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ReferenceNumber parentReferenceNumber;

    @ManyToOne(targetEntity = ReferenceNumberType.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ReferenceNumberType type;
    
    @OneToOne(targetEntity = EntityMetadata.class, fetch = FetchType.EAGER)
    @JoinColumn
    private EntityMetadata entityMetadata;      

    private Long entityPK;

    private String entityClassName;

    private boolean rootNum;

    private String value;
}
