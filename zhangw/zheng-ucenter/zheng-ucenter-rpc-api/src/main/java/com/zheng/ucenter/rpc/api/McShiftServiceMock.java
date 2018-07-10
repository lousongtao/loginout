package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McShiftMapper;
import com.zheng.ucenter.dao.model.McShift;
import com.zheng.ucenter.dao.model.McShiftExample;

/**
* 降级实现McShiftService接口
* Created by shuzheng on 2018/6/29.
*/
public class McShiftServiceMock extends BaseServiceMock<McShiftMapper, McShift, McShiftExample> implements McShiftService {

}
