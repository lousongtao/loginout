package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McCountDayMapper;
import com.zheng.upms.dao.model.McCountDay;
import com.zheng.upms.dao.model.McCountDayExample;

/**
* 降级实现McCountDayService接口
* Created by shuzheng on 2018/8/26.
*/
public class McCountDayServiceMock extends BaseServiceMock<McCountDayMapper, McCountDay, McCountDayExample> implements McCountDayService {

}
