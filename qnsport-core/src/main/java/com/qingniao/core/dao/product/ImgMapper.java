package com.qingniao.core.dao.product;

import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.pojo.product.ImgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImgMapper {
    int countByExample(ImgExample example);

    int deleteByExample(ImgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Img record);

    int insertSelective(Img record);

    List<Img> selectByExample(ImgExample example);

    Img selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByExample(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByPrimaryKeySelective(Img record);

    int updateByPrimaryKey(Img record);
}