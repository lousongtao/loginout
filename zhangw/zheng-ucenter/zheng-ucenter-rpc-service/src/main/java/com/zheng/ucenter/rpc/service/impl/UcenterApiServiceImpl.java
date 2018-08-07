package com.zheng.ucenter.rpc.service.impl;

import com.zheng.ucenter.rpc.mapper.UcenterApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zheng.ucenter.rpc.api.UcenterApiService;

/**
 * UcenterApiService实现
 * Created by shuzheng on 2017/6/19.
 */
@Service
@Transactional
public class UcenterApiServiceImpl implements UcenterApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UcenterApiServiceImpl.class);

    @Autowired
    private UcenterApiMapper ucenterApiMapper;
}