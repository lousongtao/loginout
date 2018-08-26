package com.zheng.upms.dao.vo;

import com.zheng.upms.dao.model.McGroup;
import com.zheng.upms.dao.model.UpmsUser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zw on 2018/7/17
 */
public class McGroupUser extends McGroup implements Serializable{

    //组内用户
    private List<UpmsUser> userList;

    public List<UpmsUser> getUserList() {
        return userList;
    }

    public void setUserList(List<UpmsUser> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "McGroupUser{" +
                "userList=" + userList +
                '}';
    }
}
