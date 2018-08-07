package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McUserSignMapper {
    long countByExample(McUserSignExample example);

    int deleteByExample(McUserSignExample example);

    int deleteByPrimaryKey(Long signId);

    int insert(McUserSign record);

    int insertSelective(McUserSign record);

    List<McUserSign> selectByExample(McUserSignExample example);

    McUserSign selectByPrimaryKey(Long signId);

    int updateByExampleSelective(@Param("record") McUserSign record, @Param("example") McUserSignExample example);

    int updateByExample(@Param("record") McUserSign record, @Param("example") McUserSignExample example);

    int updateByPrimaryKeySelective(McUserSign record);

    int updateByPrimaryKey(McUserSign record);
}