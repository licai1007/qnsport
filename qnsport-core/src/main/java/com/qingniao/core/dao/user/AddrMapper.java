package com.qingniao.core.dao.user;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.qingniao.core.pojo.user.Addr;
import com.qingniao.core.pojo.user.AddrExample;

public interface AddrMapper {
    int countByExample(AddrExample example);

    int deleteByExample(AddrExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Addr record);

    int insertSelective(Addr record);

    List<Addr> selectByExample(AddrExample example);

    Addr selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Addr record, @Param("example") AddrExample example);

    int updateByExample(@Param("record") Addr record, @Param("example") AddrExample example);

    int updateByPrimaryKeySelective(Addr record);

    int updateByPrimaryKey(Addr record);
}