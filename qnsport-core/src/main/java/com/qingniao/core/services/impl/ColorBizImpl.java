package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.product.ColorMapper;
import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.ColorExample;
import com.qingniao.core.services.ColorBiz;
@Service
@Transactional
public class ColorBizImpl implements ColorBiz {
	@Autowired
	private ColorMapper colorMapper;
	@Override
	public List<Color> selectColorByColorExample(ColorExample colorExample) {
		return colorMapper.selectByExample(colorExample);
	}

}
