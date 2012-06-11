package com.conx.logistics.kernel.system.dao.services.application;

import java.util.List;

import com.conx.logistics.mdm.domain.application.Feature;

public interface IFeatureDAOService {
	public List<Feature> getFeatures();

	public Feature addFeature(Feature app);

	public void deleteFeature(Feature app);

	public Feature updateFeature(Feature app);

	public Feature getFeature(long id);
}
