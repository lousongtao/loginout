package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McGroupMapper;
import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.McGroupExample;

/**
* 降级实现McGroupService接口
* Created by shuzheng on 2018/8/26.
*/
public class McGroupServiceMock extends BaseServiceMock<McGroupMapper, McGroup, McGroupExample> implements McGroupService {
    @Override
    public int checkAndDeleteById(Integer ids) {
        return 0;
    }
}
