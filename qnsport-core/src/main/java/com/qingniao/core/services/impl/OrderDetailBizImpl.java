package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.order.DetailMapper;
import com.qingniao.core.pojo.order.Detail;
import com.qingniao.core.pojo.order.DetailExample;
import com.qingniao.core.services.OrderDetailBiz;
@Service
@Transactional
public class OrderDetailBizImpl implements OrderDetailBiz {
	@Autowired
	DetailMapper detailMapper;
	@Override
	public List<Detail> getOrderDetailByOrderId(Long id) {
		DetailExample detailExample = new DetailExample();
		detailExample.createCriteria().andOrderIdEqualTo(id);
		List<Detail> details = detailMapper.selectByExample(detailExample);
		return details;
	}

}
