package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.McScheduleplanTemplateMapper;
import com.zheng.upms.dao.model.McScheduleplanTemplate;
import com.zheng.upms.dao.model.McScheduleplanTemplateExample;

import java.util.Date;

/**
* 降级实现McScheduleplanTemplateService接口
* Created by shuzheng on 2018/9/15.
*/
public class McScheduleplanTemplateServiceMock extends BaseServiceMock<McScheduleplanTemplateMapper, McScheduleplanTemplate, McScheduleplanTemplateExample> implements McScheduleplanTemplateService {

    @Override
    public int createScheduleTemplate(String name, Date sDate, Date eDate, int userId, int branchId) {
        return 0;
    }
}
