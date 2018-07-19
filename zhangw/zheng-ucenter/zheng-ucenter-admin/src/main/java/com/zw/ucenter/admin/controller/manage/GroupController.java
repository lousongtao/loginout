package com.zw.ucenter.admin.controller.manage;

import java.security.PublicKey;
import java.util.*;

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
import com.zheng.common.validator.NotNullValidator;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import com.zheng.ucenter.dao.model.McGroupUser;
import com.zheng.ucenter.rpc.api.McGroupService;
import com.zheng.ucenter.rpc.api.McUserGroupService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.api.UpmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zw on 2018/7/12
 */
@Controller
@Api(value = "员工分组管理", description = "员工分组管理")
@RequestMapping("/manage/group")
public class GroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private McGroupService      mcGroupService;

    @Autowired
    private McUserGroupService  mcUserGroupService;

    @Autowired
    private UpmsApiService      upmsApiService;

    @Autowired
    private UpmsUserService     upmsUserService;

    @ApiOperation(value = "用户分组首页")
    @RequiresPermissions("ucenter:group:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/group/index.jsp";
    }

    @ApiOperation(value = "用户分组列表")
    @RequiresPermissions("ucenter:group:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit) {
        McGroupExample groupExample = new McGroupExample();
        groupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExampleForOffsetPage(groupExample, offset,
            limit);
        ArrayList<McGroupUser> rows = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mcGroups)) {
            for (McGroup group : mcGroups) {
                McGroupUser mcGroupUser = new McGroupUser();
                BeanUtils.copyProperties(group, mcGroupUser);
                List<Integer> groupUsersId = mcUserGroupService.getGroupUsers(group.getId());
                if (!CollectionUtils.isEmpty(groupUsersId)) {
                    UpmsUserExample upmsUserExample = new UpmsUserExample();
                    upmsUserExample.createCriteria().andUserIdIn(groupUsersId);
                    List<UpmsUser> upmsUsers = upmsUserService.selectByExample(upmsUserExample);
                    mcGroupUser.setUserList(upmsUsers);
                }
                rows.add(mcGroupUser);
            }
        }
        long total = mcGroupService.countByExample(groupExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "获取分组用户数据")
    @RequiresPermissions("ucenter:group:read")
    @RequestMapping(value = "/getGroupUser", method = RequestMethod.POST)
    @ResponseBody
    public Object getGroupUser() {
        McGroupExample groupExample = new McGroupExample();
        groupExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUserId());
        List<McGroup> mcGroups = mcGroupService.selectByExample(groupExample);
        ArrayList<McGroupUser> groupUsers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mcGroups)) {
            for (McGroup group : mcGroups) {
                McGroupUser mcGroupUser = new McGroupUser();
                BeanUtils.copyProperties(group, mcGroupUser);
                List<Integer> groupUsersId = mcUserGroupService.getGroupUsers(group.getId());
                if (!CollectionUtils.isEmpty(groupUsersId)) {
                    UpmsUserExample upmsUserExample = new UpmsUserExample();
                    upmsUserExample.createCriteria().andUserIdIn(groupUsersId).andSchedulestatusEqualTo((byte) 1);
                    List<UpmsUser> upmsUsers = upmsUserService.selectByExample(upmsUserExample);
                    mcGroupUser.setUserList(upmsUsers);
                }
                groupUsers.add(mcGroupUser);
            }
        }
        return new UcenterResult(UcenterResultConstant.SUCCESS, groupUsers);
    }

    @ApiOperation(value = "新增组页面")
    @RequiresPermissions("ucenter:group:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/group/create.jsp";
    }

    @ApiOperation(value = "新增组")
    @RequiresPermissions("ucenter:group:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(McGroup mcGroup) {
        ComplexResult result = FluentValidator.checkAll()
            .on(mcGroup.getName(), new NotNullValidator("名称")).doValidate()
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        Integer userId = UserUtils.getCurrentUserId();
        if (userId == null) {
            UpmsUser upmsUser1 = upmsApiService
                .selectUpmsUserByUsername(UserUtils.getCurrentUserName());
            userId = upmsUser1.getUserId();
        }
        mcGroup.setMcId(userId);
        mcGroup.setCreateTime(new Date());
        int create = mcGroupService.insert(mcGroup);
        if (create == 0) {
            return new UcenterResult(UcenterResultConstant.FAILED, "组名已存在,请更换！");
        }
        return new UcenterResult(UcenterResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除组")
    @RequiresPermissions("ucenter:group:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id) {
        //删除之前先确定组内没有成员
        int count = mcGroupService.checkAndDeleteById(id);
        if (count == 0) {
            return new UcenterResult(UcenterResultConstant.FAILED, "组内有员工,请先解除绑定");
        } else {
            return new UcenterResult(UcenterResultConstant.SUCCESS, count);
        }
    }

    @ApiOperation(value = "修改组页面")
    @RequiresPermissions("ucenter:group:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        McGroup mcGroup = mcGroupService.selectByPrimaryKey(id);
        modelMap.put("mcGroup", mcGroup);
        return "/manage/group/update.jsp";
    }

    @ApiOperation(value = "修改组")
    @RequiresPermissions("ucenter:group:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, McGroup mcGroup) {
        ComplexResult result = FluentValidator.checkAll()
            .on(mcGroup.getName(), new NotNullValidator("名称")).doValidate()
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        mcGroup.setId(id);
        int count = mcGroupService.updateByPrimaryKeySelective(mcGroup);
//        int count = mcGroupService.createOrUpdateGroup(mcGroup, "");
        if (count > 0) {
            return new UcenterResult(UcenterResultConstant.SUCCESS, count);
        } else {
            return new UcenterResult(UcenterResultConstant.FAILED, "修改失败");
        }
    }

}
