package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.dao.mapper.McGroupMapper;
import com.zheng.upms.dao.mapper.McUserGroupMapper;
import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.McGroupExample;
import com.zheng.upms.dao.model.McUserGroup;
import com.zheng.upms.dao.model.McUserGroupExample;
import com.zheng.upms.rpc.api.McGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* McGroupService实现
* Created by shuzheng on 2018/8/26.
*/
@Service
@Transactional
@BaseService
public class McGroupServiceImpl extends BaseServiceImpl<McGroupMapper, McGroup, McGroupExample> implements McGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McGroupServiceImpl.class);

    @Autowired
    McGroupMapper mcGroupMapper;

    @Autowired
    McUserGroupMapper mcUserGroupMapper;

    @Override
    public int checkAndDeleteById(Integer groupId) {
        //先检查是否有成员在组内
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            McUserGroupExample mcUserGroupExample = new McUserGroupExample();
            mcUserGroupExample.createCriteria().andMcGroupIdEqualTo(groupId);
            List<McUserGroup> mcUserGroups = mcUserGroupMapper.selectByExample(mcUserGroupExample);
            if (CollectionUtils.isEmpty(mcUserGroups)) {
                return mcGroupMapper.deleteByPrimaryKey(groupId);
            }
        } catch (Exception e) {
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }
}