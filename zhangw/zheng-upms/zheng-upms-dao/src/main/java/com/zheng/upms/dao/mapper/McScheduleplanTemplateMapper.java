package com.zheng.upms.dao.mapper;

import com.zheng.upms.dao.model.McScheduleplanTemplate;
import com.zheng.upms.dao.model.McScheduleplanTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McScheduleplanTemplateMapper {
    long countByExample(McScheduleplanTemplateExample example);

    int deleteByExample(McScheduleplanTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McScheduleplanTemplate record);

    int insertSelective(McScheduleplanTemplate record);

    List<McScheduleplanTemplate> selectByExample(McScheduleplanTemplateExample example);

    McScheduleplanTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McScheduleplanTemplate record, @Param("example") McScheduleplanTemplateExample example);

    int updateByExample(@Param("record") McScheduleplanTemplate record, @Param("example") McScheduleplanTemplateExample example);

    int updateByPrimaryKeySelective(McScheduleplanTemplate record);

    int updateByPrimaryKey(McScheduleplanTemplate record);
}