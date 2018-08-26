package com.zheng.upms.dao.mapper;

import com.zheng.upms.dao.model.McUserGroup;
import com.zheng.upms.dao.model.McUserGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McUserGroupMapper {
    long countByExample(McUserGroupExample example);

    int deleteByExample(McUserGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McUserGroup record);

    int insertSelective(McUserGroup record);

    List<McUserGroup> selectByExample(McUserGroupExample example);

    McUserGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McUserGroup record, @Param("example") McUserGroupExample example);

    int updateByExample(@Param("record") McUserGroup record, @Param("example") McUserGroupExample example);

    int updateByPrimaryKeySelective(McUserGroup record);

    int updateByPrimaryKey(McUserGroup record);
}