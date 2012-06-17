package com.conx.logistics.mdm.domain.referencenumber;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmreferencenumbertype")
public class ReferenceNumberType extends MultitenantBaseEntity implements Serializable {
    @OneToOne(targetEntity = DefaultEntityMetadata.class)
    @JoinColumn
    private DefaultEntityMetadata entityMetadata;

	public DefaultEntityMetadata getEntityMetadata() {
		return entityMetadata;
	}

	public void setEntityMetadata(DefaultEntityMetadata entityMetadata) {
		this.entityMetadata = entityMetadata;
	}
}
