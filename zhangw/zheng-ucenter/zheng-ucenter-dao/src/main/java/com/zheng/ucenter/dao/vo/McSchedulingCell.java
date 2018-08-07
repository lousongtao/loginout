package com.zheng.ucenter.dao.vo;

import java.io.Serializable;

import com.zheng.ucenter.dao.model.McSchedulePlan;

/**
 * Created by zw on 2018/7/26
 * 排班单元格数据
 */
public class McSchedulingCell extends McSchedulePlan implements Serializable {
    //组名
    private String name;
    //标识颜色
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "McSchedulingCell{" + "name='" + name + '\'' + ", color='" + color + '\'' + '}';
    }
}
