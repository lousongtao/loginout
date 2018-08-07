package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McCountDayMapper;
import com.zheng.ucenter.dao.model.McCountDay;
import com.zheng.ucenter.dao.model.McCountDayExample;
import com.zheng.ucenter.rpc.api.McCountDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McCountDayService实现
* Created by shuzheng on 2018/7/24.
*/
@Service
@Transactional
@BaseService
public class McCountDayServiceImpl extends BaseServiceImpl<McCountDayMapper, McCountDay, McCountDayExample> implements McCountDayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McCountDayServiceImpl.class);

    @Autowired
    McCountDayMapper mcCountDayMapper;

}