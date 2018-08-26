package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McLogMapper;
import com.zheng.upms.dao.model.McLog;
import com.zheng.upms.dao.model.McLogExample;

/**
* 降级实现McLogService接口
* Created by shuzheng on 2018/8/26.
*/
public class McLogServiceMock extends BaseServiceMock<McLogMapper, McLog, McLogExample> implements McLogService {

}
