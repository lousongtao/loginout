package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McUserGroupMapper;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.dao.model.McUserGroupExample;

import java.util.List;

/**
* 降级实现McUserGroupService接口
* Created by shuzheng on 2018/7/16.
*/
public class McUserGroupServiceMock extends BaseServiceMock<McUserGroupMapper, McUserGroup, McUserGroupExample> implements McUserGroupService {

    @Override
    public List<Integer> getGroupUsers(Integer groupId) {
        return null;
    }

    @Override
    public McGroup getUserGroup(int userId) {
        return null;
    }

    @Override
    public void deleteAndSave(int userId, Integer groupId) {

    }
}
