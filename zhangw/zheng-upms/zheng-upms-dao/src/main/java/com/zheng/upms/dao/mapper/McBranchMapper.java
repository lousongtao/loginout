package com.zheng.upms.dao.mapper;

import com.zheng.upms.dao.model.McBranch;
import com.zheng.upms.dao.model.McBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McBranchMapper {
    long countByExample(McBranchExample example);

    int deleteByExample(McBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(McBranch record);

    int insertSelective(McBranch record);

    List<McBranch> selectByExample(McBranchExample example);

    McBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") McBranch record, @Param("example") McBranchExample example);

    int updateByExample(@Param("record") McBranch record, @Param("example") McBranchExample example);

    int updateByPrimaryKeySelective(McBranch record);

    int updateByPrimaryKey(McBranch record);
}