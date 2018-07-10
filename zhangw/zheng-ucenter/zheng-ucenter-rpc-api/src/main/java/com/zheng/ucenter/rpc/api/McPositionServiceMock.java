package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McPositionMapper;
import com.zheng.ucenter.dao.model.McPosition;
import com.zheng.ucenter.dao.model.McPositionExample;

/**
* 降级实现McPositionService接口
* Created by shuzheng on 2018/6/29.
*/
public class McPositionServiceMock extends BaseServiceMock<McPositionMapper, McPosition, McPositionExample> implements McPositionService {

}
