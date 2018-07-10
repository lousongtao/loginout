package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McPosition;
import com.zheng.ucenter.dao.model.McPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McPositionMapper {
    long countByExample(McPositionExample example);

    int deleteByExample(McPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McPosition record);

    int insertSelective(McPosition record);

    List<McPosition> selectByExample(McPositionExample example);

    McPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McPosition record, @Param("example") McPositionExample example);

    int updateByExample(@Param("record") McPosition record, @Param("example") McPositionExample example);

    int updateByPrimaryKeySelective(McPosition record);

    int updateByPrimaryKey(McPosition record);
}