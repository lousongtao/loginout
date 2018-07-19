package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class McShift implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 商户id
     *
     * @mbg.generated
     */
    private Integer mId;

    /**
     * 班次名称
     *
     * @mbg.generated
     */
    private String planName;

    /**
     * 班次 类型 0工作 1非工作
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 排班颜色
     *
     * @mbg.generated
     */
    private String color;

    /**
     * 总时长 (单位:小时)
     *
     * @mbg.generated
     */
    private BigDecimal totalTime;

    /**
     * 备注
     *
     * @mbg.generated
     */
    private String mark;

    /**
     * 起止时间
     *
     * @mbg.generated
     */
    private String periodTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mId=").append(mId);
        sb.append(", planName=").append(planName);
        sb.append(", type=").append(type);
        sb.append(", color=").append(color);
        sb.append(", totalTime=").append(totalTime);
        sb.append(", mark=").append(mark);
        sb.append(", periodTime=").append(periodTime);
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
        McShift other = (McShift) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getTotalTime() == null ? other.getTotalTime() == null : this.getTotalTime().equals(other.getTotalTime()))
            && (this.getMark() == null ? other.getMark() == null : this.getMark().equals(other.getMark()))
            && (this.getPeriodTime() == null ? other.getPeriodTime() == null : this.getPeriodTime().equals(other.getPeriodTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getTotalTime() == null) ? 0 : getTotalTime().hashCode());
        result = prime * result + ((getMark() == null) ? 0 : getMark().hashCode());
        result = prime * result + ((getPeriodTime() == null) ? 0 : getPeriodTime().hashCode());
        return result;
    }
}