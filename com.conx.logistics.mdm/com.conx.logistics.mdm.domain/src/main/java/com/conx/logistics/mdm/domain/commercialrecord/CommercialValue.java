package com.conx.logistics.mdm.domain.commercialrecord;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.currency.CurrencyUnit;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmcommercialvalue")
public class CommercialValue extends MultitenantBaseEntity implements Serializable {

    @ManyToOne(targetEntity = CommercialRecord.class)
    @JoinColumn
    private CommercialRecord parentCommercialRecord;

    @ManyToOne(targetEntity = CurrencyUnit.class)
    @JoinColumn
    private CurrencyUnit currency;

    private Double value;

	public CommercialRecord getParentCommercialRecord() {
		return parentCommercialRecord;
	}

	public void setParentCommercialRecord(CommercialRecord parentCommercialRecord) {
		this.parentCommercialRecord = parentCommercialRecord;
	}

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
