package com.zheng.upms.server.form;

import java.io.Serializable;
import java.util.Objects;

public class ScheduleTemplateForm implements Serializable {
    private int templateId;//tempalteId
    private String name;
    private String startDate;
    private String endDate;
    private int branchId;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleTemplateForm that = (ScheduleTemplateForm) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate);
    }

    @Override
    public String toString() {
        return "ScheduleTemplateForm{" +
                "name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
