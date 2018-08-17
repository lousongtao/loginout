package com.zheng.ucenter.rpc.service.impl;

import java.util.Date;
import java.util.List;

import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.vo.McSchedulingCell;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;
import com.zheng.ucenter.rpc.mapper.UcenterApiMapper;

/**
* McSchedulePlanService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
public class McSchedulePlanServiceImpl implements McSchedulePlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McSchedulePlanServiceImpl.class);

    @Autowired
    UcenterApiMapper            ucenterApiMapper;

    @Override
    public McSchedulePlan getCellDataById(Integer cellId, Integer currentUserId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            return ucenterApiMapper.selectScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public int updateCellData(McSchedulePlan cell, Integer currentUserId) {
        int result = 0;
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            cell.setUpdateTime(new Date());
            if (cell.getId() == null) {
                //todo 新增要校验当天是否已排班
                Integer getuId = cell.getuId();
                Date schedulingDate = cell.getSchedulingDate();
                List<McSchedulingCell> schedulingCells = ucenterApiMapper
                    .selectStaffData(schedulingDate, schedulingDate, currentUserId, getuId);
                if (CollectionUtils.isEmpty(schedulingCells)) {
                    cell.setResult(0);
                    cell.setCreateTime(new Date());
                    result = ucenterApiMapper.insertScheduleData(cell, currentUserId);
                }
            } else {
                result = ucenterApiMapper.updateScheduleDataSelective(cell, currentUserId);
            }
        } catch (Exception e) {

        }
        DynamicDataSource.clearDataSource();
        return result;
    }

    @Override
    public int deleteCell(Integer cellId, Integer currentUserId) {
        int result = 0;
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            result = ucenterApiMapper.deleteScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {

        }
        DynamicDataSource.clearDataSource();
        return result;
    }

    @Override
    public List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            int size = 0;
            List<McSchedulingCell> mcSchedulePlans = ucenterApiMapper
                .selectSchedulingDataByDate(startDate, endDate, mcId);
            if (!CollectionUtils.isEmpty(mcSchedulePlans)) {
                size = mcSchedulePlans.size();
            }
            LOGGER.info("用户:" + mcId + "时间段:" + startDate.toString() + "---" + endDate.toString()
                        + "->" + size + " 条记录");
            return mcSchedulePlans;
        } catch (Exception e) {

        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<McSchedulingCell> selectDataByExample(McSchedulePlanExample example, Integer mcId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            List<McSchedulingCell> mcSchedulePlans = ucenterApiMapper.selectDataByExample(example,
                mcId);
            return mcSchedulePlans;
        } catch (Exception e) {

        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public int batchDeleteCell(String ids, Integer currentUserId) {
        try {
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            String[] idArray = ids.split("-");
            int result = ucenterApiMapper.batchDeleteScheduleData(idArray, currentUserId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public List<McSchedulingCell> selectStaffData(Date startDate, Date endDate, Integer parentId,
                                                  Integer uId) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            List<McSchedulingCell> mcSchedulePlans = ucenterApiMapper.selectStaffData(startDate,
                endDate, parentId, uId);
            return mcSchedulePlans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }
}