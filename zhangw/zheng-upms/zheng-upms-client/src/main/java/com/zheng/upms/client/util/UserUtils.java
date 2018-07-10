package com.zheng.upms.client.util;

import com.alibaba.fastjson.JSON;
import com.zheng.common.base.BaseConstants;
import com.zheng.common.util.RedisUtil;
import com.zheng.upms.dao.model.UpmsUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zw on 2018/7/3
 */
public class UserUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    /**
     * 从redis中获取当前登录用户
     * @return
     */
    public static UpmsUser getCurrentUser() {
        try {
            String username =getCurrentUserName();
            String value = RedisUtil.hget(BaseConstants.SYS_UPMS_USERS_KEY, username);
            return JSON.parseObject(value, UpmsUser.class);
        } catch (Exception e) {
            LOGGER.error("获取当前用户失败");
            return null;
        }
    }

    /**
     * 获取当前登录用户id失败
     * @return
     */
    public static Integer getCurrentUserId() {
        UpmsUser upmsUser = getCurrentUser();
        if (upmsUser != null) {
            return upmsUser.getUserId();
        } else {
            LOGGER.error("缓存中获取当前用户ID失败");
            return null;
        }
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getCurrentUserName(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return username;
    }
}
