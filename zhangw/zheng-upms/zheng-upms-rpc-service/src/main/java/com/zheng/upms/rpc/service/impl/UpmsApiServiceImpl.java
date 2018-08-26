package com.zheng.upms.rpc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.dao.mapper.*;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;

/**
 * UpmsApiService实现
 * Created by shuzheng on 2016/01/19.
 */
@Service
@Transactional
public class UpmsApiServiceImpl implements UpmsApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    UpmsUserMapper              upmsUserMapper;

    @Autowired
    UpmsRoleMapper              upmsRoleMapper;
    @Autowired
    UpmsUserRoleMapper          upmsUserRoleMapper;

    @Autowired
    UpmsApiMapper               upmsApiMapper;

    @Autowired
    UpmsRolePermissionMapper    upmsRolePermissionMapper;

    @Autowired
    UpmsUserPermissionMapper    upmsUserPermissionMapper;

    @Autowired
    UpmsSystemMapper            upmsSystemMapper;

    @Autowired
    UpmsOrganizationMapper      upmsOrganizationMapper;

    @Autowired
    UpmsLogMapper               upmsLogMapper;

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            LOGGER.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsPermission> upmsPermissions = upmsApiMapper
            .selectUpmsPermissionByUpmsUserId(upmsUserId);
        DynamicDataSource.clearDataSource();
        return upmsPermissions;
    }

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    @Override
    @Cacheable(value = "zheng-upms-rpc-service-ehcache", key = "'selectUpmsPermissionByUpmsUserId_' + #upmsUserId")
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        List<UpmsPermission> upmsPermissions = selectUpmsPermissionByUpmsUserId(upmsUserId);
        DynamicDataSource.clearDataSource();
        return upmsPermissions;
    }

    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            LOGGER.info("selectUpmsRoleByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsRole> upmsRoles = upmsApiMapper.selectUpmsRoleByUpmsUserId(upmsUserId);
        DynamicDataSource.clearDataSource();
        return upmsRoles;
    }

    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    @Override
    @Cacheable(value = "zheng-upms-rpc-service-ehcache", key = "'selectUpmsRoleByUpmsUserId_' + #upmsUserId")
    public List<UpmsRole> selectUpmsRoleByUpmsUserIdByCache(Integer upmsUserId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        List<UpmsRole> upmsRoles = selectUpmsRoleByUpmsUserId(upmsUserId);
        DynamicDataSource.clearDataSource();
        return upmsRoles;
    }

    /**
     * 根据角色id获取所拥有的权限
     * @param upmsRoleId
     * @return
     */
    @Override
    public List<UpmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        UpmsRolePermissionExample upmsRolePermissionExample = new UpmsRolePermissionExample();
        upmsRolePermissionExample.createCriteria().andRoleIdEqualTo(upmsRoleId);
        List<UpmsRolePermission> upmsRolePermissions = upmsRolePermissionMapper
            .selectByExample(upmsRolePermissionExample);
        DynamicDataSource.clearDataSource();
        return upmsRolePermissions;
    }

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    @Override
    public List<UpmsUserPermission> selectUpmsUserPermissionByUpmsUserId(Integer upmsUserId) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        UpmsUserPermissionExample upmsUserPermissionExample = new UpmsUserPermissionExample();
        upmsUserPermissionExample.createCriteria().andUserIdEqualTo(upmsUserId);
        List<UpmsUserPermission> upmsUserPermissions = upmsUserPermissionMapper
            .selectByExample(upmsUserPermissionExample);
        DynamicDataSource.clearDataSource();
        return upmsUserPermissions;
    }

    /**
     * 根据条件获取系统数据
     * @param upmsSystemExample
     * @return
     */
    @Override
    public List<UpmsSystem> selectUpmsSystemByExample(UpmsSystemExample upmsSystemExample) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        List<UpmsSystem> upmsSystems = upmsSystemMapper.selectByExample(upmsSystemExample);
        DynamicDataSource.clearDataSource();
        return upmsSystems;
    }

    /**
     * 根据条件获取组织数据
     * @param upmsOrganizationExample
     * @return
     */
    @Override
    public List<UpmsOrganization> selectUpmsOrganizationByExample(UpmsOrganizationExample upmsOrganizationExample) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        List<UpmsOrganization> upmsOrganizations = upmsOrganizationMapper
            .selectByExample(upmsOrganizationExample);
        DynamicDataSource.clearDataSource();
        return upmsOrganizations;
    }

    /**
     * 根据username获取UpmsUser
     * @param username
     * @return
     */
    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andLoginnameEqualTo(username);
        List<UpmsUser> upmsUsers = upmsUserMapper.selectByExample(upmsUserExample);
        DynamicDataSource.clearDataSource();
        if (null != upmsUsers && upmsUsers.size() > 0) {
            return upmsUsers.get(0);
        }
        return null;
    }

    /**
     * 写入操作日志
     * @param record
     * @return
     */
    @Override
    public int insertUpmsLogSelective(UpmsLog record) {
        DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
        int result = upmsLogMapper.insertSelective(record);
        DynamicDataSource.clearDataSource();
        return result;
    }

    @Override
    public UpmsUser insertStaffInfo(UpmsUser upmsUser) {
        DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andLoginnameEqualTo(upmsUser.getLoginname());
        long count = upmsUserMapper.countByExample(upmsUserExample);
        if (count > 0) {
            return null;
        }
        int insert = upmsUserMapper.insert(upmsUser);
        if (insert > 0) {
            //默认给与员工角色
            UpmsRoleExample upmsRoleExample = new UpmsRoleExample();
            upmsRoleExample.createCriteria().andNameEqualTo("staff");
            List<UpmsRole> upmsRoles = upmsRoleMapper.selectByExample(upmsRoleExample);
            if (!CollectionUtils.isEmpty(upmsRoles)) {
                UpmsRole upmsRole = upmsRoles.get(0);
                Integer roleId = upmsRole.getRoleId();
                UpmsUserRole userRole = new UpmsUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(upmsUser.getUserId());
                upmsUserRoleMapper.insertSelective(userRole);
            }
        }
        DynamicDataSource.clearDataSource();
        return upmsUser;
    }

}