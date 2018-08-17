package com.zheng.ucenter.rpc.api;

import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import com.zheng.ucenter.dao.vo.McSchedulingCell;

import java.util.Date;
import java.util.List;

/**
* 降级实现McSchedulePlanService接口
* Created by shuzheng on 2018/6/29.
*/
public class McSchedulePlanServiceMock implements McSchedulePlanService {

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
    public List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId) {
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
