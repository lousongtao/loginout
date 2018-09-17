package com.zheng.upms.server.controller.manage;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.zheng.common.base.BaseController;
import com.zheng.common.util.DateUtils;
import com.zheng.common.util.Money;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.dao.vo.McSchedulingCell;
import com.zheng.upms.rpc.api.*;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.common.constant.ConstantValue;
import com.zheng.upms.server.dto.SchedulingRow;
import com.zheng.upms.server.form.ScheduleTemplateForm;
import com.zheng.upms.server.form.SchedulingForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private McScheduleplanTemplateService mcScheduleplanTemplateService;

    @Autowired
    private McGroupService        mcGroupService;

    @Autowired
    private McUserGroupService    mcUserGroupService;

    @Autowired
    private UpmsUserService       upmsUserService;

    @Autowired
    private McBranchService mcBranchService;

    @ApiOperation(value = "员工排班首页")
    @RequiresPermissions("ucenter:scheduling:read")
    @RequestMapping(value = "/index/{branchId}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable("branchId") int branchId) {
        // 所有组
        McGroupExample mcGroupExample = new McGroupExample();
        mcGroupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExample(mcGroupExample);
        int userId = UserUtils.getCurrentUserId();
        McScheduleplanTemplateExample templateExample = new McScheduleplanTemplateExample();
        templateExample.createCriteria().andUserIdEqualTo(userId);
        List<McScheduleplanTemplate> templates = mcScheduleplanTemplateService.selectByExample(templateExample);
        model.addAttribute("mcGroups", mcGroups);
        model.addAttribute("branchId", branchId);
        model.addAttribute("templates", templates);
        return "/manage/scheduling/index.jsp";
    }

    @ApiOperation(value = "员工排班, 选择分店")
    @RequiresPermissions("ucenter:scheduling:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Object index(Model model) {
        int currentUserId = UserUtils.getCurrentUserId();
        McBranchExample mcBranchExample = new McBranchExample();
        mcBranchExample.createCriteria().andUserIdEqualTo(currentUserId);
        List<McBranch> branches = mcBranchService.selectByExample(mcBranchExample);
        if (branches == null || branches.isEmpty()){
            return new UpmsResult(UpmsResultConstant.FAILED, "Please input branch data.");
        } else if (branches.size() == 1){
            model.addAttribute("branchId", branches.get(0).getId());
            return "/manage/scheduling/index.jsp";
        } else {
//            model.addAttribute("branches", branches);
            return "/manage/branch/index.jsp";
        }
    }

    @ApiOperation(value = "跳转更新单元格页面")
    @RequestMapping(value = "/updateCell/{groupId}", method = RequestMethod.GET)
    @RequiresPermissions("ucenter:scheduling:write")
    public String updateCell(@PathVariable("groupId") Integer groupId, Integer cellId, int branchId,
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
        model.addAttribute("branchId", branchId);
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
            double totalTime = cell.getTotalTime();
            Double perSalary = upmsUser.getPerSalary();
            if (perSalary != null) {
                Money money = new Money(perSalary);
                cell.setEstimatePay((double)(perSalary * totalTime));
            }
            cell.setuName(upmsUser.getRealname());
            cell.setPerSalary(perSalary);
        }
        int result = mcSchedulePlanService.updateCellData(cell, UserUtils.getCurrentUserId());
        if (result > 0) {
            LOGGER.info("schedule data operation successful");
            return new UpmsResult(UpmsResultConstant.SUCCESS, result);
        }
        return new UpmsResult(UpmsResultConstant.FAILED, "operation failed");
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder){
//        binder.addValidators(new DateValidator());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "schedulingDate", new CustomDateEditor(dateFormat, true));
    }

    @ApiOperation(value = "删除单元格数据")
    @RequestMapping(value = "/deleteCell/{cellId}", method = RequestMethod.GET)
    @RequiresPermissions("ucenter:scheduling:write")
    @ResponseBody
    public Object deleteCell(@PathVariable("cellId") Integer cellId) {
        int deleteCell = mcSchedulePlanService.deleteCell(cellId, UserUtils.getCurrentUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, deleteCell);
    }

    @ApiOperation(value = "批量删除单元格数据")
    @RequestMapping(value = "/batchDeleteCell", method = RequestMethod.POST)
    @RequiresPermissions("ucenter:scheduling:write")
    @ResponseBody
    public Object batchDeleteCell(@RequestParam("ids") String ids) {
        int deleteCell = mcSchedulePlanService.batchDeleteCell(ids, UserUtils.getCurrentUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, deleteCell);
    }

    @ApiOperation(value = "获取排班数据")
    @RequiresPermissions("ucenter:scheduling:read")
    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Object getData(@RequestBody SchedulingForm form) {
        Integer currentUserId = UserUtils.getCurrentUserId();
        Timestamp dayStartTime = DateUtils.getDayStartTime(form.getPageMonday());
        Timestamp dayEndTime = DateUtils.getDayEndTime(form.getPageSunday());
        int branchId = form.getBranchId();
        System.out.println(dayStartTime.toString());
        System.out.println(dayEndTime.toString());

        List<McSchedulingCell> mcSchedulePlans = mcSchedulePlanService
            .selectDataByDate(dayStartTime, dayEndTime, currentUserId, branchId);
        //处理数据,并排序
        List<SchedulingRow> rows = formatterScheduleData(mcSchedulePlans, dayStartTime, dayEndTime);
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
        //降序排序
        rows.sort((o1, o2) -> {
            if (o1.getMcGroup() != null && o2.getMcGroup() != null){
                return o2.getMcGroup().getLevel() - o1.getMcGroup().getLevel();
            }
            return 0;
        });
        return rows;
    }

    private McSchedulingCell removeElement(LinkedList<McSchedulingCell> linkedList) {
        try {
            if (!CollectionUtils.isEmpty(linkedList)) {
                return linkedList.removeFirst();
            }
        } catch (Exception e) {
			LOGGER.error("", e);
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
            Double perSalary1 = Double.valueOf((100 * i + 1) * 100);
            data1.setPerSalary((double) ((100 * i) * 100));
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
            data1.setTotalTime(new Double(4));
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

    @ApiOperation(value = "保存排班模板")
    @RequiresPermissions("ucenter:scheduling:write")
    @RequestMapping(value = "/createTemplate", method = RequestMethod.GET)
    public String createTemplate(@RequestParam Map<String, Object> paramMap, Model model) {
        model.addAttribute("startDate", paramMap.get("startDate"));
        model.addAttribute("branchId", paramMap.get("branchId"));
        return "/manage/scheduling/createTemplate.jsp";
    }

    @ApiOperation(value = "保存排班模板")
    @RequiresPermissions("ucenter:scheduling:write")
    @RequestMapping(value = "/createScheduleTemplate", method = RequestMethod.POST)
    @ResponseBody
    public Object createTemplate(ScheduleTemplateForm form) {
        int userId = UserUtils.getCurrentUserId();
//        McScheduleplanTemplateExample example = new McScheduleplanTemplateExample();
//        example.createCriteria().andUserIdEqualTo(userId);
//        List<McScheduleplanTemplate> temps = mcScheduleplanTemplateService.selectByExample(example);
//        if (temps != null && temps.size() >= 10){
//            return new UpmsResult(UpmsResultConstant.FAILED, "You already have saved over 10 templates. If you want to save more, please delete some old.");
//        }

        int branchId = form.getBranchId();
        String startDate = form.getStartDate();
        Date sDate = null;
        try {
            sDate = ConstantValue.DFYMDHMS.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.error("", e);
            return new UpmsResult(UpmsResultConstant.FAILED, "The start date format is wrong.");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date eDate = c.getTime();
        int result = mcScheduleplanTemplateService.createScheduleTemplate(form.getName(), sDate, eDate, userId, branchId);
        if (result == 0)
            return new UpmsResult(UpmsResultConstant.FAILED, "Faild to create schedule template.");
        return new UpmsResult(UpmsResultConstant.SUCCESS, "Build schedule template successfully.");
    }

    @ApiOperation(value = "加载排班模板数据, 根据当前日期生成该周的排班表")
    @RequiresPermissions("ucenter:scheduling:write")
    @RequestMapping(value = "/loadTemplateToSchedule", method = RequestMethod.POST)
    @ResponseBody
    public Object loadTemplateToSchedule(ScheduleTemplateForm form) {
        Date startDate = null;
        try {
            startDate = ConstantValue.DFYMDHMS.parse(form.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.error("", e);
            return new UpmsResult(UpmsResultConstant.FAILED, "The start date format is wrong.");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 6);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date endDate = c.getTime();
        int userId = UserUtils.getCurrentUserId();
        int deletei = mcSchedulePlanService.deleteScheduleDataByDate(startDate, endDate, userId, form.getBranchId());

        int result = mcSchedulePlanService.loadScheduleFromTemplate(startDate, endDate, userId, form.getBranchId(), form.getTemplateId());
        if (result == 0)
            return new UpmsResult(UpmsResultConstant.FAILED, "Faild to load schedule template.");
        return new UpmsResult(UpmsResultConstant.SUCCESS, "Load schedule template successfully.");
    }
}
