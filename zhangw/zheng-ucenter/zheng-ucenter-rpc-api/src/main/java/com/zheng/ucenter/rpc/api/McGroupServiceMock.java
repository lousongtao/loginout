package com.zheng.ucenter.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.ucenter.dao.mapper.McGroupMapper;
import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.model.McGroupExample;
import io.swagger.models.auth.In;

/**
* 降级实现McGroupService接口
* Created by shuzheng on 2018/7/16.
*/
public class McGroupServiceMock extends BaseServiceMock<McGroupMapper, McGroup, McGroupExample> implements McGroupService {

    @Override
    public int checkAndDeleteById(Integer ids) {
        return 0;
    }
}

