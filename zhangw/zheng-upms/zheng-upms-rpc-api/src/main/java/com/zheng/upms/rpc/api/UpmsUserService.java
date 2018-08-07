package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;

/**
* UpmsUserService接口
* Created by shuzheng on 2017/3/20.
*/
public interface UpmsUserService extends BaseService<UpmsUser, UpmsUserExample> {

    /**
     * 创建用户
     * @param upmsUser
     * @param createTable 是否创建相关数据表
     * @return
     */
    UpmsUser createUser(UpmsUser upmsUser,Boolean createTable);

}