package com.zheng.upms.dao.mapper;

import com.zheng.upms.dao.model.McScheduleplanTemplatedetail;
import com.zheng.upms.dao.model.McScheduleplanTemplatedetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McScheduleplanTemplatedetailMapper {
    long countByExample(McScheduleplanTemplatedetailExample example);

    int deleteByExample(McScheduleplanTemplatedetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McScheduleplanTemplatedetail record);

    int insertSelective(McScheduleplanTemplatedetail record);

    List<McScheduleplanTemplatedetail> selectByExample(McScheduleplanTemplatedetailExample example);

    McScheduleplanTemplatedetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McScheduleplanTemplatedetail record, @Param("example") McScheduleplanTemplatedetailExample example);

    int updateByExample(@Param("record") McScheduleplanTemplatedetail record, @Param("example") McScheduleplanTemplatedetailExample example);

    int updateByPrimaryKeySelective(McScheduleplanTemplatedetail record);

    int updateByPrimaryKey(McScheduleplanTemplatedetail record);
}