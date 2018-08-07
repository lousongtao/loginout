package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McCountDayMapper;
import com.zheng.ucenter.dao.model.McCountDay;
import com.zheng.ucenter.dao.model.McCountDayExample;

/**
* 降级实现McCountDayService接口
* Created by shuzheng on 2018/7/24.
*/
public class McCountDayServiceMock extends BaseServiceMock<McCountDayMapper, McCountDay, McCountDayExample> implements McCountDayService {

}
