package com.zheng.upms.rpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.dao.model.McUserSign;
import com.zheng.upms.dao.model.McUserSignExample;
import com.zheng.upms.rpc.api.McUserSignService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* McUserSignService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
public class McUserSignServiceImpl implements McUserSignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McUserSignServiceImpl.class);

    @Autowired
    UpmsApiMapper upmsApiMapper;

    @Override
    public int insertSignRecord(McUserSign mcUserSign, Integer parentId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            return upmsApiMapper.insertSignRecord(mcUserSign, parentId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("insertSignRecord error");
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public List<McUserSign> selectSignRecordByExample(McUserSignExample example, Integer parentId,
                                                      Integer offset, Integer limit) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            if (offset != null && limit != null) {
                PageHelper.offsetPage(offset, limit, false);
            }
            List<McUserSign> mcUserSigns = upmsApiMapper.selectSignRecordByExample(example,
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
            long count = upmsApiMapper.countSignRecordByExample(example, parentId);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("selectSignRecordByExample error");
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }
}