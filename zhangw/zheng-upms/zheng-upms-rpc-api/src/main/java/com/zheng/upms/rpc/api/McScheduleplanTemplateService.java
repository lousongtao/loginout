package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseService;
import com.zheng.upms.dao.model.McScheduleplanTemplate;
import com.zheng.upms.dao.model.McScheduleplanTemplateExample;

import java.util.Date;

/**
* McScheduleplanTemplateService接口
* Created by shuzheng on 2018/9/15.
*/
public interface McScheduleplanTemplateService extends BaseService<McScheduleplanTemplate, McScheduleplanTemplateExample> {

    /**
     * 从排班表拷贝数据, 生成一份模板表, 选择sDate到eDate中间的排班数据
     * @param name
     * @param sDate
     * @param eDate
     * @param userId
     * @return
     */
    int createScheduleTemplate(String name, Date sDate, Date eDate, int userId, int branchId);
}