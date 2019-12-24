package com.qingniao.core.services;

import com.qingniao.core.pojo.user.Addr;

public interface AddrBiz {
	Addr selectAddrByUsername(String username);
}
