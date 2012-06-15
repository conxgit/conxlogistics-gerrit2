package com.conx.logistics.mdm.domain.geolocation;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.BaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refunloco")
public class Unloco extends BaseEntity implements Serializable {

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn
    private Country country;
    
    @ManyToOne(targetEntity = CountryState.class)
    @JoinColumn
    private CountryState countryState;    

    private String portCity;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public CountryState getCountryState() {
		return countryState;
	}

	public void setCountryState(CountryState countryState) {
		this.countryState = countryState;
	}

	public String getPortCity() {
		return portCity;
	}

	public void setPortCity(String portCity) {
		this.portCity = portCity;
	}
}
