package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.user.AddrMapper;
import com.qingniao.core.pojo.user.Addr;
import com.qingniao.core.pojo.user.AddrExample;
import com.qingniao.core.services.AddrBiz;
@Service
@Transactional
public class AddrBizImpl implements AddrBiz {
	@Autowired
	AddrMapper addrMapper;
	@Override
	public Addr selectAddrByUsername(String username) {
		AddrExample addrExample = new AddrExample();
		addrExample.createCriteria().andUserIdEqualTo(username);
		List<Addr> addrs = addrMapper.selectByExample(addrExample);
		return addrs.get(0);
	}

}
