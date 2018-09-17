package com.zheng.upms.dao.model;

import java.io.Serializable;

public class McScheduleplanTemplatedetail implements Serializable {
    private Integer id;

    /**
     * 员工的ID
     *
     * @mbg.generated
     */
    private Integer staffId;

    /**
     * 员工所在的组
     *
     * @mbg.generated
     */
    private Integer groupId;

    /**
     * 排班时间段, 如11.00-13.40
     *
     * @mbg.generated
     */
    private String periodTime;

    /**
     * 标记周几, 1=Sunday, 2=Monday.... 跟Calendar里面定义的一致
     *
     * @mbg.generated
     */
    private Integer weekday;

    private Integer templateId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", staffId=").append(staffId);
        sb.append(", groupId=").append(groupId);
        sb.append(", periodTime=").append(periodTime);
        sb.append(", weekday=").append(weekday);
        sb.append(", templateId=").append(templateId);
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
        McScheduleplanTemplatedetail other = (McScheduleplanTemplatedetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStaffId() == null ? other.getStaffId() == null : this.getStaffId().equals(other.getStaffId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getPeriodTime() == null ? other.getPeriodTime() == null : this.getPeriodTime().equals(other.getPeriodTime()))
            && (this.getWeekday() == null ? other.getWeekday() == null : this.getWeekday().equals(other.getWeekday()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStaffId() == null) ? 0 : getStaffId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getPeriodTime() == null) ? 0 : getPeriodTime().hashCode());
        result = prime * result + ((getWeekday() == null) ? 0 : getWeekday().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        return result;
    }
}