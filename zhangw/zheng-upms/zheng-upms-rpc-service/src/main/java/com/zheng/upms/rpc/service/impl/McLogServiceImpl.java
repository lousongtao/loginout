package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.upms.dao.mapper.McLogMapper;
import com.zheng.upms.dao.model.McLog;
import com.zheng.upms.dao.model.McLogExample;
import com.zheng.upms.rpc.api.McLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* McLogService实现
* Created by shuzheng on 2018/8/26.
*/
@Service
@Transactional
@BaseService
public class McLogServiceImpl extends BaseServiceImpl<McLogMapper, McLog, McLogExample> implements McLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McLogServiceImpl.class);

    @Autowired
    McLogMapper mcLogMapper;

}