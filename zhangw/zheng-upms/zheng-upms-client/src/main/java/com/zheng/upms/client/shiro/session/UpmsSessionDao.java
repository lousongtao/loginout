package com.zheng.upms.client.shiro.session;

import java.io.Serializable;
import java.util.*;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zheng.common.util.RedisUtil;
import com.zheng.upms.client.util.SerializableUtil;
import com.zheng.upms.common.constant.UpmsConstant;
import com.zheng.upms.common.constant.UpmsRedisKeyPrefix;

import redis.clients.jedis.Jedis;

/**
 * 基于redis的sessionDao，缓存共享session
 * Created by shuzheng on 2017/2/23.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static final Logger LOGGER                      = LoggerFactory
        .getLogger(UpmsSessionDao.class);
    // 会话key
    private final static String SYS_UPMS_SHIRO_SESSION_ID   = UpmsRedisKeyPrefix.SYS_UPMS_SHIRO_SESSION_ID;
    // 全局会话key
    private final static String SYS_UPMS_SERVER_SESSION_ID  = UpmsRedisKeyPrefix.SYS_UPMS_SERVER_SESSION_ID;
    // 全局会话列表key
    private final static String SYS_UPMS_SERVER_SESSION_IDS = UpmsRedisKeyPrefix.SYS_UPMS_SERVER_SESSION_IDS;
    // code key
    private final static String SYS_UPMS_SERVER_CODE        = UpmsRedisKeyPrefix.SYS_UPMS_SERVER_CODE;
    // 局部会话key
    private final static String SYS_UPMS_CLIENT_SESSION_ID  = UpmsRedisKeyPrefix.SYS_UPMS_CLIENT_SESSION_ID;
    // 单点同一个code所有局部会话key
    private final static String SYS_UPMS_CLIENT_SESSION_IDS = UpmsRedisKeyPrefix.SYS_UPMS_CLIENT_SESSION_IDS;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(SYS_UPMS_SHIRO_SESSION_ID + sessionId, SerializableUtil.serialize(session),
            (int) session.getTimeout() / 1000);
        LOGGER.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(SYS_UPMS_SHIRO_SESSION_ID + sessionId);
        LOGGER.debug("doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(SYS_UPMS_SHIRO_SESSION_ID + session.getId(),
            SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        // 更新ZHENG_UPMS_SERVER_SESSION_ID、ZHENG_UPMS_SERVER_CODE过期时间 TODO
        LOGGER.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String upmsType = ObjectUtils.toString(session.getAttribute(UpmsConstant.UPMS_TYPE));
        if ("client".equals(upmsType)) {
            // 删除局部会话和同一code注册的局部会话
            String code = RedisUtil.get(SYS_UPMS_CLIENT_SESSION_ID + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(SYS_UPMS_CLIENT_SESSION_ID + sessionId);
            jedis.srem(SYS_UPMS_CLIENT_SESSION_IDS + code, sessionId);
            jedis.close();
        }
        if ("server".equals(upmsType)) {
            // 当前全局会话code
            String code = RedisUtil.get(SYS_UPMS_SERVER_SESSION_ID + sessionId);
            // 清除全局会话
            RedisUtil.remove(SYS_UPMS_SERVER_SESSION_ID + sessionId);
            // 清除code校验值
            RedisUtil.remove(SYS_UPMS_SERVER_CODE + code);
            // 清除所有局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(SYS_UPMS_CLIENT_SESSION_IDS + code);
            for (String clientSessionId : clientSessionIds) {
                jedis.del(SYS_UPMS_CLIENT_SESSION_ID + clientSessionId);
                jedis.srem(SYS_UPMS_CLIENT_SESSION_IDS + code, clientSessionId);
            }
            LOGGER.debug("当前code={}，对应的注册系统个数：{}个", code,
                jedis.scard(SYS_UPMS_CLIENT_SESSION_IDS + code));
            jedis.close();
            // 维护会话id列表，提供会话分页管理
            RedisUtil.lrem(SYS_UPMS_SERVER_SESSION_IDS, 1, sessionId);
        }
        // 删除session
        RedisUtil.remove(SYS_UPMS_SHIRO_SESSION_ID + sessionId);
        LOGGER.debug("doUpdate >>>>> sessionId={}", sessionId);
    }

    /**
     * 获取会话列表
     * @param offset
     * @param limit
     * @return
     */
    public Map getActiveSessions(int offset, int limit) {
        Map sessions = new HashMap();
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        long total = jedis.llen(SYS_UPMS_SERVER_SESSION_IDS);
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(SYS_UPMS_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> rows = new ArrayList<>();
        for (String id : ids) {
            String session = RedisUtil.get(SYS_UPMS_SHIRO_SESSION_ID + id);
            // 过滤redis过期session
            if (null == session) {
                RedisUtil.lrem(SYS_UPMS_SERVER_SESSION_IDS, 1, id);
                total = total - 1;
                continue;
            }
            rows.add(SerializableUtil.deserialize(session));
        }
        jedis.close();
        sessions.put("total", total);
        sessions.put("rows", rows);
        return sessions;
    }

    /**
     * 强制退出
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = RedisUtil.get(SYS_UPMS_SHIRO_SESSION_ID + sessionId);
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(SYS_UPMS_SHIRO_SESSION_ID + sessionId,
                SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(SYS_UPMS_SHIRO_SESSION_ID + session.getId(),
            SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

}
