package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McShift;
import com.zheng.ucenter.dao.model.McShiftExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McShiftMapper {
    long countByExample(McShiftExample example);

    int deleteByExample(McShiftExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McShift record);

    int insertSelective(McShift record);

    List<McShift> selectByExample(McShiftExample example);

    McShift selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McShift record, @Param("example") McShiftExample example);

    int updateByExample(@Param("record") McShift record, @Param("example") McShiftExample example);

    int updateByPrimaryKeySelective(McShift record);

    int updateByPrimaryKey(McShift record);
}