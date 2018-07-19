package com.zheng.ucenter.dao.model;

import java.io.Serializable;

import com.zheng.upms.dao.model.UpmsUser;

/**
 * Created by zw on 2018/7/17
 * 员工数据表
 */
public class StaffInfo extends UpmsUser implements Serializable {

    private Integer groupId;

    private String  groupName;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "StaffInfo{" + "groupId=" + groupId + ", groupName='" + groupName + '\'' + '}';
    }
}
