package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McSchedulePlanMapper {
    long countByExample(McSchedulePlanExample example);

    int deleteByExample(McSchedulePlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McSchedulePlan record);

    int insertSelective(McSchedulePlan record);

    List<McSchedulePlan> selectByExample(McSchedulePlanExample example);

    McSchedulePlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McSchedulePlan record, @Param("example") McSchedulePlanExample example);

    int updateByExample(@Param("record") McSchedulePlan record, @Param("example") McSchedulePlanExample example);

    int updateByPrimaryKeySelective(McSchedulePlan record);

    int updateByPrimaryKey(McSchedulePlan record);
}