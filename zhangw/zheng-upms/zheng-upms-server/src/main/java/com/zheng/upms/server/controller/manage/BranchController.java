package com.zheng.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.zheng.common.base.BaseController;
import com.zheng.common.validator.NotNullValidator;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.dao.model.McBranch;
import com.zheng.upms.dao.model.McBranchExample;
import com.zheng.upms.rpc.api.McBranchService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Api(value = "Branch management", description = "Branch management")
@RequestMapping("/manage/branch")
public class BranchController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    private McBranchService mcBranchService;
    @Autowired
    private UpmsApiService upmsApiService;

    @ApiOperation(value = "index of branch management")
    @RequiresPermissions("ucenter:branch:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/branch/index.jsp";
    }

    @ApiOperation(value = "用户分组列表")
    @RequiresPermissions("ucenter:branch:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit) {
        McBranchExample branchExample = new McBranchExample();
        branchExample.createCriteria().andUserIdEqualTo(UserUtils.getCurrentUserId());
        List<McBranch> mcBranches = mcBranchService.selectByExampleForOffsetPage(branchExample, offset, limit);
        int total = mcBranchService.countByExample(branchExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", mcBranches);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增页面")
    @RequiresPermissions("ucenter:branch:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/branch/create.jsp";
    }

    @ApiOperation(value = "新增")
    @RequiresPermissions("ucenter:branch:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(McBranch mcBranch) {
        ComplexResult result = FluentValidator.checkAll()
                .on(mcBranch.getBranchName(), new NotNullValidator("branch name")).doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.FAILED, result.getErrors());
        }
        Integer userId = UserUtils.getCurrentUserId();
        if (userId == null) {
            UpmsUser upmsUser1 = upmsApiService.selectUpmsUserByUsername(UserUtils.getCurrentUserName());
            userId = upmsUser1.getUserId();
        }
        mcBranch.setUserId(userId);
        int create = mcBranchService.insert(mcBranch);
        if (create == 0) {
            return new UpmsResult(UpmsResultConstant.FAILED, "Failed to create branch, please contact administrator!");
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除")
    @RequiresPermissions("ucenter:branch:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id) {
        int count = mcBranchService.deleteByPrimaryKey(id);
        if (count == 0) {
            return new UpmsResult(UpmsResultConstant.FAILED, "Failed to delete this branch, please contact administrator!");
        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, count);
        }
    }

    @ApiOperation(value = "修改页面")
    @RequiresPermissions("ucenter:branch:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        McBranch mcBranch = mcBranchService.selectByPrimaryKey(id);
        modelMap.put("mcBranch", mcBranch);
        return "/manage/branch/update.jsp";
    }

    @ApiOperation(value = "修改")
    @RequiresPermissions("ucenter:branch:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, McBranch mcBranch) {
        ComplexResult result = FluentValidator.checkAll()
                .on(mcBranch.getBranchName(), new NotNullValidator("Branch Name")).doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.FAILED, result.getErrors());
        }
        mcBranch.setId(id);
        int count = mcBranchService.updateByPrimaryKeySelective(mcBranch);
        if (count > 0) {
            return new UpmsResult(UpmsResultConstant.SUCCESS, count);
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Failed to update this branch, please contact administrator!");
        }
    }
}
