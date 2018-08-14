package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;

/**
* McGroupService接口
* Created by shuzheng on 2018/7/16.
*/
public interface McGroupService extends BaseService<McGroup, McGroupExample> {

    int checkAndDeleteById(Integer groupId);
}