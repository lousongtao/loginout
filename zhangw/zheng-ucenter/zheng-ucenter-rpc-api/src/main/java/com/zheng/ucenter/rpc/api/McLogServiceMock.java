package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McLogMapper;
import com.zheng.ucenter.dao.model.McLog;
import com.zheng.ucenter.dao.model.McLogExample;

/**
* 降级实现McLogService接口
* Created by shuzheng on 2018/6/29.
*/
public class McLogServiceMock extends BaseServiceMock<McLogMapper, McLog, McLogExample> implements McLogService {

}
