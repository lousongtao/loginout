package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McUserPosition;
import com.zheng.ucenter.dao.model.McUserPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McUserPositionMapper {
    long countByExample(McUserPositionExample example);

    int deleteByExample(McUserPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McUserPosition record);

    int insertSelective(McUserPosition record);

    List<McUserPosition> selectByExample(McUserPositionExample example);

    McUserPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McUserPosition record, @Param("example") McUserPositionExample example);

    int updateByExample(@Param("record") McUserPosition record, @Param("example") McUserPositionExample example);

    int updateByPrimaryKeySelective(McUserPosition record);

    int updateByPrimaryKey(McUserPosition record);
}