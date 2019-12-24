package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.ColorExample;

public interface ColorBiz {
	public List<Color> selectColorByColorExample(ColorExample colorExample);
}
