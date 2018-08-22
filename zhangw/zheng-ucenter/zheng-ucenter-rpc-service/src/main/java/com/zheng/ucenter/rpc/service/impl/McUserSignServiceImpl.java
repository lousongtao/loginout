package com.zheng.ucenter.rpc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import com.zheng.ucenter.rpc.api.McUserSignService;
import com.zheng.ucenter.rpc.mapper.UcenterApiMapper;

/**
* McUserSignService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
public class McUserSignServiceImpl implements McUserSignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McUserSignServiceImpl.class);

    @Autowired
    UcenterApiMapper            ucenterApiMapper;

    @Override
    public int insertSignRecord(McUserSign mcUserSign, Integer parentId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            return ucenterApiMapper.insertSignRecord(mcUserSign, parentId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("insertSignRecord error");
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public List<McUserSign> selectSignRecordByExample(McUserSignExample example, Integer parentId,
                                                      Integer pageNum, Integer pageSize) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize, false);
            }
            List<McUserSign> mcUserSigns = ucenterApiMapper.selectSignRecordByExample(example,
                parentId);
            return mcUserSigns;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("selectSignRecordByExample error");
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public long countByExample(McUserSignExample example, Integer parentId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            long count = ucenterApiMapper.countSignRecordByExample(example, parentId);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("selectSignRecordByExample error");
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }
}