package com.conx.logistics.mdm.domain.geolocation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refcountrystate")
public class CountryState extends BaseEntity implements Serializable {

    @ManyToOne(targetEntity = Country.class)
    private Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
