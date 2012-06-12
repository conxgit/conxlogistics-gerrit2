package com.conx.logistics.mdm.domain.application;

import java.util.List;

import javax.persistence.CascadeType;
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

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="sysfeature")
public class Feature extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;	
    
    @ManyToOne(targetEntity = Application.class, fetch = FetchType.EAGER)
    @JoinColumn
    protected Application parentApplication;    
    
    @ManyToOne(targetEntity = Feature.class, fetch = FetchType.EAGER)
    @JoinColumn
    protected Feature parentFeature;   
    
    @OneToMany(targetEntity = Feature.class, mappedBy="parentFeature", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn
    private List<Feature> childFeatures;  
    
    protected boolean featureSet = false;
    
    public Feature()
    {
    }
    
    public Feature(Application parentApplication, Feature parentFeature, String featureCode)
    {
    	setParentApplication(parentApplication);
    	setParentFeature(parentFeature);
    	if (Validator.isNotNull(parentFeature))
    		setCode(parentFeature.getCode()+"."+featureCode);
    	else
    	{
    		setCode(parentApplication.getCode()+"."+featureCode);
    		setFeatureSet(true);
    	}
    }
      
    
    public Feature(Application parentApplication, Feature parentFeature, String featureCode, boolean isFeatuteset)
    {
    	setParentApplication(parentApplication);
    	setParentFeature(parentFeature);
    	setCode(parentFeature.getCode()+"."+featureCode);
    	this.featureSet = isFeatuteset;
    }   
    
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Feature getParentFeature() {
		return parentFeature;
	}

	public void setParentFeature(Feature parentFeature) {
		this.parentFeature = parentFeature;
	}
	
	public Application getParentApplication() {
		return parentApplication;
	}

	public void setParentApplication(Application parentApplication) {
		this.parentApplication = parentApplication;
	} 
	
	public List<Feature> getChildFeatures() {
		return childFeatures;
	}

	public void setChildFeatures(List<Feature> childFeatures) {
		this.childFeatures = childFeatures;
	}

	public boolean isFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(boolean featureSet) {
		this.featureSet = featureSet;
	}   	
}
