package com.zheng.upms.dao.vo;

import com.zheng.upms.dao.model.McSchedulePlan;
import com.zheng.upms.dao.model.McUserSign;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zw on 2018/8/15
 */
public class McAttendanceCell extends McSchedulePlan implements Serializable {

    //当天排班对应的考勤数据
    List<McUserSign> signList;

    public List<McUserSign> getSignList() {
        return signList;
    }

    public void setSignList(List<McUserSign> signList) {
        this.signList = signList;
    }

    @Override
    public String toString() {
        return "McAttendanceCell{" + "signList=" + signList + '}';
    }
}
