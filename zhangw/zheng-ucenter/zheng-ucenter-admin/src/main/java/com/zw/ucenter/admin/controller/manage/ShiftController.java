package com.zw.ucenter.admin.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zheng.common.validator.LengthValidator;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.McShift;
import com.zheng.ucenter.dao.model.McShiftExample;
import com.zheng.ucenter.rpc.api.McShiftService;
import com.zheng.upms.client.util.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zw on 2018/7/11
 */
@Controller
@Api(value = "班次管理", description = "班次管理")
@RequestMapping("/manage/shift")
public class ShiftController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    private McShiftService      mcShiftService;

    @ApiOperation(value = "班次首页")
    @RequiresPermissions("ucenter:shift:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/shift/index.jsp";
    }

    @ApiOperation(value = "班次列表")
    @RequiresPermissions("ucenter:shift:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {
        McShiftExample shiftExample = new McShiftExample();
        shiftExample.createCriteria().andMIdEqualTo(UserUtils.getCurrentUserId());
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            shiftExample.setOrderByClause(sort + " " + order);
        }
        List<McShift> list = mcShiftService.selectByExampleForOffsetPage(shiftExample, offset,
            limit);
        long total = mcShiftService.countByExample(shiftExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增班次页面")
    @RequiresPermissions("ucenter:shift:write")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/shift/create.jsp";
    }

    @ApiOperation(value = "新增班次")
    @RequiresPermissions("ucenter:shift:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(McShift mcShift) {
        ComplexResult result = FluentValidator.checkAll()
            .on(mcShift.getPlanName(), new LengthValidator(1, 20, "名称"))
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        Integer mId = UserUtils.getCurrentUserId();
        mcShift.setmId(mId);
        int insertSelective = mcShiftService.insertSelective(mcShift);
        if (insertSelective > 0) {
            return new UcenterResult(UcenterResultConstant.SUCCESS, insertSelective);
        } else {
            return new UcenterResult(UcenterResultConstant.FAILED, "新增失败");
        }
    }

    @ApiOperation(value = "删除班次")
    @RequiresPermissions("ucenter:shift:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = mcShiftService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改班次页面")
    @RequiresPermissions("ucenter:shift:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        McShift mcShift = mcShiftService.selectByPrimaryKey(id);
        modelMap.put("mcShift", mcShift);
        return "/manage/shift/update.jsp";
    }

    @ApiOperation(value = "修改班次")
    @RequiresPermissions("ucenter:shift:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, McShift mcShift) {
        ComplexResult result = FluentValidator.checkAll()
            .on(mcShift.getPlanName(), new LengthValidator(1, 20, "名称"))
            .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        mcShift.setId(id);
        int updateByPrimaryKeySelective = mcShiftService.updateByPrimaryKeySelective(mcShift);
        return new UcenterResult(UcenterResultConstant.SUCCESS, updateByPrimaryKeySelective);
    }


    @ApiOperation(value = "获取用户全部班次")
    @RequiresPermissions("ucenter:shift:read")
    @RequestMapping(value = "/getAllShift", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllShift(){
        Integer currentUserId = UserUtils.getCurrentUserId();
        McShiftExample mcShiftExample = new McShiftExample();
        mcShiftExample.createCriteria().andMIdEqualTo(currentUserId);
        mcShiftExample.setOrderByClause("type asc");
        List<McShift> mcShifts = mcShiftService.selectByExample(mcShiftExample);
        return new UcenterResult(UcenterResultConstant.SUCCESS, mcShifts);
    }
}
