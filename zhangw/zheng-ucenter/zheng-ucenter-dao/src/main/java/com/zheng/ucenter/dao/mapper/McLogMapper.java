package com.zheng.ucenter.dao.mapper;

import com.zheng.ucenter.dao.model.McLog;
import com.zheng.ucenter.dao.model.McLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface McLogMapper {
    long countByExample(McLogExample example);

    int deleteByExample(McLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(McLog record);

    int insertSelective(McLog record);

    List<McLog> selectByExampleWithBLOBs(McLogExample example);

    List<McLog> selectByExample(McLogExample example);

    McLog selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") McLog record, @Param("example") McLogExample example);

    int updateByExampleWithBLOBs(@Param("record") McLog record, @Param("example") McLogExample example);

    int updateByExample(@Param("record") McLog record, @Param("example") McLogExample example);

    int updateByPrimaryKeySelective(McLog record);

    int updateByPrimaryKeyWithBLOBs(McLog record);

    int updateByPrimaryKey(McLog record);
}