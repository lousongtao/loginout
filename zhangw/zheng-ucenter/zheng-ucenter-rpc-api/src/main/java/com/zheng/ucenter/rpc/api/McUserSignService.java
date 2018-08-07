package com.zheng.ucenter.rpc.api;

import java.util.List;

import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;

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
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<McUserSign> selectSignRecordByExample(McUserSignExample example, Integer parentId,
                                               Integer pageNum, Integer pageSize);

    /**
     * 根据条件统计
     * @param example
     * @param parentId
     * @return
     */
    long countByExample(McUserSignExample example, Integer parentId);
}