package com.zheng.ucenter.rpc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.zheng.upms.dao.mapper.UpmsUserMapper;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;

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
    public List<McGroup> getUserGroup(int userId) {
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcUserIdEqualTo(userId);
        List<McUserGroup> mcUserGroups = mcUserGroupMapper.selectByExample(userGroupExample);
        if (!CollectionUtils.isEmpty(mcUserGroups)) {
            ArrayList<McGroup> list = new ArrayList<>();
            for (McUserGroup mcUserGroup : mcUserGroups) {
                Integer mcGroupId = mcUserGroup.getMcGroupId();
                McGroup mcGroup = mcGroupMapper.selectByPrimaryKey(mcGroupId);
                list.add(mcGroup);
            }
            return list;
        }
        return null;
    }

    @Override
    public int group(String[] groupIds, int userId) {
        int result = 0;
        // 删除旧记录
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcUserIdEqualTo(userId);
        mcUserGroupMapper.deleteByExample(userGroupExample);
        // 增加新记录
        if (null != groupIds) {
            for (String groupId : groupIds) {
                if (StringUtils.isBlank(groupId)) {
                    continue;
                }
                McUserGroup userGroup = new McUserGroup();
                userGroup.setMcUserId(userId);
                userGroup.setMcGroupId(Integer.valueOf(groupId));
                result = mcUserGroupMapper.insertSelective(userGroup);
            }
        }
        return result;
    }

}