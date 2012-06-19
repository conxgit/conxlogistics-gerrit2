package com.conx.logistics.mdm.domain.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.conx.logistics.mdm.domain.BaseEntity;

@Entity
@Table(name="sysapplication")
public class Application extends BaseEntity {
	
    private String themeIconPath;
    

    @OneToMany(targetEntity = Feature.class, mappedBy="parentApplication", cascade=CascadeType.ALL)
    private List<Feature> features = new ArrayList<Feature>();  

    public Application()
    {
    }
    
    public Application(String ddAppPrefix)//e.g. APP.WHSE
    {
    	setCode(ddAppPrefix);
    }

	public String getThemeIconPath() {
		return themeIconPath;
	}

	public void setThemeIconPath(String themeIconPath) {
		this.themeIconPath = themeIconPath;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
}
