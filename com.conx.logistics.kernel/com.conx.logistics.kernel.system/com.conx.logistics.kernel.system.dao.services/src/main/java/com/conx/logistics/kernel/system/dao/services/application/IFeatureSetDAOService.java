package com.conx.logistics.kernel.system.dao.services.application;

import java.util.List;

import com.conx.logistics.mdm.domain.application.FeatureSet;

public interface IFeatureSetDAOService {
	public List<FeatureSet> getFeatureSets();

	public FeatureSet addFeatureSet(FeatureSet app);

	public void deleteFeatureSet(FeatureSet app);

	public FeatureSet updateFeatureSet(FeatureSet app);

	public FeatureSet getFeatureSet(long id);
}
