package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.McGroupExample;

/**
* McGroupService接口
* Created by shuzheng on 2018/8/26.
*/
public interface McGroupService extends BaseService<McGroup, McGroupExample> {
    int checkAndDeleteById(Integer groupId);
}