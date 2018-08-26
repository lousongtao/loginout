package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.upms.dao.model.McSchedulePlan;
import com.zheng.upms.dao.model.McSchedulePlanExample;
import com.zheng.upms.dao.vo.McSchedulingCell;

import java.util.Date;
import java.util.List;

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
     * 条件查询
     * @param example
     * @param mcId
     * @return
     */
    List<McSchedulingCell> selectDataByExample(McSchedulePlanExample example, Integer mcId);

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