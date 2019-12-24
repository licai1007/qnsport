package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.product.TypeMapper;
import com.qingniao.core.pojo.product.Type;
import com.qingniao.core.pojo.product.TypeExample;
import com.qingniao.core.services.TypeBiz;
@Service
@Transactional
public class TypeBizImpl implements TypeBiz {
	@Autowired
	private TypeMapper typeMapper;
	@Override
	public List<Type> selectTypeByTypeExample(TypeExample typeExample) {
		return typeMapper.selectByExample(typeExample);
	}

}
