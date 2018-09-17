package com.zheng.upms.server.controller.manage;

import com.zheng.common.base.BaseController;
import com.zheng.common.util.DateUtils;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.rpc.api.McUserSignService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.rpc.api.UpmsRoleService;
import com.zheng.upms.rpc.api.UpmsUserRoleService;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zheng.upms.common.constant.ConstantValue;
import com.zheng.upms.server.conf.SeqKit;
import com.zheng.upms.server.dto.SignReportRow;
import com.zheng.upms.server.form.SignReportForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zw on 2018/8/2
 * 考勤管理
 */
@Controller
@Api(value = "员工考勤", description = "员工考勤管理")
@RequestMapping("/manage/sign")
public class SignController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private McUserSignService   mcUserSignService;
    @Autowired
    private UpmsUserRoleService upmsUserRoleService;
    @Autowired
    private UpmsRoleService upmsRoleService;
    @Autowired
    private UpmsUserService     upmsUserService;

    @ApiOperation(value = "员工签到数据列表")
    @RequiresPermissions("ucenter:sign:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        return "/manage/sign/index.jsp";
    }

    @ApiOperation(value = "员工签到数据")
    @RequiresPermissions("ucenter:sign:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit) {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            currentUser = upmsUserService.selectByPrimaryKey(UserUtils.getCurrentUserId());
        }
        Map<String, Object> result = new HashMap<>();
        //检查role是客户管理员还是普通员工 todo 用户表里有字段标识用户type,不需要查
        UpmsUserRoleExample urExample = new UpmsUserRoleExample();
        urExample.createCriteria().andUserIdEqualTo(currentUser.getUserId());
        List<UpmsUserRole> userRoles = upmsUserRoleService.selectByExample(urExample);
        if (userRoles != null && !userRoles.isEmpty()){
            UpmsRoleExample roleExample = new UpmsRoleExample();
            roleExample.createCriteria().andRoleIdEqualTo(userRoles.get(0).getRoleId());
            List<UpmsRole> roles = upmsRoleService.selectByExample(roleExample);
            if (roles != null && !roles.isEmpty()){
                if (roles.get(0).getName().equals("marchant")){
                    McUserSignExample example = new McUserSignExample();
                    example.setOrderByClause("sign_time desc");
                    List<McUserSign> rows = mcUserSignService.selectSignRecordByExample(example, currentUser.getUserId(), offset, limit);
                    long total = mcUserSignService.countByExample(example, currentUser.getUserId());
                    result.put("rows", rows);
                    result.put("total", total);
                }else if (roles.get(0).getName().equals("staff")){
                    Integer parentId = currentUser.getParentId();
                    McUserSignExample example = new McUserSignExample();
                    example.createCriteria().andUIdEqualTo(UserUtils.getCurrentUserId());
                    example.setOrderByClause("sign_time desc");
                    List<McUserSign> rows = mcUserSignService.selectSignRecordByExample(example, parentId, offset, limit);
                    long total = mcUserSignService.countByExample(example, parentId);
                    result.put("rows", rows);
                    result.put("total", total);
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "网页端员工签到")
    @RequiresPermissions("ucenter:sign:signinout")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public Object signIn() {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            return sign(ConstantValue.SIGNTYPE_IN, ConstantValue.SIGNVIA_BROWSER, new Date(), currentUser);
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Please Login again");
        }
    }

    @ApiOperation(value = "网页端员工签到")
    @RequiresPermissions("ucenter:sign:signinout")
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public Object signOut() {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            return sign(ConstantValue.SIGNTYPE_OUT, ConstantValue.SIGNVIA_BROWSER, new Date(), currentUser);
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Please Login again");
        }
    }

    @ApiOperation(value = "管理员补录员工签到记录")
    @RequiresPermissions("ucenter:sign:supplysign")
    @RequestMapping(value = "/supplySign", method = RequestMethod.GET)
    public String supplySign(Model model) {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            return "/manage/index.jsp";
        }
        UpmsUserExample example = new UpmsUserExample();
        example.createCriteria().andParentIdEqualTo(currentUser.getUserId());
        List<UpmsUser> staffs = upmsUserService.selectByExample(example);
        model.addAttribute("staffs", staffs);
        return "/manage/sign/supplySign.jsp";
    }

    @ApiOperation(value = "管理员补录员工签到记录")
    @RequiresPermissions("ucenter:sign:supplysign")
    @RequestMapping(value = "/supplySign", method = RequestMethod.POST)
    @ResponseBody
    public Object supplySign(McUserSign sign) {
        UpmsUser staff = upmsUserService.selectByPrimaryKey(sign.getuId());
        if (staff == null){
            return new UpmsResult(UpmsResultConstant.FAILED, "Cannot find staff by id " + sign.getuId());
        }
        if (sign.getSignType() == ConstantValue.SIGNTYPE_IN) {
            return sign(ConstantValue.SIGNTYPE_IN, ConstantValue.SIGNVIA_BROWSER, sign.getSignTime(), staff);
        } else {
            return sign(ConstantValue.SIGNTYPE_OUT, ConstantValue.SIGNVIA_BROWSER, sign.getSignTime(), staff);
        }
    }

    private Object sign(int type, int signVia, Date signTime, UpmsUser staff){
        McUserSign mcUserSign = new McUserSign();
        mcUserSign.setSignId(SeqKit.genId());
        mcUserSign.setSignTime(signTime);
        mcUserSign.setSignType(type);
        mcUserSign.setSignVia(signVia);
        mcUserSign.setuId(staff.getUserId());
        mcUserSign.setuName(staff.getRealname());
        int signRecord = mcUserSignService.insertSignRecord(mcUserSign, staff.getParentId());
        LOGGER.info(staff.getRealname() + " sign success");
        return new UpmsResult(UpmsResultConstant.SUCCESS, signRecord);
    }

    @ApiOperation(value = "修改页面")
    @RequiresPermissions("ucenter:signrecord:update")
    @RequestMapping(value = "/update/{signId}", method = RequestMethod.GET)
    public String update(@PathVariable("signId") long signId, ModelMap modelMap) {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            McUserSignExample example = new McUserSignExample();
            example.createCriteria().andSignIdEqualTo(signId);
            List<McUserSign> rows = mcUserSignService.selectSignRecordByExample(example, currentUser.getUserId(), 0, 10);
            if (rows != null && !rows.isEmpty())
                modelMap.put("mcUserSign", rows.get(0));
            return "/manage/sign/update.jsp";
        } else {
            return "/manage/index.jsp";
        }
    }

    @ApiOperation(value = "修改")
    @RequiresPermissions("ucenter:signrecord:update")
    @RequestMapping(value = "/update/{signId}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("signId") long signId, McUserSign mcUserSign) {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            mcUserSign.setSignId(signId);
            int count = mcUserSignService.updateByPrimaryKeySelective(currentUser.getUserId(),mcUserSign);
            if (count > 0) {
                return new UpmsResult(UpmsResultConstant.SUCCESS, count);
            } else {
                return new UpmsResult(UpmsResultConstant.FAILED, "Failed to update this record, please contact administrator!");
            }
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Please Login again");
        }

    }

    @InitBinder
    public void dataBinding(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "signTime", new CustomDateEditor(dateFormat, true));
    }

    @ApiOperation(value = "delete sign record")
    @RequiresPermissions("ucenter:signrecord:delete")
    @RequestMapping(value = "/delete/{signId}", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteSignRecord(@PathVariable long signId){
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null){
            int count = mcUserSignService.deleteSignRecord(signId, currentUser.getUserId());
            if (count == 0) {
                return new UpmsResult(UpmsResultConstant.FAILED, "Failed to delete this record, please contact administrator!");
            } else {
                return new UpmsResult(UpmsResultConstant.SUCCESS, count);
            }
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Please Login again");
        }

    }

    @ApiOperation(value = "sign record report")
    @RequiresPermissions("ucenter:sign:queryrecord")
    @RequestMapping(value = "/queryrecord", method = RequestMethod.GET)
    public String reportSignRecord(){
        return "/manage/sign/report.jsp";
    }

    @ApiOperation(value = "sign record report")
    @RequiresPermissions("ucenter:sign:queryrecord")
    @RequestMapping(value = "/queryrecord", method = RequestMethod.POST)
    @ResponseBody
    public Object reportSignRecord(@RequestBody SignReportForm form){
        UpmsUser currentUser = UserUtils.getCurrentUser();
        Timestamp dayStartTime = DateUtils.getDayStartTime(form.getPageMonday());
        Timestamp dayEndTime = DateUtils.getDayEndTime(form.getPageSunday());
        McUserSignExample example = new McUserSignExample();
        example.createCriteria().andSignTimeBetween(dayStartTime, dayEndTime);
        List<McUserSign> signs = mcUserSignService.selectSignRecordByExample(example, currentUser.getUserId(), 0, 50000);
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andParentIdEqualTo(currentUser.getUserId());
        List<UpmsUser> staffs = upmsUserService.selectByExample(userExample);
        HashMap<String, Object> result = new HashMap<>();
        try {
            List<SignReportRow> rows = formatSignReportRow(signs, staffs);
            result.put("rows", rows);
        } catch (Exception e) {
            return new UpmsResult(UpmsResultConstant.FAILED, e.getMessage());
        }

        return result;

    }

    private List<SignReportRow> formatSignReportRow(List<McUserSign> signs, List<UpmsUser> staffs) throws Exception {
        HashMap<String, SignReportRow> hmNameRow = new HashMap<>();
        for (int i = 0; i < signs.size(); i++) {
            McUserSign sign = signs.get(i);
            UpmsUser staff = getUserById(sign.getuId(), staffs);
            if (staff == null)
                throw new Exception("cannot find staff info by sign record id " + sign.getSignId());
            if (hmNameRow.get(sign.getuName()) == null){
                SignReportRow row = new SignReportRow(staff);
                row.setStaff(staff);
                hmNameRow.put(row.getStaff().getRealname(), row);
            }
            try {
                hmNameRow.get(staff.getRealname()).addSignRecord(sign);
            } catch (Exception e) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                throw new Exception(sign.getuName() + " sign more in or out on " + df.format(sign.getSignTime()));
            }
        }

        ArrayList<SignReportRow> rows = new ArrayList<>();
        rows.addAll(hmNameRow.values());
//        rows.add(generateRowTotal(rows));//最后增加行的汇总记录
        return rows;
    }

//    /**
//     * 对员工打卡信息进行汇总, 每天的员工小时数和工资累加
//     * @param staffSigns 每个员工一周的打卡数据作为一条记录,
//     * @return
//     */
//    private SignReportRow generateRowTotal(ArrayList<SignReportRow> staffSigns){
//        UpmsUser staff = new UpmsUser();
//        staff.setRealname("Total");
//        SignReportRow total = new SignReportRow(staff);
//        if (staffSigns == null)
//            return total;
//        for (int i = 0; i < staffSigns.size(); i++) {
//            SignReportRow staffSign = staffSigns.get(i);
//            total.getMonday().setHours(total.getMonday().getHours() + staffSign.getMonday().getHours());
//            total.getMonday().setSalary(total.getMonday().getSalary() + staffSign.getMonday().getSalary());
//            total.getTuesday().setHours(total.getTuesday().getHours() + staffSign.getTuesday().getHours());
//            total.getTuesday().setSalary(total.getTuesday().getSalary() + staffSign.getTuesday().getSalary());
//            total.getWednesday().setHours(total.getWednesday().getHours() + staffSign.getWednesday().getHours());
//            total.getWednesday().setSalary(total.getWednesday().getSalary() + staffSign.getWednesday().getSalary());
//            total.getThursday().setHours(total.getThursday().getHours() + staffSign.getThursday().getHours());
//            total.getThursday().setSalary(total.getThursday().getSalary() + staffSign.getThursday().getSalary());
//            total.getFriday().setHours(total.getFriday().getHours() + staffSign.getFriday().getHours());
//            total.getFriday().setSalary(total.getFriday().getSalary() + staffSign.getFriday().getSalary());
//            total.getSaturday().setHours(total.getSaturday().getHours() + staffSign.getSaturday().getHours());
//            total.getSaturday().setSalary(total.getSaturday().getSalary() + staffSign.getSaturday().getSalary());
//            total.getSunday().setHours(total.getSunday().getHours() + staffSign.getSunday().getHours());
//            total.getSunday().setSalary(total.getSunday().getSalary() + staffSign.getSunday().getSalary());
//        }
//        return total;
//    }

    private UpmsUser getUserById(int userId, List<UpmsUser> userList){
        if (userList == null)
            return null;
        else {
            for (UpmsUser user : userList){
                if (user.getUserId() == userId){
                    return user;
                }
            }
        }
        return null;
    }
}
