package com.zw.ucenter.admin.controller.manage;

import com.zheng.common.base.BaseController;
import com.zheng.ucenter.dao.model.McPosition;
import com.zheng.ucenter.dao.model.McPositionExample;
import com.zheng.ucenter.rpc.api.McPositionService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zw on 2018/7/4
 * 员工标签
 */
@Controller
@Api(value = "员工标签管理", description = "员工标签管理")
@RequestMapping("/manage/label")
public class LabelController  extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private McPositionService mcPositionService;

    @Autowired
    private UpmsApiService upmsApiService;

    @ApiOperation(value = "用户标签首页")
    @RequiresPermissions("ucenter:label:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/label/index.jsp";
    }

    @ApiOperation(value = "用户标签列表")
    @RequiresPermissions("ucenter:label:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {
        McPositionExample positionExample = new McPositionExample();
        positionExample.createCriteria().andMcIdEqualTo(UserUtils.getCurrentUser().getParentId());
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            positionExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            positionExample.or().andNameLike("%" + search + "%");
        }
        List<McPosition> rows = mcPositionService.selectByExampleForOffsetPage(positionExample, offset,
                limit);
        long total = mcPositionService.countByExample(positionExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }
}
