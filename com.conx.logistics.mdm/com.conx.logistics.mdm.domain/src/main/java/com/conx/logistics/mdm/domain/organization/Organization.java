package com.conx.logistics.mdm.domain.organization;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.geolocation.Address;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="reforganization")
public class Organization extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Address.class)
    private Address mainAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address shippingAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address receivingAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address billingAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address pickupAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address deliveryAddress;
    @ManyToOne(targetEntity = Address.class)
    private Address adHocAddress;

    @ManyToOne(targetEntity = Contact.class)
    private Contact mainContact;    

	public Address getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(Address mainAddress) {
		this.mainAddress = mainAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(Address receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(Address pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Address getAdHocAddress() {
		return adHocAddress;
	}

	public void setAdHocAddress(Address adHocAddress) {
		this.adHocAddress = adHocAddress;
	}

	public Contact getMainContact() {
		return mainContact;
	}

	public void setMainContact(Contact mainContact) {
		this.mainContact = mainContact;
	}	
}
