package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McUserPositionMapper;
import com.zheng.ucenter.dao.model.McUserPosition;
import com.zheng.ucenter.dao.model.McUserPositionExample;

/**
* 降级实现McUserPositionService接口
* Created by shuzheng on 2018/6/29.
*/
public class McUserPositionServiceMock extends BaseServiceMock<McUserPositionMapper, McUserPosition, McUserPositionExample> implements McUserPositionService {

}
