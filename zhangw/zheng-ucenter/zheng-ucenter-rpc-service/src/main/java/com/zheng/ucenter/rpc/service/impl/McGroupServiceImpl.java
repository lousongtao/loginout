package com.zheng.ucenter.rpc.service.impl;

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
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.ucenter.dao.mapper.McGroupMapper;
import com.zheng.ucenter.dao.mapper.McUserGroupMapper;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.dao.model.McUserGroupExample;
import com.zheng.ucenter.rpc.api.McGroupService;

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