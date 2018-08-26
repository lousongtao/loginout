package com.zheng.upms.server.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zw on 2018/7/25
 */
public class SchedulingForm implements Serializable {

    private Date pageToday;
    private Date pageMonday;
    private Date pageSunday;

    public Date getPageToday() {
        return pageToday;
    }

    public void setPageToday(Date pageToday) {
        this.pageToday = pageToday;
    }

    public Date getPageMonday() {
        return pageMonday;
    }

    public void setPageMonday(Date pageMonday) {
        this.pageMonday = pageMonday;
    }

    public Date getPageSunday() {
        return pageSunday;
    }

    public void setPageSunday(Date pageSunday) {
        this.pageSunday = pageSunday;
    }

    @Override
    public String toString() {
        return "SchedulingForm{" + "pageToday=" + pageToday + ", pageMonday=" + pageMonday
               + ", pageSunday=" + pageSunday + '}';
    }
}
