package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McSchedulePlanMapper;
import com.zheng.upms.dao.model.McSchedulePlan;
import com.zheng.upms.dao.model.McSchedulePlanExample;
import com.zheng.upms.dao.vo.McSchedulingCell;

import java.util.Date;
import java.util.List;

/**
* 降级实现McSchedulePlanService接口
* Created by shuzheng on 2018/8/26.
*/
public class McSchedulePlanServiceMock extends BaseServiceMock<McSchedulePlanMapper, McSchedulePlan, McSchedulePlanExample> implements McSchedulePlanService {

    @Override
    public McSchedulePlan getCellDataById(Integer cellId, Integer currentUserId) {
        return null;
    }

    @Override
    public int updateCellData(McSchedulePlan cell, Integer currentUserId) {
        return 0;
    }

    @Override
    public int deleteCell(Integer cellId, Integer currentUserId) {
        return 0;
    }

    @Override
    public int deleteScheduleDataByDate(Date startDate, Date endDate, Integer userId, int branchId) {
        return 0;
    }

    @Override
    public int loadScheduleFromTemplate(Date startDate, Date endDate, Integer userId, int branchId, int templateId) {
        return 0;
    }

    @Override
    public List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId, int branchId) {
        return null;
    }

    @Override
    public List<McSchedulingCell> selectDataByExample(McSchedulePlanExample example, Integer mcId) {
        return null;
    }

    @Override
    public int batchDeleteCell(String ids, Integer currentUserId) {
        return 0;
    }

    @Override
    public List<McSchedulingCell> selectStaffData(Date startDate, Date endDate, Integer parentId, Integer uId) {
        return null;
    }
}
