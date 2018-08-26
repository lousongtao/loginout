package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McUserGroupMapper;
import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.McUserGroup;
import com.zheng.upms.dao.model.McUserGroupExample;

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
    public List<McGroup> getUserGroup(int userId) {
        return null;
    }

    @Override
    public int group(String[] groupIds, int userId) {
        return 0;
    }

}
