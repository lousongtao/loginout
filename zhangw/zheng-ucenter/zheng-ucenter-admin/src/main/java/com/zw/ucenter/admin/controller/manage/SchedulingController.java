package com.zw.ucenter.admin.controller.manage;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.zheng.common.base.BaseController;
import com.zheng.common.util.DateUtils;
import com.zheng.common.util.Money;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.vo.McSchedulingCell;
import com.zheng.ucenter.rpc.api.McGroupService;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;
import com.zheng.ucenter.rpc.api.McUserGroupService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zw.ucenter.admin.dto.SchedulingRow;
import com.zw.ucenter.admin.form.SchedulingForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zw on 2018/7/18
 */
@Controller
@Api(value = "员工排班", description = "员工排班管理")
@RequestMapping("/manage/scheduling")
public class SchedulingController extends BaseController {

    private static final Logger   LOGGER = LoggerFactory.getLogger(SchedulingController.class);

    @Autowired
    private McSchedulePlanService mcSchedulePlanService;

    @Autowired
    private McGroupService        mcGroupService;

    @Autowired
    private McUserGroupService    mcUserGroupService;

    @Autowired
    private UpmsUserService       upmsUserService;

    @ApiOperation(value = "员工排班首页")
    @RequiresPermissions("ucenter:scheduling:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        // 所有组
        McGroupExample mcGroupExample = new McGroupExample();
        mcGroupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExample(mcGroupExample);
        model.addAttribute("mcGroups", mcGroups);
        return "/manage/scheduling/index.jsp";
    }

    @ApiOperation(value = "跳转更新单元格页面")
    @RequestMapping(value = "/updateCell/{groupId}", method = RequestMethod.GET)
    @RequiresPermissions("ucenter:scheduling:write")
    public String updateCell(@PathVariable("groupId") Integer groupId, Integer cellId,
                             HttpServletRequest request, Model model) {
        //组内参与排班的用户
        String schedulingDate = request.getParameter("schedulingDate");
        List<Integer> groupUsersId = mcUserGroupService.getGroupUsers(groupId);
        List<UpmsUser> groupUsers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(groupUsersId)) {
            UpmsUserExample upmsUserExample = new UpmsUserExample();
            upmsUserExample.createCriteria().andUserIdIn(groupUsersId)
                .andSchedulestatusEqualTo((byte) 1);
            groupUsers = upmsUserService.selectByExample(upmsUserExample);
        }
        //该单元格数据
        McSchedulePlan cellData = mcSchedulePlanService.getCellDataById(cellId,
            UserUtils.getCurrentUserId());
        model.addAttribute("cellData", cellData);
        model.addAttribute("groupId", groupId);
        model.addAttribute("schedulingDate", schedulingDate);
        model.addAttribute("groupUsers", groupUsers);
        return "/manage/scheduling/updateCell.jsp";
    }

    @ApiOperation(value = "保存单元格数据")
    @RequestMapping(value = "/updateCell", method = RequestMethod.POST)
    @RequiresPermissions("ucenter:scheduling:write")
    @ResponseBody
    public Object updateCell(McSchedulePlan cell) {
        System.out.println(cell.toString());
        Integer getuId = cell.getuId();
        UpmsUser upmsUser = upmsUserService.selectByPrimaryKey(getuId);
        if (upmsUser != null) {
            BigDecimal totalTime = cell.getTotalTime();
            Long perSalary = upmsUser.getPerSalary();
            if (perSalary != null && totalTime != null) {
                Money money = new Money(perSalary);
                cell.setEstimatePay(money.multiply(totalTime).getCent());
            }
            cell.setuName(upmsUser.getRealname());
            cell.setPerSalary(perSalary);
        }
        mcSchedulePlanService.updateCellData(cell, UserUtils.getCurrentUserId());
        return new UcenterResult(UcenterResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除单元格数据")
    @RequestMapping(value = "/deleteCell/{cellId}", method = RequestMethod.GET)
    @RequiresPermissions("ucenter:scheduling:write")
    @ResponseBody
    public Object deleteCell(@PathVariable("cellId") Integer cellId) {
        int deleteCell = mcSchedulePlanService.deleteCell(cellId, UserUtils.getCurrentUserId());
        return new UcenterResult(UcenterResultConstant.SUCCESS, deleteCell);
    }

    @ApiOperation(value = "批量删除单元格数据")
    @RequestMapping(value = "/batchDeleteCell", method = RequestMethod.POST)
    @RequiresPermissions("ucenter:scheduling:write")
    @ResponseBody
    public Object batchDeleteCell(@RequestParam("ids") String ids) {
        int deleteCell=mcSchedulePlanService.batchDeleteCell(ids,UserUtils.getCurrentUserId());
        return new UcenterResult(UcenterResultConstant.SUCCESS, deleteCell);
    }

    @ApiOperation(value = "获取排班数据")
    @RequiresPermissions("ucenter:scheduling:read")
    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Object getData(@RequestBody SchedulingForm form) {
        Integer currentUserId = UserUtils.getCurrentUserId();
        Timestamp dayStartTime = DateUtils.getDayStartTime(form.getPageMonday());
        Timestamp dayEndTime = DateUtils.getDayEndTime(form.getPageSunday());
        System.out.println(dayStartTime.toString());
        System.out.println(dayEndTime.toString());

        List<McSchedulingCell> mcSchedulePlans = mcSchedulePlanService
            .selectDataByDate(dayStartTime, dayEndTime, currentUserId);
        //处理数据
        List<SchedulingRow> rows = formatterScheduleData(mcSchedulePlans, dayStartTime, dayEndTime);
        //        List<SchedulingRow> rows = getD(currentUserId);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        int total = 0;
        if (!CollectionUtils.isEmpty(rows)) {
            total = rows.size();
        }
        result.put("total", total);
        return result;
    }

    private List<SchedulingRow> formatterScheduleData(List<McSchedulingCell> mcSchedulePlans,
                                                      Timestamp dayStartTime,
                                                      Timestamp dayEndTime) {
        ArrayList<SchedulingRow> rows = new ArrayList<>();
        if (CollectionUtils.isEmpty(mcSchedulePlans)) {
            return rows;
        }
        List<String> dateStrList = DateUtils.getStringDateByStartAndEndDate(dayStartTime,
            dayEndTime);
        Table<Integer, String, LinkedList<McSchedulingCell>> totalData = HashBasedTable.create();
        //多线程收集
        mcSchedulePlans.parallelStream().forEach((McSchedulingCell item) -> {
            Integer groupId = item.getGroupId();
            String stringDate = DateUtils.format(item.getSchedulingDate(), DateUtils.DATE_PATTERN);
            boolean contains = totalData.contains(groupId, stringDate);
            if (contains) {
                totalData.get(groupId, stringDate).add(item);
            } else {
                LinkedList<McSchedulingCell> linkedList = new LinkedList<>();
                linkedList.add(item);
                totalData.put(groupId, stringDate, linkedList);
            }
        });
        //三维 linked数据收集完毕 totalData

        //记录每组的行数map
        Set<Integer> rowKeySet = totalData.rowKeySet();
        Map<Integer, Integer> groupRowCount = new HashMap();
        rowKeySet.forEach((Integer groupId) -> {
            ArrayList<Integer> compareList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                LinkedList<McSchedulingCell> groupIdCells = totalData.get(groupId,
                    dateStrList.get(i));
                if (CollectionUtils.isEmpty(groupIdCells)) {
                    compareList.add(0);
                } else {
                    compareList.add(groupIdCells.size());
                }
            }
            //找出集合中的最大值,确定当前组的行数,并保存
            Integer rowCount = Collections.max(compareList);
            groupRowCount.put(groupId, rowCount);
        });

        rowKeySet.forEach(groupId -> {
            Integer rowCount = groupRowCount.get(groupId);
            for (int i = 0; i < rowCount; i++) {
                SchedulingRow row = new SchedulingRow();
                row.setMcGroup(mcGroupService.selectByPrimaryKey(groupId));
                for (int j = 0; j < 7; j++) {
                    LinkedList<McSchedulingCell> linkedList = totalData.get(groupId,
                        dateStrList.get(j));
                    if (j == 0) {
                        row.setData1(removeElement(linkedList));
                    } else if (j == 1) {
                        row.setData2(removeElement(linkedList));
                    } else if (j == 2) {
                        row.setData3(removeElement(linkedList));
                    } else if (j == 3) {
                        row.setData4(removeElement(linkedList));
                    } else if (j == 4) {
                        row.setData5(removeElement(linkedList));
                    } else if (j == 5) {
                        row.setData6(removeElement(linkedList));
                    } else {
                        row.setData7(removeElement(linkedList));
                    }
                }
                rows.add(row);
            }
        });

        return rows;
    }

    private McSchedulingCell removeElement(LinkedList<McSchedulingCell> linkedList) {
        try {
            if (!CollectionUtils.isEmpty(linkedList)) {
                return linkedList.removeFirst();
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * MOCK 数据 用于页面调试
     * @param currentUserId
     * @return
     */
    private List<SchedulingRow> getD(Integer currentUserId) {
        List<SchedulingRow> rows = new ArrayList<>();
        McGroupExample mcGroupExample = new McGroupExample();
        mcGroupExample.createCriteria().andMcIdEqualTo(currentUserId);
        List<McGroup> mcGroups = mcGroupService.selectByExample(mcGroupExample);
        for (int i = 0; i < 10; i++) {
            McGroup mcGroup = mcGroups.get(new Random().nextInt(mcGroups.size()));
            SchedulingRow row = new SchedulingRow();
            row.setMcGroup(mcGroup);
            //data1
            McSchedulingCell data1 = new McSchedulingCell();
            data1.setId(10 * i + 1);
            data1.setuId(100 * i + 1);
            data1.setuName("person" + (100 * i + 1));
            Long perSalary1 = Long.valueOf((100 * i + 1) * 100);
            data1.setPerSalary((long) ((100 * i) * 100));
            Date beginDayOfWeek = DateUtils.getBeginDayOfWeek();
            Date schedulingDate1 = DateUtils.addDate(beginDayOfWeek, i);
            data1.setSchedulingDate(schedulingDate1);
            schedulingDate1.setHours(new Random().nextInt(10) + 10);
            schedulingDate1.setMinutes(new Random().nextInt(10) + 20);
            schedulingDate1.setSeconds(new Random().nextInt(10) + 30);
            //            data1.setStartTime(schedulingDate1);
            int hours1 = schedulingDate1.getHours();
            schedulingDate1.setHours(hours1 + 4);
            //            data1.setEndTime(schedulingDate1);
            data1.setEstimatePay(perSalary1 * 4);
            data1.setResult(0);
            data1.setCreateTime(beginDayOfWeek);
            data1.setUpdateTime(beginDayOfWeek);
            data1.setTotalTime(new BigDecimal(4));
            //            data1.setTotalPay(220l);
            //data2
            //            McSchedulingCell data2 = new McSchedulingCell();
            //            data2.setId(10 * i + 2);
            //            data2.setuId(100 * i + 2);
            //            data2.setuName("person" + (100 * i  + 2));
            //            Long perSalary2 = Long.valueOf((100 * i  + 2) * 100);
            //            data2.setPerSalary((long) ((100 * i ) * 100));
            //            Date schedulingDate2 = DateUtils.addDate(beginDayOfWeek, i);
            //            data2.setSchedulingDate(schedulingDate2);
            //            schedulingDate2.setHours(new Random().nextInt(10) + 10);
            //            schedulingDate2.setMinutes(new Random().nextInt(10) + 20);
            //            schedulingDate2.setSeconds(new Random().nextInt(10) + 30);
            //            data2.setStartTime(schedulingDate2);
            //            int hours2 = schedulingDate2.getHours();
            //            schedulingDate2.setHours(hours2 + 4);
            //            data2.setEndTime(schedulingDate2);
            //            data2.setEstimatePay(perSalary2 * 4);
            //            data2.setResult(0);
            //            data2.setCreateTime(beginDayOfWeek);
            //            data2.setUpdateTime(beginDayOfWeek);
            //data3
            //            McSchedulingCell data3 = new McSchedulingCell();
            //            data3.setId(10 * i + 3);
            //            data3.setuId(100 * i + 3);
            //            data3.setuName("person" + (100 * i  + 3));
            //            Long perSalary3 = Long.valueOf((100 * i  + 3) * 100);
            //            data3.setPerSalary((long) ((100 * i ) * 100));
            //            Date schedulingDate3 = DateUtils.addDate(beginDayOfWeek, i);
            //            data3.setSchedulingDate(schedulingDate3);
            //            schedulingDate3.setHours(new Random().nextInt(10) + 10);
            //            schedulingDate3.setMinutes(new Random().nextInt(10) + 20);
            //            schedulingDate3.setSeconds(new Random().nextInt(10) + 30);
            //            data3.setStartTime(schedulingDate3);
            //            int hours3 = schedulingDate3.getHours();
            //            schedulingDate3.setHours(hours3 + 4);
            //            data3.setEndTime(schedulingDate3);
            //            data3.setEstimatePay(perSalary3 * 4);
            //            data3.setResult(0);
            //            data3.setCreateTime(beginDayOfWeek);
            //            data3.setUpdateTime(beginDayOfWeek);
            //data4
            //            McSchedulingCell data4 = new McSchedulingCell();
            //            data4.setId(10 * i + 4);
            //            data4.setuId(100 * i + 4);
            //            data4.setuName("person" + (100 * i  + 4));
            //            Long perSalary4 = Long.valueOf((100 * i  + 4) * 100);
            //            data4.setPerSalary((long) ((100 * i ) * 100));
            //            Date schedulingDate4 = DateUtils.addDate(beginDayOfWeek, i);
            //            data3.setSchedulingDate(schedulingDate4);
            //            schedulingDate4.setHours(new Random().nextInt(10) + 10);
            //            schedulingDate4.setMinutes(new Random().nextInt(10) + 20);
            //            schedulingDate4.setSeconds(new Random().nextInt(10) + 30);
            //            data3.setStartTime(schedulingDate4);
            //            int hours4 = schedulingDate2.getHours();
            //            schedulingDate4.setHours(hours4 + 4);
            //            data4.setEndTime(schedulingDate4);
            //            data4.setEstimatePay(perSalary4 * 4);
            //            data4.setResult(0);
            //            data4.setCreateTime(beginDayOfWeek);
            //            data3.setUpdateTime(beginDayOfWeek);
            //            McSchedulingCell data5 = new McSchedulingCell();
            //            McSchedulingCell data6 = new McSchedulingCell();
            //            McSchedulingCell data7 = new McSchedulingCell();
            //            settlement.setTotalPay(1000l + i);
            row.setData1(data1);
            row.setData2(data1);
            row.setData3(data1);
            row.setData4(data1);
            row.setData5(data1);
            row.setData6(data1);
            row.setData7(data1);

            rows.add(row);
        }
        return rows;
    }
}
