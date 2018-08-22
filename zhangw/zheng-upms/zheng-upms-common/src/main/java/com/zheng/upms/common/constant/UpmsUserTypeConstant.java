package com.zheng.upms.common.constant;

/**
 * Created by zw on 2018/8/20
 * 用户类型枚举
 */
public enum UpmsUserTypeConstant {
    //管理员
    administrators("administrators", "administrators"),
    //客户
    customer("customer","customer"),
    //客户的员工
    staff("staff","staff");

    private String code;

    private String message;

    UpmsUserTypeConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
