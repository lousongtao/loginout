package com.zw.ucenter.admin.controller.manage;

import java.util.*;

import com.zheng.common.util.Money;
import com.zheng.common.validator.MoneyValidator;
import com.zheng.upms.dao.model.UpmsUserExample;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.zheng.common.base.BaseController;
import com.zheng.common.util.MD5Util;
import com.zheng.common.validator.LengthValidator;
import com.zheng.common.validator.NotNullValidator;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.api.UpmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zw on 2018/7/2
 * 客户员工管理
 */
@Controller
@Api(value = "用户员工管理", description = "用户员工管理")
@RequestMapping("/manage/staff")
public class StaffController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private UpmsUserService     upmsUserService;

    @Autowired
    private UpmsApiService      upmsApiService;

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
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        //有父级id的为员工
        upmsUserExample.createCriteria().andParentIdEqualTo(UserUtils.getCurrentUserId());
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsUserExample.or().andRealnameLike("%" + search + "%");
            upmsUserExample.or().andUsernameLike("%" + search + "%");
        }
        List<UpmsUser> rows = upmsUserService.selectByExampleForOffsetPage(upmsUserExample, offset,
                limit);
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

    @ApiOperation(value = "新增用户员工")
    @RequiresPermissions("ucenter:staff:write")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UpmsUser upmsUser, HttpServletRequest request) {
        String baseSalary = request.getParameter("baseSalaryMoney");
        String perSalary = request.getParameter("perSalaryMoney");
        ComplexResult result = FluentValidator.checkAll()
            .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
            .on(baseSalary, new MoneyValidator( "底薪"))
            .on(perSalary, new MoneyValidator("时薪"))
            .on(upmsUser.getPassword(), new LengthValidator(5, 32, "密码"))
            .on(upmsUser.getRealname(), new NotNullValidator("姓名")).doValidate()
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        //钱的转换
        upmsUser.setBaseSalary(new Money(baseSalary).getCent());
        upmsUser.setPerSalary(new Money(perSalary).getCent());

        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.md5(upmsUser.getPassword() + upmsUser.getSalt()));
        upmsUser.setCtime(time);
        Integer userId = UserUtils.getCurrentUserId();
        if (userId == null) {
            UpmsUser upmsUser1 = upmsApiService
                .selectUpmsUserByUsername(UserUtils.getCurrentUserName());
            userId = upmsUser1.getUserId();
        }
        upmsUser.setParentId(userId);
        upmsUser = upmsUserService.createUser(upmsUser);
        if (null == upmsUser) {
            return new UpmsResult(UpmsResultConstant.FAILED, "帐号名已存在！");
        }
        LOGGER.info("用户:" + userId + "->新增员工，主键：userId={}", upmsUser.getUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除用户员工")
    @RequiresPermissions("ucenter:staff:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = upmsUserService.deleteByPrimaryKeys(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
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
    public Object update(@PathVariable("id") int id, UpmsUser upmsUser) {
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
                .on(upmsUser.getRealname(), new NotNullValidator("姓名")).doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        // 不允许直接改密码
        upmsUser.setPassword(null);
        // 不允许修改父id
        upmsUser.setParentId(null);
        upmsUser.setUserId(id);
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }
}
