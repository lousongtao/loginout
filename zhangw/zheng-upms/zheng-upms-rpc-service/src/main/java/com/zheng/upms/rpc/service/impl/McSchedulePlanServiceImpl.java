package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.dao.mapper.McSchedulePlanMapper;
import com.zheng.upms.dao.model.McSchedulePlan;
import com.zheng.upms.dao.model.McSchedulePlanExample;
import com.zheng.upms.dao.vo.McSchedulingCell;
import com.zheng.upms.rpc.api.McSchedulePlanService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
* McSchedulePlanService实现
* Created by shuzheng on 2018/8/26.
*/
@Service
@Transactional
@BaseService
public class McSchedulePlanServiceImpl extends BaseServiceImpl<McSchedulePlanMapper, McSchedulePlan, McSchedulePlanExample> implements McSchedulePlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McSchedulePlanServiceImpl.class);

    @Autowired
    McSchedulePlanMapper mcSchedulePlanMapper;
    @Autowired
    UpmsApiMapper upmsApiMapper;

    @Override
    public McSchedulePlan getCellDataById(Integer cellId, Integer currentUserId) {
        try {
            return upmsApiMapper.selectScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public int updateCellData(McSchedulePlan cell, Integer currentUserId) {
        int result = 0;
        try {
            cell.setUpdateTime(new Date());
            if (cell.getId() == null) {
                //todo 新增要校验当天是否已排班
                Integer getuId = cell.getuId();
                Date schedulingDate = cell.getSchedulingDate();
                List<McSchedulingCell> schedulingCells = upmsApiMapper
                        .selectStaffData(schedulingDate, schedulingDate, currentUserId, getuId);
                if (CollectionUtils.isEmpty(schedulingCells)) {
                    cell.setResult(0);
                    cell.setCreateTime(new Date());
                    result = upmsApiMapper.insertScheduleData(cell, currentUserId);
                }
            } else {
                result = upmsApiMapper.updateScheduleDataSelective(cell, currentUserId);
            }
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public int deleteCell(Integer cellId, Integer currentUserId) {
        int result = 0;
        try {
            result = upmsApiMapper.deleteScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId) {
        try {
            int size = 0;
            List<McSchedulingCell> mcSchedulePlans = upmsApiMapper
                    .selectSchedulingDataByDate(startDate, endDate, mcId);
            if (!CollectionUtils.isEmpty(mcSchedulePlans)) {
                size = mcSchedulePlans.size();
            }
            LOGGER.info("用户:" + mcId + "时间段:" + startDate.toString() + "---" + endDate.toString()
                    + "->" + size + " 条记录");
            return mcSchedulePlans;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<McSchedulingCell> selectDataByExample(McSchedulePlanExample example, Integer mcId) {
        try {
            List<McSchedulingCell> mcSchedulePlans = upmsApiMapper.selectDataByExample(example,
                    mcId);
            return mcSchedulePlans;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public int batchDeleteCell(String ids, Integer currentUserId) {
        try {
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            String[] idArray = ids.split("-");
            int result = upmsApiMapper.batchDeleteScheduleData(idArray, currentUserId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<McSchedulingCell> selectStaffData(Date startDate, Date endDate, Integer parentId,
                                                  Integer uId) {
        try {
            List<McSchedulingCell> mcSchedulePlans = upmsApiMapper.selectStaffData(startDate,
                    endDate, parentId, uId);
            return mcSchedulePlans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}