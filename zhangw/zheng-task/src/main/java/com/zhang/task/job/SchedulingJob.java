package com.zhang.task.job;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.zheng.common.util.DateUtils;
import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import com.zheng.ucenter.dao.vo.McSchedulingCell;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;
import com.zheng.ucenter.rpc.api.McUserSignService;
import com.zheng.upms.common.constant.UpmsUserTypeConstant;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsUserService;

/**
 * Created by zw on 2018/8/20
 */
@Component
public class SchedulingJob {
    private static final Logger   LOGGER = LoggerFactory.getLogger(SchedulingJob.class);
    @Autowired
    private McSchedulePlanService schedulePlanService;
    @Autowired
    private McUserSignService     mcUserSignService;
    @Autowired
    private UpmsUserService       upmsUserService;

    /**
     * 每天晚上凌晨5点统计各用户前一天的数据
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void task() {
        Date yesterday = DateUtils.addDate(DateUtils.getStartTime(), -1);
        String yesterdayStr = DateUtils.format(yesterday);
        LOGGER.info("Start Deal All Staff Scheduling:" + yesterdayStr);
        Timestamp yesterdayStartTime = DateUtils.getDayStartTime(yesterday);
        Timestamp yesterdayEndTime = DateUtils.getDayEndTime(yesterday);
        try {
            //查出所有员工
            UpmsUserExample userExample = new UpmsUserExample();
            userExample.createCriteria().andLockedEqualTo((byte) 0)
                .andTypeEqualTo(UpmsUserTypeConstant.staff.getCode())
                .andSchedulestatusEqualTo((byte) 1);
            List<UpmsUser> upmsUsers = upmsUserService.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(upmsUsers)) {
                upmsUsers.parallelStream().forEach((UpmsUser staff) -> {
                    LOGGER.info("Deal Staff Schedule ->" + staff.getUsername());
                    McSchedulePlanExample mcSchedulePlanExample = new McSchedulePlanExample();
                    mcSchedulePlanExample.createCriteria().andUIdEqualTo(staff.getUserId())
                        .andSchedulingDateEqualTo(yesterday);
                    mcSchedulePlanExample.setOrderByClause("id desc");
                    List<McSchedulingCell> mcSchedulingCells = schedulePlanService
                        .selectDataByExample(mcSchedulePlanExample, staff.getParentId());
                    if (!CollectionUtils.isEmpty(mcSchedulingCells)) {
                        McUserSignExample signExample = new McUserSignExample();
                        signExample.createCriteria().andUIdEqualTo(staff.getUserId())
                            .andSignTimeBetween(yesterdayStartTime, yesterdayEndTime);
                        //查昨天的用户签到数据
                        List<McUserSign> userSigns = mcUserSignService.selectSignRecordByExample(
                            signExample, staff.getParentId(), null, null);
                        if (!CollectionUtils.isEmpty(userSigns)) {
                            //拿到第一次和最后一次签到时间
                            Date firstSignTime = userSigns.get(0).getSignTime();
                            Date lastSignTime = userSigns.get(userSigns.size() - 1).getSignTime();

                            McSchedulingCell schedulingCell = mcSchedulingCells.get(0);
                            String periodTime = schedulingCell.getPeriodTime();
                            String[] time = StringUtils.split(periodTime, "-");
                            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                            String startDateStr = yesterdayStr + " " + time[0] + ":00";
                            String endDateStr = yesterdayStr + " " + time[1] + ":00";
                            try {
                                Date startDate = sDateFormat.parse(startDateStr);
                                Date endDate = sDateFormat.parse(endDateStr);
                                //比较时间,修改排班数据结果
                                McSchedulePlan mcSchedulePlan = new McSchedulePlan();
                                BeanUtils.copyProperties(schedulingCell, mcSchedulePlan);
                                mcSchedulePlan.setUpdateTime(new Date());
                                //第一次签到时间在开始时间之前
                                //最后一次签到在结束时间之后
                                //todo 假如需要排除小的误差,转化成时间戳处理
                                if (firstSignTime.before(startDate)
                                    && lastSignTime.after(endDate)) {
                                    //finished
                                    mcSchedulePlan.setResult(1);
                                } else {
                                    //unfinished
                                    mcSchedulePlan.setResult(2);
                                }
                                schedulePlanService.updateCellData(mcSchedulePlan,
                                    staff.getParentId());
                            } catch (Exception e) {
                                LOGGER.error(
                                    staff.getUsername() + "Deal Schedule Error" + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("Please Deal:" + e.getMessage());
        }
    }
}
