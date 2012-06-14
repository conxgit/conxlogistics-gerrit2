package com.conx.logistics.mdm.domain.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.conx.logistics.mdm.domain.BaseEntity;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysapplicationsubmodule")
public class ApplicationSubModule extends BaseEntity {

    @ManyToOne(targetEntity = ApplicationModule.class)
    private ApplicationModule parentModule;    
	
    public ApplicationSubModule(ApplicationModule parentModule, String smPrefix)//e.g. PTWY
    {
    	setCode(parentModule.getCode()+"."+smPrefix);
    }
}
