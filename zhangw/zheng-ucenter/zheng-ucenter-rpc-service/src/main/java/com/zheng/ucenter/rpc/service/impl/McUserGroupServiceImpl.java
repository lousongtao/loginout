package com.zheng.ucenter.rpc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McGroupMapper;
import com.zheng.ucenter.dao.mapper.McUserGroupMapper;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.dao.model.McUserGroupExample;
import com.zheng.ucenter.rpc.api.McUserGroupService;

/**
* McUserGroupService实现
* Created by shuzheng on 2018/7/16.
*/
@Service
@Transactional
@BaseService
public class McUserGroupServiceImpl extends
                                    BaseServiceImpl<McUserGroupMapper, McUserGroup, McUserGroupExample>
                                    implements McUserGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McUserGroupServiceImpl.class);

    @Autowired
    McUserGroupMapper           mcUserGroupMapper;

    @Autowired
    McGroupMapper               mcGroupMapper;

    @Override
    public List<Integer> getGroupUsers(Integer groupId) {
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcGroupIdEqualTo(groupId);
        List<McUserGroup> mcUserGroups = mcUserGroupMapper.selectByExample(userGroupExample);
        if (!CollectionUtils.isEmpty(mcUserGroups)) {
            ArrayList<Integer> userList = new ArrayList<>();
            for (McUserGroup userGroup : mcUserGroups) {
                Integer mcUserId = userGroup.getMcUserId();
                userList.add(mcUserId);
            }
            return userList;
        }
        return null;
    }

    @Override
    public McGroup getUserGroup(int userId) {
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcUserIdEqualTo(userId);
        List<McUserGroup> mcUserGroups = mcUserGroupMapper.selectByExample(userGroupExample);
        if (!CollectionUtils.isEmpty(mcUserGroups)) {
            Integer mcGroupId = mcUserGroups.get(0).getMcGroupId();
            return mcGroupMapper.selectByPrimaryKey(mcGroupId);
        }
        return null;
    }

    @Override
    public void deleteAndSave(int userId, Integer groupId) {
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcUserIdEqualTo(userId);
        mcUserGroupMapper.deleteByExample(userGroupExample);
        McUserGroup userGroup = new McUserGroup();
        userGroup.setMcUserId(userId);
        userGroup.setMcGroupId(groupId);
        mcUserGroupMapper.insert(userGroup);
    }
}