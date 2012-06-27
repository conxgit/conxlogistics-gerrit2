package com.conx.logistics.app.whse.data;

import javax.persistence.EntityManager;

import com.conx.logistics.mdm.domain.application.Feature;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;


public class HierarchicalFeatureContainer extends JPAContainer<Feature> {
	public HierarchicalFeatureContainer(EntityManager kernelSystemEntityManager)
	{
		super(Feature.class);
		setEntityProvider(new CachingLocalEntityProvider<Feature>(
				Feature.class,
				kernelSystemEntityManager));		
		//setParentProperty("parentFeatureset");
		setParentProperty("parentFeature");
		addNestedContainerProperty("parentApplication.id");
	}
	
	@Override
	public boolean areChildrenAllowed(Object itemId) {
		if (itemId instanceof Long)
		{
			EntityItem<Feature> item = getItem(itemId);
			Feature entity = item.getEntity();
			return entity.isFeatureSet() && entity.getChildFeatures().size() > 0;
		}
		else
		{
			EntityItem<Feature> item = (EntityItem<Feature>)itemId;//Must have come from tree
			Feature entity = item.getEntity();
			return entity.isFeatureSet() && entity.getChildFeatures().size() > 0;
		}
	}	
}
