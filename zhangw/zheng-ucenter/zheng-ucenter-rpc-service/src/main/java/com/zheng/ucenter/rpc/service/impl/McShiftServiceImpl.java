package com.zheng.ucenter.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.ucenter.dao.mapper.McShiftMapper;
import com.zheng.ucenter.dao.model.McShift;
import com.zheng.ucenter.dao.model.McShiftExample;
import com.zheng.ucenter.rpc.api.McShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McShiftService实现
* Created by shuzheng on 2018/6/29.
*/
@Service
@Transactional
@BaseService
public class McShiftServiceImpl extends BaseServiceImpl<McShiftMapper, McShift, McShiftExample> implements McShiftService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McShiftServiceImpl.class);

    @Autowired
    McShiftMapper mcShiftMapper;

}