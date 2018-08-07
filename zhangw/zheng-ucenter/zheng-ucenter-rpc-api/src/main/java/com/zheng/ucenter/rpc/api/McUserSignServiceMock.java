package com.zheng.ucenter.rpc.api;

import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;

import java.util.List;

/**
* 降级实现McUserSignService接口
* Created by shuzheng on 2018/6/29.
*/
public class McUserSignServiceMock implements McUserSignService {

    @Override
    public int insertSignRecord(McUserSign mcUserSign, Integer parentId) {
        return 0;
    }

    @Override
    public List<McUserSign> selectSignRecordByExample(McUserSignExample example, Integer parentId, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public long countByExample(McUserSignExample example, Integer parentId) {
        return 0;
    }
}
