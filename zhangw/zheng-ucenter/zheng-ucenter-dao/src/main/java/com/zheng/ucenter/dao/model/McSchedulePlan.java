package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class McSchedulePlan implements Serializable {
    private Integer           id;

    /**
     * 员工id
     *
     * @mbg.generated
     */
    private Integer           uId;

    /**
     * 员工姓名
     *
     * @mbg.generated
     */
    private String            uName;

    /**
     * 组ID
     *
     * @mbg.generated
     */
    private Integer           groupId;

    /**
     * 员工时薪
     *
     * @mbg.generated
     */
    private Long              perSalary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date              schedulingDate;

    /**
     * 起止时间
     *
     * @mbg.generated
     */
    private String            periodTime;

    /**
     * 结束时间
     *
     * @mbg.generated
     */
    private BigDecimal        totalTime;

    /**
     * 预计支付 单位:分
     *
     * @mbg.generated
     */
    private Long              estimatePay;

    /**
     * 结果记录(0未知,1已完成,2未完成)
     *
     * @mbg.generated
     */
    private Integer           result;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date              createTime;

    /**
     * 排班最后一次更新时间
     *
     * @mbg.generated
     */
    private Date              updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Long getPerSalary() {
        return perSalary;
    }

    public void setPerSalary(Long perSalary) {
        this.perSalary = perSalary;
    }

    public Date getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(Date schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public Long getEstimatePay() {
        return estimatePay;
    }

    public void setEstimatePay(Long estimatePay) {
        this.estimatePay = estimatePay;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uId=").append(uId);
        sb.append(", uName=").append(uName);
        sb.append(", groupId=").append(groupId);
        sb.append(", perSalary=").append(perSalary);
        sb.append(", schedulingDate=").append(schedulingDate);
        sb.append(", periodTime=").append(periodTime);
        sb.append(", totalTime=").append(totalTime);
        sb.append(", estimatePay=").append(estimatePay);
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
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
               && (this.getuId() == null ? other.getuId() == null
                   : this.getuId().equals(other.getuId()))
               && (this.getuName() == null ? other.getuName() == null
                   : this.getuName().equals(other.getuName()))
               && (this.getGroupId() == null ? other.getGroupId() == null
                   : this.getGroupId().equals(other.getGroupId()))
               && (this.getPerSalary() == null ? other.getPerSalary() == null
                   : this.getPerSalary().equals(other.getPerSalary()))
               && (this.getSchedulingDate() == null ? other.getSchedulingDate() == null
                   : this.getSchedulingDate().equals(other.getSchedulingDate()))
               && (this.getPeriodTime() == null ? other.getPeriodTime() == null
                   : this.getPeriodTime().equals(other.getPeriodTime()))
               && (this.getTotalTime() == null ? other.getTotalTime() == null
                   : this.getTotalTime().equals(other.getTotalTime()))
               && (this.getEstimatePay() == null ? other.getEstimatePay() == null
                   : this.getEstimatePay().equals(other.getEstimatePay()))
               && (this.getResult() == null ? other.getResult() == null
                   : this.getResult().equals(other.getResult()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null
                   : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null
                   : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getuName() == null) ? 0 : getuName().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getPerSalary() == null) ? 0 : getPerSalary().hashCode());
        result = prime * result
                 + ((getSchedulingDate() == null) ? 0 : getSchedulingDate().hashCode());
        result = prime * result + ((getPeriodTime() == null) ? 0 : getPeriodTime().hashCode());
        result = prime * result + ((getTotalTime() == null) ? 0 : getTotalTime().hashCode());
        result = prime * result + ((getEstimatePay() == null) ? 0 : getEstimatePay().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}