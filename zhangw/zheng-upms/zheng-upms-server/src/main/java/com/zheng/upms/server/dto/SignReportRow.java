package com.zheng.upms.server.dto;

import com.zheng.upms.dao.model.McUserSign;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.common.constant.ConstantValue;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class SignReportRow implements Serializable {
    private UpmsUser staff;
    private SignReportCell monday;
    private SignReportCell tuesday;
    private SignReportCell wednesday;
    private SignReportCell thursday;
    private SignReportCell friday;
    private SignReportCell saturday;
    private SignReportCell sunday;
    private SignReportCell total;

    public SignReportRow(UpmsUser staff){
        this.staff = staff;
        monday = new SignReportCell(staff);
        tuesday = new SignReportCell(staff);
        wednesday = new SignReportCell(staff);
        thursday = new SignReportCell(staff);
        friday = new SignReportCell(staff);
        saturday = new SignReportCell(staff);
        sunday = new SignReportCell(staff);
        total = new SignReportCell(staff);
    }
    public UpmsUser getStaff() {
        return staff;
    }

    public void setStaff(UpmsUser staff) {
        this.staff = staff;
    }

    public SignReportCell getMonday() {
        return monday;
    }

    public void setMonday(SignReportCell monday) {
        this.monday = monday;
    }

    public SignReportCell getTuesday() {
        return tuesday;
    }

    public void setTuesday(SignReportCell tuesday) {
        this.tuesday = tuesday;
    }

    public SignReportCell getWednesday() {
        return wednesday;
    }

    public void setWednesday(SignReportCell wednesday) {
        this.wednesday = wednesday;
    }

    public SignReportCell getThursday() {
        return thursday;
    }

    public void setThursday(SignReportCell thursday) {
        this.thursday = thursday;
    }

    public SignReportCell getFriday() {
        return friday;
    }

    public void setFriday(SignReportCell friday) {
        this.friday = friday;
    }

    public SignReportCell getSaturday() {
        return saturday;
    }

    public void setSaturday(SignReportCell saturday) {
        this.saturday = saturday;
    }

    public SignReportCell getSunday() {
        return sunday;
    }

    public void setSunday(SignReportCell sunday) {
        this.sunday = sunday;
    }

    public SignReportCell getTotal() {
        return total;
    }

    public void setTotal(SignReportCell total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SignReportRow{" +
                "staffName='" + staff.getRealname() + '\'' +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thurday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignReportRow that = (SignReportRow) o;
        return Objects.equals(staff, that.staff) &&
                Objects.equals(monday, that.monday) &&
                Objects.equals(tuesday, that.tuesday) &&
                Objects.equals(wednesday, that.wednesday) &&
                Objects.equals(thursday, that.thursday) &&
                Objects.equals(friday, that.friday) &&
                Objects.equals(saturday, that.saturday) &&
                Objects.equals(sunday, that.sunday) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staff, monday, tuesday, wednesday, thursday, friday, saturday, sunday, total);
    }

    public void addSignRecord(McUserSign sign) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(sign.getSignTime());
        int week = c.get(Calendar.DAY_OF_WEEK);
        switch (week){
            case Calendar.MONDAY :
                recordSignReportCell(monday, sign);
                break;
            case Calendar.TUESDAY :
                recordSignReportCell(tuesday, sign);
                break;
            case Calendar.WEDNESDAY:
                recordSignReportCell(wednesday, sign);
                break;
            case Calendar.THURSDAY:
                recordSignReportCell(thursday, sign);
                break;
            case Calendar.FRIDAY:
                recordSignReportCell(friday, sign);
                break;
            case Calendar.SATURDAY :
                recordSignReportCell(saturday, sign);
                break;
            case Calendar.SUNDAY:
                recordSignReportCell(sunday, sign);
                break;
        }
        //re calculate the total
        total.setHours(monday.getHours() + tuesday.getHours() + wednesday.getHours() + thursday.getHours() + friday.getHours() + saturday.getHours() + sunday.getHours());
        total.setSalary(total.getHours() * staff.getPerSalary());
    }

    private void recordSignReportCell(SignReportCell cell, McUserSign sign) throws Exception {
        if (sign.getSignType() == ConstantValue.SIGNTYPE_IN){
            cell.setSignInTime(sign.getSignTime());
        } else {
            cell.setSignOutTime(sign.getSignTime());
        }
    }
}
