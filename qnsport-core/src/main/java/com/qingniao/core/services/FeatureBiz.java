package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.product.Feature;
import com.qingniao.core.pojo.product.FeatureExample;

public interface FeatureBiz {
	public List<Feature> selectFeatureByFeatureExample(FeatureExample featureExample);
}
