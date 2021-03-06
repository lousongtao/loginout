package com.zheng.upms.rpc.mapper;


import com.zheng.upms.dao.model.*;
import com.zheng.upms.dao.vo.McSchedulingCell;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户VOMapper
 * Created by shuzheng on 2017/01/07.
 */
public interface UpmsApiMapper {

	// 根据用户id获取所拥有的权限
	List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

	// 根据用户id获取所属的角色
	List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);

	//创建客户排班表
	void createScheduleTable(@Param("mcId") Integer mcId);

	//创建客户打卡表
	void createSignTable(@Param("mcId") Integer mcId);

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
     * 根据日期删除该时间段内的排班数据
     * @param startDate
     * @param endDate
     * @param userId
     * @param branchId
     * @return
     */
	int deleteScheduleDataByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") int userId, @Param("branchId") int branchId);

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
													  @Param("mcId") Integer mcId,
													  @Param("branchId") int branchId);

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

	int deleteSignRecord(@Param("signId") long signId, @Param("parentId") int parentId);

    int updateSignRecord(@Param("parentId") int parentId, @Param("record") McUserSign mcUserSign);

    int insertScheduleTemplate(McScheduleplanTemplate temp);

}