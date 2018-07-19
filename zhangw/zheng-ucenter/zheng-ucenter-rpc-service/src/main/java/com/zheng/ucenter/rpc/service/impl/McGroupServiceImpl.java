package com.zheng.ucenter.rpc.service.impl;

import com.zheng.ucenter.dao.model.McUserGroupExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McGroupMapper;
import com.zheng.ucenter.dao.mapper.McUserGroupMapper;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.rpc.api.McGroupService;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* McGroupService实现
* Created by shuzheng on 2018/7/16.
*/
@Service
@Transactional
@BaseService
public class McGroupServiceImpl extends BaseServiceImpl<McGroupMapper, McGroup, McGroupExample>
                                implements McGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McGroupServiceImpl.class);

    @Autowired
    McGroupMapper               mcGroupMapper;

    @Autowired
    McUserGroupMapper           mcUserGroupMapper;

    @Override
    public int createOrUpdateGroup(McGroup mcGroup, String staffIds) {
        int groupId;
        if (mcGroup.getId() == null) {
            //新增
            groupId = mcGroupMapper.insert(mcGroup);
        } else {
            //修改
            mcGroupMapper.updateByPrimaryKeySelective(mcGroup);
            groupId = mcGroup.getId();
        }
        McUserGroupExample mcUserGroupExample = new McUserGroupExample();
        mcUserGroupExample.createCriteria().andMcGroupIdEqualTo(groupId);
        mcUserGroupMapper.deleteByExample(mcUserGroupExample);
        if (!StringUtils.isBlank(staffIds)) {
            String[] staffId = staffIds.split("|");
            for (String id : staffId) {
                if (StringUtils.isBlank(id)) {
                    continue;
                }
                McUserGroup userGroup = new McUserGroup();
                userGroup.setMcGroupId(groupId);
                userGroup.setMcUserId(Integer.valueOf(id));
                mcUserGroupMapper.insert(userGroup);
            }
        }
        return groupId;
    }

    @Override
    public int checkAndDeleteById(Integer groupId) {
        //先检查是否有成员在组内
        McUserGroupExample mcUserGroupExample = new McUserGroupExample();
        mcUserGroupExample.createCriteria().andMcGroupIdEqualTo(groupId);
        List<McUserGroup> mcUserGroups = mcUserGroupMapper.selectByExample(mcUserGroupExample);
        if(CollectionUtils.isEmpty(mcUserGroups)){
            return mcGroupMapper.deleteByPrimaryKey(groupId);
        }
        return 0;
    }
}