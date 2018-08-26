package com.zheng.upms.dao.vo;

import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.UpmsUser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zw on 2018/7/17
 * 员工数据表
 */
public class StaffInfo extends UpmsUser implements Serializable {

    private List<McGroup> groupList;

    public List<McGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<McGroup> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return "StaffInfo{" + "groupList=" + groupList + '}';
    }
}
