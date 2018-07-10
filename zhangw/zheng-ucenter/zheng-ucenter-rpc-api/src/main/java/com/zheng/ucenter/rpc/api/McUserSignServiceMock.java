package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McUserSignMapper;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;

/**
* 降级实现McUserSignService接口
* Created by shuzheng on 2018/6/29.
*/
public class McUserSignServiceMock extends BaseServiceMock<McUserSignMapper, McUserSign, McUserSignExample> implements McUserSignService {

}
