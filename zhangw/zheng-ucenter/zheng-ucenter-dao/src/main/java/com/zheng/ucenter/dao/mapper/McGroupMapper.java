package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McGroupMapper {
    long countByExample(McGroupExample example);

    int deleteByExample(McGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McGroup record);

    int insertSelective(McGroup record);

    List<McGroup> selectByExample(McGroupExample example);

    McGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McGroup record, @Param("example") McGroupExample example);

    int updateByExample(@Param("record") McGroup record, @Param("example") McGroupExample example);

    int updateByPrimaryKeySelective(McGroup record);

    int updateByPrimaryKey(McGroup record);
}