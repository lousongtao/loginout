package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.upms.dao.mapper.McScheduleplanTemplatedetailMapper;
import com.zheng.upms.dao.model.McScheduleplanTemplatedetail;
import com.zheng.upms.dao.model.McScheduleplanTemplatedetailExample;
import com.zheng.upms.rpc.api.McScheduleplanTemplatedetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McScheduleplanTemplatedetailService实现
* Created by shuzheng on 2018/9/15.
*/
@Service
@Transactional
@BaseService
public class McScheduleplanTemplatedetailServiceImpl extends BaseServiceImpl<McScheduleplanTemplatedetailMapper, McScheduleplanTemplatedetail, McScheduleplanTemplatedetailExample> implements McScheduleplanTemplatedetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McScheduleplanTemplatedetailServiceImpl.class);

    @Autowired
    McScheduleplanTemplatedetailMapper mcScheduleplanTemplatedetailMapper;

}