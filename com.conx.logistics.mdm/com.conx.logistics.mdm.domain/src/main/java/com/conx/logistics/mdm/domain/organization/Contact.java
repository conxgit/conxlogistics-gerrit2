package com.conx.logistics.mdm.domain.organization;

import java.io.Serializable;
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

import com.conx.logistics.mdm.domain.BaseEntity;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refcontact")
public class Contact extends BaseEntity implements Serializable {
    private String firstName;

    private String lastName;

    private String officePhoneNumber;

    private String cellPhoneNumber;

    private String faxPhoneNumber;

    private String email;

    @OneToOne(targetEntity = DefaultEntityMetadata.class)
    @JoinColumn
    private DefaultEntityMetadata entityMetadata;      

    private Long entityPK;
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOfficePhoneNumber() {
		return officePhoneNumber;
	}

	public void setOfficePhoneNumber(String officePhoneNumber) {
		this.officePhoneNumber = officePhoneNumber;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getFaxPhoneNumber() {
		return faxPhoneNumber;
	}

	public void setFaxPhoneNumber(String faxPhoneNumber) {
		this.faxPhoneNumber = faxPhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DefaultEntityMetadata getEntityMetadata() {
		return entityMetadata;
	}

	public void setEntityMetadata(DefaultEntityMetadata entityMetadata) {
		this.entityMetadata = entityMetadata;
	}

	public Long getEntityPK() {
		return entityPK;
	}

	public void setEntityPK(Long entityPK) {
		this.entityPK = entityPK;
	}
}
