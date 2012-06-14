package com.conx.logistics.mdm.domain.application;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class FeatureSet extends Feature {
    public FeatureSet()
    {
    }
    
    public FeatureSet(Application parentApp, String featureSetCode) {
    	setParentApplication(parentApp);
		setCode(parentApp.getCode()+"."+featureSetCode);
	}    
}
