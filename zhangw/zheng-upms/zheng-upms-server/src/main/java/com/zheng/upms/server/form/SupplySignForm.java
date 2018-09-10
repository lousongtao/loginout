package com.zheng.upms.server.form;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SupplySignForm implements Serializable {
    private Date signTime;
    private int staffId;
    private int signType;

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "SupplySignForm{" +
                "signTime=" + signTime +
                ", staffId=" + staffId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplySignForm that = (SupplySignForm) o;
        return staffId == that.staffId &&
                Objects.equals(signTime, that.signTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signTime, staffId);
    }
}
