package com.conx.logistics.mdm.domain.geolocation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refcountry")
public class Country extends BaseEntity implements Serializable {

	public  static boolean defaultsCreated = false;

    private String code3;

    private String number;
}
