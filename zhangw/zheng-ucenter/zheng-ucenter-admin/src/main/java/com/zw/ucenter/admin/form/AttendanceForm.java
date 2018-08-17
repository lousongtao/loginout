package com.zw.ucenter.admin.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zw on 2018/8/15
 * 考勤管理form
 */
public class AttendanceForm implements Serializable {
    private String  uName;
    private Integer groupId;
    private Date    pageFirstDay;
    private Date    pageLastDay;
    private Date    pageMonth;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getPageFirstDay() {
        return pageFirstDay;
    }

    public void setPageFirstDay(Date pageFirstDay) {
        this.pageFirstDay = pageFirstDay;
    }

    public Date getPageLastDay() {
        return pageLastDay;
    }

    public void setPageLastDay(Date pageLastDay) {
        this.pageLastDay = pageLastDay;
    }

    public Date getPageMonth() {
        return pageMonth;
    }

    public void setPageMonth(Date pageMonth) {
        this.pageMonth = pageMonth;
    }

    @Override
    public String toString() {
        return "AttendanceForm{" + "uName='" + uName + '\'' + ", groupId=" + groupId
               + ", pageFirstDay=" + pageFirstDay + ", pageLastDay=" + pageLastDay + ", pageMonth="
               + pageMonth + '}';
    }
}
