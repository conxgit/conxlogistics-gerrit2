package com.conx.logistics.mdm.domain.geolocation;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refaddress")
public class Address extends BaseEntity implements Serializable {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<AddressCapability> addressCapabilities = new java.util.HashSet<AddressCapability>();

    private String street1;

    private String street2;

    private String state;

    private String phone;

    private String fax;

    private String email;

    private String zipCode;

    @ManyToOne(targetEntity = Unloco.class)
    @JoinColumn
    private Unloco unloco;

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn
    private Country country;

    @ManyToOne(targetEntity = CountryState.class)
    @JoinColumn
    private CountryState countryState;

    private String ownerEntityType;

    private Long ownerEntityId;

	public Set<AddressCapability> getAddressCapabilities() {
		return addressCapabilities;
	}

	public void setAddressCapabilities(Set<AddressCapability> addressCapabilities) {
		this.addressCapabilities = addressCapabilities;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Unloco getUnloco() {
		return unloco;
	}

	public void setUnloco(Unloco unloco) {
		this.unloco = unloco;
	}

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
