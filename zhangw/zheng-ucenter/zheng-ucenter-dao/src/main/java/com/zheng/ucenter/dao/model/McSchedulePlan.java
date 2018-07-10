package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.util.Date;

public class McSchedulePlan implements Serializable {
    private Long id;

    /**
     * 商户id
     *
     * @mbg.generated
     */
    private Integer mid;

    /**
     * 排班人id
     *
     * @mbg.generated
     */
    private Integer sid;

    /**
     * 员工id
     *
     * @mbg.generated
     */
    private Integer uid;

    /**
     * 员工姓名
     *
     * @mbg.generated
     */
    private String uName;

    /**
     * 日期
     *
     * @mbg.generated
     */
    private Date day;

    /**
     * 班次id
     *
     * @mbg.generated
     */
    private Integer shiftId;

    /**
     * 班次名称
     *
     * @mbg.generated
     */
    private String planName;

    /**
     * 是否完成(0未知,1已完成,2未完成,3其他)
     *
     * @mbg.generated
     */
    private Byte check;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Byte getCheck() {
        return check;
    }

    public void setCheck(Byte check) {
        this.check = check;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", sid=").append(sid);
        sb.append(", uid=").append(uid);
        sb.append(", uName=").append(uName);
        sb.append(", day=").append(day);
        sb.append(", shiftId=").append(shiftId);
        sb.append(", planName=").append(planName);
        sb.append(", check=").append(check);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        McSchedulePlan other = (McSchedulePlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getuName() == null ? other.getuName() == null : this.getuName().equals(other.getuName()))
            && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()))
            && (this.getShiftId() == null ? other.getShiftId() == null : this.getShiftId().equals(other.getShiftId()))
            && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
            && (this.getCheck() == null ? other.getCheck() == null : this.getCheck().equals(other.getCheck()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getuName() == null) ? 0 : getuName().hashCode());
        result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
        result = prime * result + ((getShiftId() == null) ? 0 : getShiftId().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getCheck() == null) ? 0 : getCheck().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}