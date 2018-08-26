package com.zheng.upms.server.controller.manage;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.zheng.common.base.BaseController;
import com.zheng.common.util.DateUtils;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.dao.vo.McSchedulingCell;
import com.zheng.upms.rpc.api.McGroupService;
import com.zheng.upms.rpc.api.McSchedulePlanService;
import com.zheng.upms.rpc.api.McUserSignService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zheng.upms.server.form.AttendanceForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by zw on 2018/8/15
 */
@Controller
@Api(value = "出勤管理", description = "员工排班出勤管理")
@RequestMapping("/manage/attendance")
public class AttendanceController extends BaseController {

    private static final Logger   LOGGER = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private McGroupService        mcGroupService;
    @Autowired
    private UpmsUserService       upmsUserService;
    @Autowired
    private McSchedulePlanService mcSchedulePlanService;
    @Autowired
    private McUserSignService     mcUserSignService;

    @ApiOperation(value = "出勤管理首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        // 所有组
        McGroupExample mcGroupExample = new McGroupExample();
        mcGroupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExample(mcGroupExample);
        model.addAttribute("mcGroups", mcGroups);
        return "/manage/attendance/index.jsp";
    }

    @ApiOperation(value = "出勤管理数据")
    @RequiresPermissions("ucenter:attendance:read")
    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Object getData(@RequestBody AttendanceForm form) {
        Integer currentUserId = UserUtils.getCurrentUserId();
        Timestamp dayStartTime = DateUtils.getDayStartTime(form.getPageFirstDay());
        Timestamp dayEndTime = DateUtils.getDayEndTime(form.getPageLastDay());
        McSchedulePlanExample example = new McSchedulePlanExample();
        McSchedulePlanExample.Criteria criteria = example.createCriteria()
            .andSchedulingDateBetween(dayStartTime, dayEndTime);
        if (form.getGroupId() != null) {
            criteria.andGroupIdEqualTo(form.getGroupId());
        }
        if (StringUtils.isNotBlank(form.getuName())) {
            criteria.andUNameLike("%" + form.getuName() + "%");
        }
        List<McSchedulingCell> mcSchedulePlans = mcSchedulePlanService.selectDataByExample(example,
            currentUserId);
        //格式化数据
        List<Map<String, Object>> rows = formatterAttendanceData(mcSchedulePlans, dayStartTime,
            dayEndTime);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        int total = 0;
        if (!CollectionUtils.isEmpty(rows)) {
            total = rows.size();
        }
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "排班出勤记录")
    @RequiresPermissions("ucenter:attendance:read")
    @RequestMapping(value = "/detail/{scheduleId}", method = RequestMethod.GET)
    public String detail(@PathVariable("scheduleId") Integer scheduleId, Model model) {
        McSchedulePlan mcSchedulePlan = mcSchedulePlanService.getCellDataById(scheduleId,
            UserUtils.getCurrentUserId());
        if (mcSchedulePlan != null) {
            model.addAttribute("mcSchedulePlan", mcSchedulePlan);
            Integer getuId = mcSchedulePlan.getuId();
            Date schedulingDate = mcSchedulePlan.getSchedulingDate();
            Timestamp dayStartTime = DateUtils.getDayStartTime(schedulingDate);
            Timestamp dayEndTime = DateUtils.getDayEndTime(schedulingDate);
            McUserSignExample example = new McUserSignExample();
            example.createCriteria().andUIdEqualTo(getuId).andSignTimeBetween(dayStartTime,
                dayEndTime);
            example.setOrderByClause("sign_time");
            List<McUserSign> userSignList = mcUserSignService.selectSignRecordByExample(example,
                UserUtils.getCurrentUserId(), null, null);
            if (!CollectionUtils.isEmpty(userSignList)) {
                model.addAttribute("userSignFirst", userSignList.get(0).getSignTime());
                model.addAttribute("userSignLast",
                    userSignList.get(userSignList.size() - 1).getSignTime());
            }
        }
        return "/manage/attendance/detail.jsp";
    }

    @ApiOperation(value = "修改出勤结果")
    @RequestMapping(value = "/updateResult", method = RequestMethod.POST)
    @RequiresPermissions("ucenter:attendance:write")
    @ResponseBody
    public Object updateResult(McSchedulePlan cell) {
        int result = mcSchedulePlanService.updateCellData(cell, UserUtils.getCurrentUserId());
        if (result > 0) {
            LOGGER.info("schedule result update successful");
            return new UpmsResult(UpmsResultConstant.SUCCESS, result);
        }
        return new UpmsResult(UpmsResultConstant.FAILED, "operation failed");
    }

    private List<Map<String, Object>> formatterAttendanceData(List<McSchedulingCell> mcSchedulePlans,
                                                              Timestamp dayStartTime,
                                                              Timestamp dayEndTime) {
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        if (CollectionUtils.isEmpty(mcSchedulePlans)) {
            return rows;
        }
        List<String> dateStrList = DateUtils.getStringDateByStartAndEndDate(dayStartTime,
            dayEndTime);
        Table<Integer, String, McSchedulingCell> totalData = HashBasedTable.create();
        mcSchedulePlans.parallelStream().forEach((McSchedulingCell item) -> {
            Integer getuId = item.getuId();
            String stringDate = DateUtils.format(item.getSchedulingDate(), DateUtils.DATE_PATTERN);
            totalData.put(getuId, stringDate, item);
        });
        Set<Integer> rowKeySet = totalData.rowKeySet();

        rowKeySet.forEach((Integer uId) -> {
            Map<String, Object> row = new HashMap<>();
            for (int j = 0; j < dateStrList.size(); j++) {
                McSchedulingCell mcSchedulingCell = totalData.get(uId, dateStrList.get(j));
                if (mcSchedulingCell != null && row.get("uId") == null) {
                    row.put("uName", mcSchedulingCell.getuName());
                    row.put("uId", uId);
                }
                row.put("day" + (j + 1), mcSchedulingCell);
            }
            rows.add(row);
        });
        return rows;
    }
}
