package com.zheng.upms.server.controller.manage;

import com.zheng.common.base.BaseController;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.rpc.api.McUserSignService;
import com.zheng.upms.client.util.UserUtils;
import com.zheng.upms.dao.model.*;
import com.zheng.upms.rpc.api.UpmsRoleService;
import com.zheng.upms.rpc.api.UpmsUserRoleService;
import com.zheng.upms.rpc.api.UpmsUserService;
import com.zheng.upms.server.ConstantValue;
import com.zheng.upms.server.conf.SeqKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequiresPermissions("ucenter:sign:write")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public Object signIn() {
        return sign(ConstantValue.SIGNTYPE_IN, ConstantValue.SIGNVIA_BROWSER);
    }

    @ApiOperation(value = "网页端员工签到")
    @RequiresPermissions("ucenter:sign:write")
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public Object signOut() {
        return sign(ConstantValue.SIGNTYPE_OUT, ConstantValue.SIGNVIA_BROWSER);
    }

    private Object sign(int type, int signVia){
        UpmsUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            McUserSign mcUserSign = new McUserSign();
            mcUserSign.setSignId(SeqKit.genId());
            mcUserSign.setSignTime(new Date());
            mcUserSign.setSignType(type);
            mcUserSign.setSignVia(signVia);
            mcUserSign.setuId(currentUser.getUserId());
            mcUserSign.setuName(currentUser.getRealname());
            int signRecord = mcUserSignService.insertSignRecord(mcUserSign,
                currentUser.getParentId());
            LOGGER.info(currentUser.getRealname() + " sign success");
            return new UpmsResult(UpmsResultConstant.SUCCESS, signRecord);
        } else {
            return new UpmsResult(UpmsResultConstant.FAILED, "Please Login again");
        }
    }

}
