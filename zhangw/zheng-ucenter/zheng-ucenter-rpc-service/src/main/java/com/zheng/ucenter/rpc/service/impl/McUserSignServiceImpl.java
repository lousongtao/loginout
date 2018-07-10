package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McUserSignMapper;
import com.zheng.ucenter.dao.model.McUserSign;
import com.zheng.ucenter.dao.model.McUserSignExample;
import com.zheng.ucenter.rpc.api.McUserSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McUserSignService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
@BaseService
public class McUserSignServiceImpl extends BaseServiceImpl<McUserSignMapper, McUserSign, McUserSignExample> implements McUserSignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McUserSignServiceImpl.class);

    @Autowired
    McUserSignMapper mcUserSignMapper;

}