package com.conx.logistics.mdm.domain.commercialrecord;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmcommercialrecord")
public class CommercialRecord extends MultitenantBaseEntity {
    @OneToOne(targetEntity = DefaultEntityMetadata.class)
    @JoinColumn
    private DefaultEntityMetadata parentEntityMetadata; 
    
    private long parentEntityId;
    
    @ManyToOne(targetEntity = CommercialRecord.class)
    @JoinColumn
    private CommercialValue parentCommercialRecord;	    

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CommercialRecord> childCommercialRecords = new java.util.HashSet<CommercialRecord>();
    
    @ManyToOne(targetEntity = CommercialValue.class)
    @JoinColumn
    private CommercialValue commercialValue;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReferenceNumber> refNumbers = new java.util.HashSet<ReferenceNumber>();

    private String ownerEntityType;

    private Long ownerEntityId;

	public DefaultEntityMetadata getParentEntityMetadata() {
		return parentEntityMetadata;
	}

	public void setParentEntityMetadata(DefaultEntityMetadata parentEntityMetadata) {
		this.parentEntityMetadata = parentEntityMetadata;
	}

	public long getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(long parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public CommercialValue getParentCommercialRecord() {
		return parentCommercialRecord;
	}

	public void setParentCommercialRecord(CommercialValue parentCommercialRecord) {
		this.parentCommercialRecord = parentCommercialRecord;
	}

	public Set<CommercialRecord> getChildCommercialRecords() {
		return childCommercialRecords;
	}

	public void setChildCommercialRecords(
			Set<CommercialRecord> childCommercialRecords) {
		this.childCommercialRecords = childCommercialRecords;
	}

	public CommercialValue getCommercialValue() {
		return commercialValue;
	}

	public void setCommercialValue(CommercialValue commercialValue) {
		this.commercialValue = commercialValue;
	}

	public Set<ReferenceNumber> getRefNumbers() {
		return refNumbers;
	}

	public void setRefNumbers(Set<ReferenceNumber> refNumbers) {
		this.refNumbers = refNumbers;
	}

	public String getOwnerEntityType() {
		return ownerEntityType;
	}

	public void setOwnerEntityType(String ownerEntityType) {
		this.ownerEntityType = ownerEntityType;
	}

	public Long getOwnerEntityId() {
		return ownerEntityId;
	}

	public void setOwnerEntityId(Long ownerEntityId) {
		this.ownerEntityId = ownerEntityId;
	}
}