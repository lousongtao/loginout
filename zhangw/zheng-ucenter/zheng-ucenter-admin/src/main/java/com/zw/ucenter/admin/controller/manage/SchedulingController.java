package com.zw.ucenter.admin.controller.manage;

import com.zheng.ucenter.rpc.api.McShiftService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zheng.common.base.BaseController;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zw on 2018/7/18
 */
@Controller
@Api(value = "员工排班", description = "员工排班管理")
@RequestMapping("/manage/scheduling")
public class SchedulingController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingController.class);

    @Autowired
    McSchedulePlanService       mcSchedulePlanService;

    @Autowired
    McShiftService              mcShiftService;

    @ApiOperation(value = "员工排班首页")
    @RequiresPermissions("ucenter:scheduling:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/scheduling/index.jsp";
    }

    @ApiOperation(value = "获取排班数据")
    @RequiresPermissions("ucenter:scheduling:read")
    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Object getData(Integer id) {
        return new UcenterResult(UcenterResultConstant.SUCCESS, "");
    }
}
