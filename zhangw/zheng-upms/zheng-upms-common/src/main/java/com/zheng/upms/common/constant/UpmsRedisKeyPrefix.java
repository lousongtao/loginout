package com.zheng.upms.common.constant;

import com.zheng.common.base.BaseConstants;

/**
 * Created by zw on 2018/6/28
 * redis  key 前缀
 */
public class UpmsRedisKeyPrefix extends BaseConstants {

    // 全局会话key
    public final static String SYS_UPMS_SERVER_SESSION_ID  = "sys-upms-server-session-id:";
    // 全局会话key列表
    public final static String SYS_UPMS_SERVER_SESSION_IDS = "sys-upms-server-session-ids";
    // code key
    public final static String SYS_UPMS_SERVER_CODE        = "sys-upms-server-code:";

    // shiro 会话key
    public final static String SYS_UPMS_SHIRO_SESSION_ID   = "sys-upms-shiro-session-id:";

    // 客户端局部会话key
    public final static String SYS_UPMS_CLIENT_SESSION_ID  = "sys-upms-client-session-id:";
    // 客户端 单点同一个code所有局部会话key
    public final static String SYS_UPMS_CLIENT_SESSION_IDS = "sys-upms-client-session-ids";
}
