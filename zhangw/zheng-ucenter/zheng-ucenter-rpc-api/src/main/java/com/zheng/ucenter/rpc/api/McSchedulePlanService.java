package com.zheng.ucenter.rpc.api;

import java.util.Date;
import java.util.List;

import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.vo.McSchedulingCell;

/**
* McSchedulePlanService接口
* Created by shuzheng on 2018/6/29.
*/
public interface McSchedulePlanService {
    /**
     * 获取单元格数据
     * @param cellId 单元格主键ID
     * @param currentUserId 当前操作人ID
     * @return
     */
    McSchedulePlan getCellDataById(Integer cellId, Integer currentUserId);

    /**
     * 更新单元格数据 新增,修改
     * @param cell
     * @param currentUserId
     * @return
     */
    int updateCellData(McSchedulePlan cell, Integer currentUserId);

    /**
     * 删除单元格数据
     * @param cellId
     * @param currentUserId
     * @return
     */
    int deleteCell(Integer cellId,Integer currentUserId);

    /**
     * 根据起止时间查询
     * @param startDate
     * @param endDate
     * @param mcId
     * @return
     */
    List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId);

    /**
     * 批量删除单元格数据
     * @param ids
     * @param currentUserId
     * @return
     */
    int batchDeleteCell(String ids, Integer currentUserId);

    /**
     * 员工查看自己的排班数据
     * @param startDate
     * @param endDate
     * @param parentId
     * @param uId
     * @return
     */
    List<McSchedulingCell> selectStaffData(Date startDate, Date endDate, Integer parentId,Integer uId);

}