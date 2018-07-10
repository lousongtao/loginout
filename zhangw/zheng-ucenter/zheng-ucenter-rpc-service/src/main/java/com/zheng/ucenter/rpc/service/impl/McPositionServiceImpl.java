package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McPositionMapper;
import com.zheng.ucenter.dao.model.McPosition;
import com.zheng.ucenter.dao.model.McPositionExample;
import com.zheng.ucenter.rpc.api.McPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McPositionService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
@BaseService
public class McPositionServiceImpl extends BaseServiceImpl<McPositionMapper, McPosition, McPositionExample> implements McPositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McPositionServiceImpl.class);

    @Autowired
    McPositionMapper mcPositionMapper;

}