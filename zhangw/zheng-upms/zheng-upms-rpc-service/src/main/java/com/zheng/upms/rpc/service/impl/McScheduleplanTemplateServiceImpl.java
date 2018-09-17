package com.zheng.upms.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.zheng.upms.dao.mapper.McScheduleplanTemplateMapper;
import com.zheng.upms.dao.model.McScheduleplanTemplate;
import com.zheng.upms.dao.model.McScheduleplanTemplateExample;
import com.zheng.upms.dao.model.McScheduleplanTemplatedetail;
import com.zheng.upms.dao.vo.McSchedulingCell;
import com.zheng.upms.rpc.api.McSchedulePlanService;
import com.zheng.upms.rpc.api.McScheduleplanTemplateService;
import com.zheng.upms.rpc.api.McScheduleplanTemplatedetailService;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* McScheduleplanTemplateService实现
* Created by shuzheng on 2018/9/15.
*/
@Service
@Transactional
@BaseService
public class McScheduleplanTemplateServiceImpl extends BaseServiceImpl<McScheduleplanTemplateMapper, McScheduleplanTemplate, McScheduleplanTemplateExample> implements McScheduleplanTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(McScheduleplanTemplateServiceImpl.class);

    @Autowired
    McScheduleplanTemplateMapper mcScheduleplanTemplateMapper;

    @Autowired
    McScheduleplanTemplatedetailService mcScheduleplanTemplatedetailService;

    @Autowired
    UpmsApiService upmsApiService;

    @Autowired
    McSchedulePlanService mcSchedulePlanService;

    @Autowired
    UpmsApiMapper upmsApiMapper;
    @Override
    public int createScheduleTemplate(String name, Date sDate, Date eDate, int userId, int branchId) {
        McScheduleplanTemplate temp = new McScheduleplanTemplate();
        temp.setName(name);
        temp.setUserId(userId);
        int account = upmsApiMapper.insertScheduleTemplate(temp);
        if (account == 0){
            return 0;
        }
        List<McSchedulingCell> mcSchedulePlans = mcSchedulePlanService.selectDataByDate(sDate, eDate, userId, branchId);
        if (mcSchedulePlans != null){
            Calendar c = Calendar.getInstance();
            for (int i = 0; i < mcSchedulePlans.size(); i++) {
                McSchedulingCell cell = mcSchedulePlans.get(i);
                Date d = cell.getSchedulingDate();
                c.setTime(d);
                McScheduleplanTemplatedetail detail = new McScheduleplanTemplatedetail();
                detail.setGroupId(cell.getGroupId());
                detail.setPeriodTime(cell.getPeriodTime());
                detail.setStaffId(cell.getuId());
                detail.setTemplateId(temp.getId());
                detail.setWeekday(c.get(Calendar.DAY_OF_WEEK));
                mcScheduleplanTemplatedetailService.insert(detail);
            }
        }
        return 1;
    }
}