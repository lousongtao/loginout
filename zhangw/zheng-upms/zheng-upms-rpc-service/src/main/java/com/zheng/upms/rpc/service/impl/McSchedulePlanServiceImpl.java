package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.upms.common.constant.ConstantValue;
import com.zheng.upms.dao.mapper.McSchedulePlanMapper;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.dao.vo.McSchedulingCell;
import com.zheng.upms.rpc.api.McSchedulePlanService;
import com.zheng.upms.rpc.api.McScheduleplanTemplatedetailService;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    @Autowired
    UpmsUserService upmsUserService;
    @Autowired
    private McScheduleplanTemplatedetailService mcScheduleplanTemplatedetailService;

    @Override
    public McSchedulePlan getCellDataById(Integer cellId, Integer currentUserId) {
        try {
            return upmsApiMapper.selectScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {
            LOGGER.error("", e);
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
            LOGGER.error("", e);
        }
        return result;
    }

    @Override
    public int deleteCell(Integer cellId, Integer currentUserId) {
        int result = 0;
        try {
            result = upmsApiMapper.deleteScheduleDataByPrimaryKey(cellId, currentUserId);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return result;
    }

    @Override
    public int deleteScheduleDataByDate(Date startDate, Date endDate, Integer userId, int branchId) {
        int result = 0;
        try {
            result = upmsApiMapper.deleteScheduleDataByDate(startDate, endDate, userId, branchId);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return result;
    }

    @Override
    public int loadScheduleFromTemplate(Date startDate, Date endDate, Integer userId, int branchId, int templateId) {
        McScheduleplanTemplatedetailExample example = new McScheduleplanTemplatedetailExample();
        example.createCriteria().andTemplateIdEqualTo(templateId);
        List<McScheduleplanTemplatedetail> details = mcScheduleplanTemplatedetailService.selectByExample(example);
        int result = 0;
        if (details == null || details.isEmpty()){
            return result;
        }
        Map<Integer, UpmsUser> userMap = queryStaff(userId);
        try {
            for (int i = 0; i < details.size(); i++) {
                McScheduleplanTemplatedetail detail = details.get(i);
                UpmsUser staff = userMap.get(detail.getStaffId());
                if (staff == null || staff.getSchedulestatus() != ConstantValue.UPMS_USER_SCHEDULESTATUS_AVAILABLE){
                    continue;//找不到的员工直接跳过
                }

                McSchedulePlan plan = new McSchedulePlan();
                plan.setBranchId(branchId);
                plan.setCreateTime(new Date());
                plan.setGroupId(detail.getGroupId());
                plan.setPeriodTime(detail.getPeriodTime());
                plan.setuId(detail.getStaffId());
                plan.setuName(staff.getRealname());
                plan.setTotalTime(calculateWorkHours(plan.getPeriodTime()));
                plan.setSchedulingDate(getScheduleDayForPlan(startDate, detail.getWeekday()));
                plan.setPerSalary(staff.getPerSalary());
                plan.setEstimatePay((double)(staff.getPerSalary() * plan.getTotalTime()));
                result = updateCellData(plan, userId);
                if (result == 0){
                    return result;//exception
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return result;
    }

    /**
     * 根据排班时间计算小时数
     * @param workPeriod 类似于01:45-05:25格式的字符串
     * @return
     */
    private double calculateWorkHours(String workPeriod){
        String sStart = workPeriod.split("-")[0];
        String sEnd = workPeriod.split("-")[1];
        double hours = Integer.parseInt(sEnd.split(":")[0]) - Integer.parseInt(sStart.split(":")[0]);
        hours += (double)(Integer.parseInt(sEnd.split(":")[1]) - Integer.parseInt(sStart.split(":")[1])) / (double)60;
        String sHours = String.format(ConstantValue.FORMAT_DOUBLE, hours);//格式化为小数点后两位
        hours = Double.parseDouble(sHours);
        return hours;
    }

    /**
     * 根据startDate确定具体的周, 再根据weekDay对应到该周的第几天
     * @param startDate 默认为周一, 如果传入数据不是周一, 则是错误数据
     * @param weekDay  0=Sunday, 1=Monday....
     * @return
     */
    private Date getScheduleDayForPlan(Date startDate, int weekDay){
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (weekDay == 1){
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 6);
            return c.getTime();
        } else {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + weekDay - 2);
            return c.getTime();
        }
    }

    /**
     * query all staffs which parentId = userId
     * @param userId
     * @return
     */
    private Map<Integer, UpmsUser> queryStaff(int userId){
        Map<Integer, UpmsUser> userMap = new HashMap<>();
        UpmsUserExample example = new UpmsUserExample();
        example.createCriteria().andParentIdEqualTo(userId);
        List<UpmsUser> staffs = upmsUserService.selectByExample(example);
        if (staffs == null || staffs.isEmpty())
            return userMap;
        for (int i = 0; i < staffs.size(); i++) {
            UpmsUser staff = staffs.get(i);
            userMap.put(staff.getUserId(), staff);
        }
        return userMap;
    }

    @Override
    public List<McSchedulingCell> selectDataByDate(Date startDate, Date endDate, Integer mcId, int branchId) {
        try {
            int size = 0;
            List<McSchedulingCell> mcSchedulePlans = upmsApiMapper
                    .selectSchedulingDataByDate(startDate, endDate, mcId, branchId);
            if (!CollectionUtils.isEmpty(mcSchedulePlans)) {
                size = mcSchedulePlans.size();
            }
            LOGGER.info("用户:" + mcId + "时间段:" + startDate.toString() + "---" + endDate.toString()
                    + "->" + size + " 条记录");
            return mcSchedulePlans;
        } catch (Exception e) {
            LOGGER.error("", e);
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
            LOGGER.error("", e);
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
			LOGGER.error("", e);
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
			LOGGER.error("", e);
        }
        return null;
    }
}