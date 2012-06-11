package com.conx.logistics.kernel.system.data;

import com.conx.logistics.mdm.domain.application.FeatureSet;
import com.vaadin.addon.jpacontainer.JPAContainer;


public class HierarchicalFeaturesetContainer extends JPAContainer<FeatureSet> {
	public HierarchicalFeaturesetContainer()
	{
		super(FeatureSet.class);
		setParentProperty("parentFeatureset");
	}
}
