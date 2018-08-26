package com.zheng.upms.dao.mapper;

import com.zheng.upms.dao.model.McCountDay;
import com.zheng.upms.dao.model.McCountDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McCountDayMapper {
    long countByExample(McCountDayExample example);

    int deleteByExample(McCountDayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McCountDay record);

    int insertSelective(McCountDay record);

    List<McCountDay> selectByExample(McCountDayExample example);

    McCountDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McCountDay record, @Param("example") McCountDayExample example);

    int updateByExample(@Param("record") McCountDay record, @Param("example") McCountDayExample example);

    int updateByPrimaryKeySelective(McCountDay record);

    int updateByPrimaryKey(McCountDay record);
}