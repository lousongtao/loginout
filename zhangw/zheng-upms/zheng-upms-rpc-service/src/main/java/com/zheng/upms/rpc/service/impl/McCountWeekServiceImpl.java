package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.upms.dao.mapper.McCountWeekMapper;
import com.zheng.upms.dao.model.McCountWeek;
import com.zheng.upms.dao.model.McCountWeekExample;
import com.zheng.upms.rpc.api.McCountWeekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McCountWeekService实现
* Created by shuzheng on 2018/8/26.
*/
@Service
@Transactional
@BaseService
public class McCountWeekServiceImpl extends BaseServiceImpl<McCountWeekMapper, McCountWeek, McCountWeekExample> implements McCountWeekService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McCountWeekServiceImpl.class);

    @Autowired
    McCountWeekMapper mcCountWeekMapper;

}