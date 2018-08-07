package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McCountWeek;
import com.zheng.ucenter.dao.model.McCountWeekExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McCountWeekMapper {
    long countByExample(McCountWeekExample example);

    int deleteByExample(McCountWeekExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McCountWeek record);

    int insertSelective(McCountWeek record);

    List<McCountWeek> selectByExample(McCountWeekExample example);

    McCountWeek selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McCountWeek record, @Param("example") McCountWeekExample example);

    int updateByExample(@Param("record") McCountWeek record, @Param("example") McCountWeekExample example);

    int updateByPrimaryKeySelective(McCountWeek record);

    int updateByPrimaryKey(McCountWeek record);
}