package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.product.FeatureMapper;
import com.qingniao.core.pojo.product.Feature;
import com.qingniao.core.pojo.product.FeatureExample;
import com.qingniao.core.services.FeatureBiz;
@Service
@Transactional
public class FeatureBizImpl implements FeatureBiz {
	@Autowired
	private FeatureMapper featureMapper;
	@Override
	public List<Feature> selectFeatureByFeatureExample(FeatureExample featureExample) {
		return featureMapper.selectByExample(featureExample);
	}

}
