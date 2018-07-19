package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.dao.model.McUserGroupExample;
import com.zheng.upms.dao.model.UpmsUser;

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
    McGroup getUserGroup(int userId);

    /**
     * 删除并保存
     * @param userId
     * @param groupId
     */
    void deleteAndSave(int userId, Integer groupId);
}