package com.zheng.ucenter.rpc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import com.zheng.ucenter.dao.vo.McSchedulingCell;

/**
 * Created by zw on 2018/7/26
 */
public interface UcenterApiMapper {

    /**
     * 插入排班数据
     * @param record
     * @param mcId
     * @return
     */
    int insertScheduleData(@Param("record") McSchedulePlan record, @Param("mcId") Integer mcId);

    /**
     * 修改排班数据
     * @param record
     * @param mcId
     * @return
     */
    int updateScheduleDataSelective(@Param("record") McSchedulePlan record,
                                    @Param("mcId") Integer mcId);

    /**
     * 删除排班数据
     * @param id
     * @param mcId
     * @return
     */
    int deleteScheduleDataByPrimaryKey(@Param("id") Integer id, @Param("mcId") Integer mcId);

    /**
     * 批量删除单元格数据
     * @param idArray
     * @param mcId
     * @return
     */
    int batchDeleteScheduleData(@Param("idArray") String[] idArray, @Param("mcId") Integer mcId);

    /**
     * 查询某条排班数据
     * @param id
     * @param mcId
     * @return
     */
    McSchedulePlan selectScheduleDataByPrimaryKey(@Param("id") Integer id,
                                                  @Param("mcId") Integer mcId);

    /**
     * 根据日期查询排班数据,
     * @param startDate 起始日期
     * @param endDate 结束过期
     * @param mcId 操作人ID
     * @return
     */
    List<McSchedulingCell> selectSchedulingDataByDate(@Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate,
                                                      @Param("mcId") Integer mcId);

    /**
     * 条件查询,补充 selectSchedulingDataByDate 方法
     * @param example
     * @param mcId
     * @return
     */
    List<McSchedulingCell> selectDataByExample(@Param("example") McSchedulePlanExample example,
                                               @Param("mcId") Integer mcId);

    /**
     * 员工查看自己的排班数据
     * @param startDate
     * @param endDate
     * @param parentId
     * @param uId
     * @return
     */
    List<McSchedulingCell> selectStaffData(@Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate,
                                           @Param("parentId") Integer parentId,
                                           @Param("uId") Integer uId);

    /**
     * 员工签到
     * @param record
     * @param parentId
     * @return
     */
    int insertSignRecord(@Param("record") McUserSign record, @Param("parentId") Integer parentId);

    /**
     * 查询签到数据
     * @param example
     * @param parentId
     * @return
     */
    List<McUserSign> selectSignRecordByExample(@Param("example") McUserSignExample example,
                                               @Param("parentId") Integer parentId);

    /**
     * 条件签到统计
     * @param example
     * @param parentId
     * @return
     */
    long countSignRecordByExample(@Param("example") McUserSignExample example,
                                  @Param("parentId") Integer parentId);

}
