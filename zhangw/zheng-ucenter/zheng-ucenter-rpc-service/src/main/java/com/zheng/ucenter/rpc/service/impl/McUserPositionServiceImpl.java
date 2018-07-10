package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McUserPositionMapper;
import com.zheng.ucenter.dao.model.McUserPosition;
import com.zheng.ucenter.dao.model.McUserPositionExample;
import com.zheng.ucenter.rpc.api.McUserPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McUserPositionService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
@BaseService
public class McUserPositionServiceImpl extends BaseServiceImpl<McUserPositionMapper, McUserPosition, McUserPositionExample> implements McUserPositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McUserPositionServiceImpl.class);

    @Autowired
    McUserPositionMapper mcUserPositionMapper;

}