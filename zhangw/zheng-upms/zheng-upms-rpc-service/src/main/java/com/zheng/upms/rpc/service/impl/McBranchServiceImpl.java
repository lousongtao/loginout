package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.upms.dao.mapper.McBranchMapper;
import com.zheng.upms.dao.model.McBranch;
import com.zheng.upms.dao.model.McBranchExample;
import com.zheng.upms.rpc.api.McBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McBranchService实现
* Created by shuzheng on 2018/8/26.
*/
@Service
@Transactional
@BaseService
public class McBranchServiceImpl extends BaseServiceImpl<McBranchMapper, McBranch, McBranchExample> implements McBranchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McBranchServiceImpl.class);

    @Autowired
    McBranchMapper mcBranchMapper;

}