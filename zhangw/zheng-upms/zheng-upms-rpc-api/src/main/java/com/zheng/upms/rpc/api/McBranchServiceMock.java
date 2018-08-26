package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McBranchMapper;
import com.zheng.upms.dao.model.McBranch;
import com.zheng.upms.dao.model.McBranchExample;

/**
* 降级实现McBranchService接口
* Created by shuzheng on 2018/8/26.
*/
public class McBranchServiceMock extends BaseServiceMock<McBranchMapper, McBranch, McBranchExample> implements McBranchService {

}
