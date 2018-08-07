package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McCountWeekMapper;
import com.zheng.ucenter.dao.model.McCountWeek;
import com.zheng.ucenter.dao.model.McCountWeekExample;
import com.zheng.ucenter.rpc.api.McCountWeekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McCountWeekService实现
* Created by shuzheng on 2018/7/24.
*/
@Service
@Transactional
@BaseService
public class McCountWeekServiceImpl extends BaseServiceImpl<McCountWeekMapper, McCountWeek, McCountWeekExample> implements McCountWeekService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McCountWeekServiceImpl.class);

    @Autowired
    McCountWeekMapper mcCountWeekMapper;

}