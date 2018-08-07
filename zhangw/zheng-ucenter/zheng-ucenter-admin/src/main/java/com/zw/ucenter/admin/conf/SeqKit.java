package com.zw.ucenter.admin.conf;

import com.zheng.common.util.PropertiesFileUtil;
import com.zheng.common.util.key.SnowflakeIdWorker;

/**
 * Created by zw on 2018/7/24
 * 高并发主键获取
 */
public class SeqKit {

    private static SnowflakeIdWorker snowflakeIdWorker;

    private static void init() {
        String workId = PropertiesFileUtil.getInstance("zheng-admin-client")
            .get("zheng.admin.workId");
        String datacenterId = PropertiesFileUtil.getInstance("zheng-admin-client")
            .get("zheng.admin.datacenterId");
        snowflakeIdWorker = new SnowflakeIdWorker(Long.parseLong(workId),
            Long.parseLong(datacenterId));
    }

    /**
     * 生成序列
     * @return
     */
    public static long genId() {
        return snowflakeIdWorker.nextId();
    }
}
