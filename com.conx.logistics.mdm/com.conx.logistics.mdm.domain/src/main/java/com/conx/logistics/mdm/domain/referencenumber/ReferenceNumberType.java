package com.conx.logistics.mdm.domain.referencenumber;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.metadata.EntityMetadata;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdreferencenumbertype")
public class ReferenceNumberType extends MultitenantBaseEntity implements Serializable {
    @OneToOne(targetEntity = EntityMetadata.class)
    @JoinColumn
    private EntityMetadata entityMetadata;

	public EntityMetadata getEntityMetadata() {
		return entityMetadata;
	}

	public void setEntityMetadata(EntityMetadata entityMetadata) {
		this.entityMetadata = entityMetadata;
	}
}
