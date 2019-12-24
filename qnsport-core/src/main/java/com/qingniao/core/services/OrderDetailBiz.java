package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.order.Detail;

public interface OrderDetailBiz {

	List<Detail> getOrderDetailByOrderId(Long id);

}
