package com.conx.logistics.mdm.domain.application;

import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="sysfeature")
public class Feature extends BaseEntity {
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn
    protected Application parentApplication;    
    
    @ManyToOne(targetEntity = Feature.class)
    @JoinColumn
    protected Feature parentFeature;   
    
    @OneToOne(targetEntity = Feature.class)
    @JoinColumn
    protected Feature onCompletionFeature;     
    
    @OneToMany(targetEntity = Feature.class, mappedBy="parentFeature", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Feature> childFeatures= new java.util.HashSet<Feature>();  
    
    protected boolean featureSet = false;
    
    protected boolean taskFeature;
    
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
	
	public Set<Feature> getChildFeatures() {
		return childFeatures;
	}

	public void setChildFeatures(Set<Feature> childFeatures) {
		this.childFeatures = childFeatures;
	}

	public boolean isFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(boolean featureSet) {
		this.featureSet = featureSet;
	}

	public boolean isTaskFeature() {
		return taskFeature;
	}

	public void setTaskFeature(boolean taskFeature) {
		this.taskFeature = taskFeature;
	}

	public Feature getOnCompletionFeature() {
		return onCompletionFeature;
	}

	public void setOnCompletionFeature(Feature onCompletionFeature) {
		this.onCompletionFeature = onCompletionFeature;
	}   
}
