package com.zw.ucenter.admin.controller.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zheng.common.base.BaseController;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import com.zheng.ucenter.rpc.api.McUserSignService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zw.ucenter.admin.conf.SeqKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
        McUserSignExample example = new McUserSignExample();
        Integer parentId = currentUser.getParentId();
        example.createCriteria().andUIdEqualTo(UserUtils.getCurrentUserId());
        example.setOrderByClause("sign_time desc");
        List<McUserSign> rows = mcUserSignService.selectSignRecordByExample(example, parentId,
            offset, limit);
        long total = mcUserSignService.countByExample(example, parentId);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "网页端员工签到")
    @RequiresPermissions("ucenter:sign:write")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public Object signIn() {
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            McUserSign mcUserSign = new McUserSign();
            mcUserSign.setSignId(SeqKit.genId());
            mcUserSign.setSignTime(new Date());
            mcUserSign.setSignType(0);
            mcUserSign.setuId(currentUser.getUserId());
            mcUserSign.setuName(currentUser.getRealname());
            int signRecord = mcUserSignService.insertSignRecord(mcUserSign,
                currentUser.getParentId());
            LOGGER.info(currentUser.getRealname() + " sign success");
            return new UcenterResult(UcenterResultConstant.SUCCESS, signRecord);
        } else {
            return new UcenterResult(UcenterResultConstant.FAILED, "Please Login again");
        }
    }

}