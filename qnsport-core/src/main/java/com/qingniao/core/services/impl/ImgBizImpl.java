package com.qingniao.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.product.ImgMapper;
import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.services.ImgBiz;
@Service
@Transactional
public class ImgBizImpl implements ImgBiz {
	@Autowired
	private ImgMapper imgMapper;
	@Override
	public int insertSelective(Img record) {
		return imgMapper.insertSelective(record);
	}

}
