package com.conx.logistics.mdm.domain.currency;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.BaseEntity;
import com.conx.logistics.mdm.domain.geolocation.Country;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refcurrencyunit")
public class CurrencyUnit extends BaseEntity implements Serializable {
    @ManyToOne(targetEntity = Country.class)
    @JoinColumn
    private Country country;
    
    private String symbol;
    
    private String subUnitName;
    
    private Integer subUnitRatio;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSubUnitName() {
		return subUnitName;
	}

	public void setSubUnitName(String subUnitName) {
		this.subUnitName = subUnitName;
	}

	public Integer getSubUnitRatio() {
		return subUnitRatio;
	}

	public void setSubUnitRatio(Integer subUnitRatio) {
		this.subUnitRatio = subUnitRatio;
	}
}
