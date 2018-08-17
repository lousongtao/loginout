package com.zheng.ucenter.dao.vo;

import java.io.Serializable;
import java.util.List;

import com.zheng.ucenter.dao.model.McSchedulePlan;
import com.zheng.ucenter.dao.model.McUserSign;

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
