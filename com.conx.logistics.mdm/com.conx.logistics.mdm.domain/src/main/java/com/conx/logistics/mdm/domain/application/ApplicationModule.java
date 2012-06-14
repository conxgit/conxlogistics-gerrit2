package com.conx.logistics.mdm.domain.application;

import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.conx.logistics.mdm.domain.BaseEntity;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysapplicationModule")
public class ApplicationModule extends BaseEntity {
    @ManyToOne(targetEntity = Application.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Application parentApplication; 
    
    @OneToMany(targetEntity = ApplicationSubModule.class, mappedBy="parentModule", fetch = FetchType.EAGER)
    private List<ApplicationSubModule> subModules;     
    
    public ApplicationModule(Application parentApp, String ddModuduleCode)//e.g. RCV
    {
    	setCode(parentApp.getCode()+"."+ddModuduleCode);
    }
    
	public void setParentApplication(Application parentApplication) {
		this.parentApplication = parentApplication;
	}

	public List<ApplicationSubModule> getSubModules() {
		return subModules;
	}

	public void setSubModules(List<ApplicationSubModule> subModules) {
		this.subModules = subModules;
	}
}
