package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.product.Type;
import com.qingniao.core.pojo.product.TypeExample;

public interface TypeBiz {
	public List<Type> selectTypeByTypeExample(TypeExample typeExample);
}
