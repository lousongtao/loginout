package com.zw.ucenter.admin.controller.manage;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.zheng.common.base.BaseController;
import com.zheng.common.util.DateUtils;
import com.zheng.common.util.MD5Util;
import com.zheng.common.util.Money;
import com.zheng.common.validator.LengthValidator;
import com.zheng.common.validator.MoneyValidator;
import com.zheng.common.validator.NotNullValidator;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import com.zheng.ucenter.dao.model.McUserGroup;
import com.zheng.ucenter.dao.model.McUserGroupExample;
import com.zheng.ucenter.dao.vo.McSchedulingCell;
import com.zheng.ucenter.dao.vo.StaffInfo;
import com.zheng.ucenter.rpc.api.McGroupService;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;
import com.zheng.ucenter.rpc.api.McUserGroupService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.common.constant.UpmsUserTypeConstant;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zw.ucenter.admin.form.SchedulingForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zw on 2018/7/2
 * 客户员工管理
 */
@Controller
@Api(value = "用户员工管理", description = "用户员工管理")
@RequestMapping("/manage/staff")
public class StaffController extends BaseController {

    private static final Logger   LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private UpmsUserService       upmsUserService;

    @Autowired
    private UpmsApiService        upmsApiService;

    @Autowired
    private McGroupService        mcGroupService;

    @Autowired
    private McUserGroupService    mcUserGroupService;

    @Autowired
    private McSchedulePlanService mcSchedulePlanService;

    @ApiOperation(value = "用户员工首页")
    @RequiresPermissions("ucenter:staff:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/staff/index.jsp";
    }

    @ApiOperation(value = "用户员工列表")
    @RequiresPermissions("ucenter:staff:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "accountSearch") String accountSearch,
                       @RequestParam(required = false, defaultValue = "", value = "nameSearch") String nameSearch,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        //有父级id的为员工
        UpmsUserExample.Criteria criteria = upmsUserExample.createCriteria()
            .andParentIdEqualTo(UserUtils.getCurrentUserId())
            .andTypeEqualTo(UpmsUserTypeConstant.staff.getCode());
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(accountSearch)) {
            criteria.andUsernameLike("%" + accountSearch + "%");
        }
        if (StringUtils.isNotBlank(nameSearch)) {
            criteria.andRealnameLike("%" + nameSearch + "%");
        }
        List<UpmsUser> upmsUsers = upmsUserService.selectByExampleForOffsetPage(upmsUserExample,
            offset, limit);
        List<StaffInfo> rows = new ArrayList<>();
        if (!CollectionUtils.isEmpty(upmsUsers)) {
            upmsUsers.forEach((UpmsUser user) -> {
                StaffInfo staffInfo = new StaffInfo();
                BeanUtils.copyProperties(user, staffInfo);
                List<McGroup> userGroups = mcUserGroupService.getUserGroup(user.getUserId());
                if (!CollectionUtils.isEmpty(userGroups)) {
                    staffInfo.setGroupList(userGroups);
                }
                rows.add(staffInfo);
            });
        }
        long total = upmsUserService.countByExample(upmsUserExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增员工页面")
    @RequiresPermissions("ucenter:staff:write")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/staff/create.jsp";
    }

    @ApiOperation(value = "员工账号监测")
    @RequiresPermissions("ucenter:staff:write")
    @ResponseBody
    @RequestMapping(value = "/ajaxUsername")
    public Object ajaxUsername(String username) {
        if (StringUtils.isNotEmpty(username)) {
            UpmsUserExample upmsUserExample = new UpmsUserExample();
            upmsUserExample.createCriteria().andUsernameEqualTo(username);
            List<UpmsUser> upmsUserList = upmsUserService.selectByExample(upmsUserExample);
            if (!CollectionUtils.isEmpty(upmsUserList)) {
                return new UcenterResult(UcenterResultConstant.FAILED, "账号已存在");
            }
        }
        return new UcenterResult(UcenterResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "新增用户员工")
    @RequiresPermissions("ucenter:staff:write")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UpmsUser upmsUser, HttpServletRequest request) {
        String baseSalary = request.getParameter("baseSalaryMoney");
        String perSalary = request.getParameter("perSalaryMoney");
        ComplexResult result = FluentValidator.checkAll()
            .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
            .on(baseSalary, new MoneyValidator("底薪")).on(perSalary, new MoneyValidator("时薪"))
            .on(upmsUser.getPassword(), new LengthValidator(5, 32, "密码"))
            .on(upmsUser.getRealname(), new NotNullValidator("姓名")).doValidate()
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        //钱的转换
        upmsUser.setBaseSalary(new Money(baseSalary).getCent());
        upmsUser.setPerSalary(new Money(perSalary).getCent());

        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.md5(upmsUser.getPassword() + upmsUser.getSalt()));
        upmsUser.setCtime(time);
        upmsUser.setType(UpmsUserTypeConstant.staff.getCode());
        Integer userId = UserUtils.getCurrentUserId();
        if (userId == null) {
            UpmsUser upmsUser1 = upmsApiService
                .selectUpmsUserByUsername(UserUtils.getCurrentUserName());
            userId = upmsUser1.getUserId();
        }
        upmsUser.setParentId(userId);
        upmsUser = upmsApiService.insertStaffInfo(upmsUser);
        if (null == upmsUser) {
            return new UcenterResult(UcenterResultConstant.FAILED, "帐号名已存在！");
        }
        LOGGER.info("用户:" + userId + "->新增员工，主键：userId={}", upmsUser.getUserId());
        return new UcenterResult(UcenterResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除用户员工")
    @RequiresPermissions("ucenter:staff:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = upmsUserService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改用户员工")
    @RequiresPermissions("ucenter:staff:write")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsUser user = upmsUserService.selectByPrimaryKey(id);
        modelMap.put("user", user);
        return "/manage/staff/update.jsp";
    }

    @ApiOperation(value = "修改用户员工信息")
    @RequiresPermissions("ucenter:staff:write")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UpmsUser upmsUser,
                         HttpServletRequest request) {
        String baseSalary = request.getParameter("baseSalaryMoney");
        String perSalary = request.getParameter("perSalaryMoney");
        ComplexResult result = FluentValidator.checkAll()
            .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
            .on(baseSalary, new MoneyValidator("底薪")).on(perSalary, new MoneyValidator("时薪"))
            .on(upmsUser.getRealname(), new NotNullValidator("姓名")).doValidate()
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        //钱的转换成分
        upmsUser.setBaseSalary(new Money(baseSalary).getCent());
        upmsUser.setPerSalary(new Money(perSalary).getCent());
        // 不允许直接改密码
        upmsUser.setPassword(null);
        // 不允许修改父id
        upmsUser.setParentId(null);
        upmsUser.setUserId(id);
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "用户分组")
    @RequiresPermissions("ucenter:staff:group")
    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    public String group(@PathVariable("id") int id, ModelMap modelMap) {
        // 所有组
        McGroupExample mcGroupExample = new McGroupExample();
        mcGroupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExample(mcGroupExample);
        // 当前用户所在组
        McUserGroupExample userGroupExample = new McUserGroupExample();
        userGroupExample.createCriteria().andMcUserIdEqualTo(id);
        List<McUserGroup> mcUserGroups = mcUserGroupService.selectByExample(userGroupExample);
        modelMap.put("mcUserGroups", mcUserGroups);
        modelMap.put("mcGroups", mcGroups);
        return "/manage/staff/group.jsp";
    }

    @ApiOperation(value = "用户分组保存")
    @RequiresPermissions("ucenter:staff:group")
    @RequestMapping(value = "/group/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Object group(@PathVariable("userId") int userId, HttpServletRequest request) {
        String[] groupIds = request.getParameterValues("groupId");
        int group = mcUserGroupService.group(groupIds, userId);
        return new UcenterResult(UcenterResultConstant.SUCCESS, group);
    }

    @ApiOperation(value = "员工查看自己的排班")
    @RequiresPermissions("ucenter:staff:schedule")
    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String schedule() {
        return "/manage/staff/schedule.jsp";
    }

    @ApiOperation(value = "员工查看自己的排班")
    @RequiresPermissions("ucenter:staff:schedule")
    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    @ResponseBody
    public Object schedule(@RequestBody SchedulingForm form) {
        Timestamp dayStartTime = DateUtils.getDayStartTime(form.getPageMonday());
        Timestamp dayEndTime = DateUtils.getDayEndTime(form.getPageSunday());
        UpmsUser currentUser = UserUtils.getCurrentUser();
        List<McSchedulingCell> schedulePlanList = mcSchedulePlanService.selectStaffData(
            dayStartTime, dayEndTime, currentUser.getParentId(), currentUser.getUserId());
        Map<String, Object> result = new HashMap<>();
        result.put("rows", schedulePlanList);
        int total = 0;
        if (!CollectionUtils.isEmpty(schedulePlanList)) {
            total = schedulePlanList.size();
        }
        result.put("total", total);
        return result;
    }

}
