package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McSchedulePlanMapper;
import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McSchedulePlanExample;
import com.zheng.ucenter.rpc.api.McSchedulePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McSchedulePlanService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
@BaseService
public class McSchedulePlanServiceImpl extends BaseServiceImpl<McSchedulePlanMapper, McSchedulePlan, McSchedulePlanExample> implements McSchedulePlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McSchedulePlanServiceImpl.class);

    @Autowired
    McSchedulePlanMapper mcSchedulePlanMapper;

}