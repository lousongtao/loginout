package com.zheng.upms.rpc.api;

import com.zheng.upms.dao.model.McUserSign;
import com.zheng.upms.dao.model.McUserSignExample;

import java.util.List;

/**
* McUserSignService接口
* Created by shuzheng on 2018/6/29.
*/
public interface McUserSignService {

    int insertSignRecord(McUserSign mcUserSign, Integer parentId);

    /**
     * 分页条件查询 签到数据
     * @param example
     * @param parentId
     * @param offset
     * @param limit
     * @return
     */
    List<McUserSign> selectSignRecordByExample(McUserSignExample example, Integer parentId,
                                               Integer offset, Integer limit);

    /**
     * 根据条件统计
     * @param example
     * @param parentId
     * @return
     */
    long countByExample(McUserSignExample example, Integer parentId);

    int deleteSignRecord(long signId, int parentId);

    int updateByPrimaryKeySelective(int parentId, McUserSign mcUserSign);
}