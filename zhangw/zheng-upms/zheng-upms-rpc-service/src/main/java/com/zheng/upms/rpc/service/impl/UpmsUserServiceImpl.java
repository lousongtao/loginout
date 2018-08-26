package com.zheng.upms.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.dao.mapper.UpmsRoleMapper;
import com.zheng.upms.dao.mapper.UpmsUserMapper;
import com.zheng.upms.dao.mapper.UpmsUserRoleMapper;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;

/**
* UpmsUserService实现
* Created by shuzheng on 2017/3/20.
*/
@Service
@Transactional
@BaseService
public class UpmsUserServiceImpl extends BaseServiceImpl<UpmsUserMapper, UpmsUser, UpmsUserExample>
                                 implements UpmsUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsUserServiceImpl.class);

    @Autowired
    UpmsUserMapper              upmsUserMapper;
    @Autowired
    UpmsApiMapper               upmsApiMapper;
    @Autowired
    UpmsRoleMapper              upmsRoleMapper;
    @Autowired
    UpmsUserRoleMapper          upmsUserRoleMapper;

    @Override
    public UpmsUser createUser(UpmsUser upmsUser, Boolean createTable) {
        DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andLoginnameEqualTo(upmsUser.getLoginname());
        long count = upmsUserMapper.countByExample(upmsUserExample);
        if (count > 0) {
            return null;
        }
        upmsUserMapper.insert(upmsUser);
        if (createTable) {
            upmsApiMapper.createScheduleTable(upmsUser.getUserId());
            upmsApiMapper.createSignTable(upmsUser.getUserId());
            LOGGER.info("Create ScheduleTable And SignTable Success");
        }
        DynamicDataSource.clearDataSource();
        return upmsUser;
    }

}