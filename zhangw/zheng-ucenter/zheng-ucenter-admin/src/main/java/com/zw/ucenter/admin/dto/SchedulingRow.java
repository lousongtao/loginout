package com.zw.ucenter.admin.dto;

import java.io.Serializable;

import com.zheng.ucenter.dao.model.McGroup;
import com.zheng.ucenter.dao.vo.McSchedulingCell;

/**
 * Created by zw on 2018/7/26
 */
public class SchedulingRow implements Serializable {
    //组数据
    private McGroup          mcGroup;
    //周一到七的数据
    private McSchedulingCell data1;
    private McSchedulingCell data2;
    private McSchedulingCell data3;
    private McSchedulingCell data4;
    private McSchedulingCell data5;
    private McSchedulingCell data6;
    private McSchedulingCell data7;

    public McGroup getMcGroup() {
        return mcGroup;
    }

    public void setMcGroup(McGroup mcGroup) {
        this.mcGroup = mcGroup;
    }

    public McSchedulingCell getData1() {
        return data1;
    }

    public void setData1(McSchedulingCell data1) {
        this.data1 = data1;
    }

    public McSchedulingCell getData2() {
        return data2;
    }

    public void setData2(McSchedulingCell data2) {
        this.data2 = data2;
    }

    public McSchedulingCell getData3() {
        return data3;
    }

    public void setData3(McSchedulingCell data3) {
        this.data3 = data3;
    }

    public McSchedulingCell getData4() {
        return data4;
    }

    public void setData4(McSchedulingCell data4) {
        this.data4 = data4;
    }

    public McSchedulingCell getData5() {
        return data5;
    }

    public void setData5(McSchedulingCell data5) {
        this.data5 = data5;
    }

    public McSchedulingCell getData6() {
        return data6;
    }

    public void setData6(McSchedulingCell data6) {
        this.data6 = data6;
    }

    public McSchedulingCell getData7() {
        return data7;
    }

    public void setData7(McSchedulingCell data7) {
        this.data7 = data7;
    }

    @Override
    public String toString() {
        return "SchedulingRow{" + "mcGroup=" + mcGroup + ", data1=" + data1 + ", data2=" + data2
               + ", data3=" + data3 + ", data4=" + data4 + ", data5=" + data5 + ", data6=" + data6
               + ", data7=" + data7 + '}';
    }
}
