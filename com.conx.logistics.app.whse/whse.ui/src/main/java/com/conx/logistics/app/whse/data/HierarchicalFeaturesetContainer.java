package com.conx.logistics.app.whse.data;

import com.conx.logistics.mdm.domain.application.FeatureSet;
import com.vaadin.addon.jpacontainer.JPAContainer;


public class HierarchicalFeaturesetContainer extends JPAContainer<FeatureSet> {
	public HierarchicalFeaturesetContainer()
	{
		super(FeatureSet.class);
		setParentProperty("parentFeatureset");
	}
}
