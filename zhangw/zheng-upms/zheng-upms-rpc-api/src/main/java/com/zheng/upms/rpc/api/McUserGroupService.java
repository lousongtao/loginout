package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.McUserGroup;
import com.zheng.upms.dao.model.McUserGroupExample;

import java.util.List;

/**
* McUserGroupService接口
* Created by shuzheng on 2018/7/16.
*/
public interface McUserGroupService extends BaseService<McUserGroup, McUserGroupExample> {

    /**
     * 获取组内的用户id
     * @param groupId
     * @return
     */
    List<Integer> getGroupUsers(Integer groupId);

    /**
     * 根据用户id 查询组
     * @param userId
     * @return
     */
    List<McGroup> getUserGroup(int userId);

    /**
     * 用户分组
     * @param groupIds 组ids
     * @param userId 用户id
     * @return
     */
    int group(String[] groupIds, int userId);

}