package com.zheng.upms.server.dto;

import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.server.ConstantValue;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 每天的数据, 即每个单元格数据
 */
public class SignReportCell implements Serializable {

    private double hours;

    private double salary;

    private String schedule;

    private String realwork;//根据具体打卡时间生成类似于 10.30-18.30 这种格式的字符串

    private Date signInTime;

    private Date signOutTime;

    private UpmsUser staff;

    public SignReportCell(UpmsUser staff){
        this.staff = staff;
    }

    public UpmsUser getStaff() {
        return staff;
    }

    public void setStaff(UpmsUser staff) {
        this.staff = staff;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) throws Exception {
        if (this.signInTime != null)
            throw new Exception("There are more times signin record");
        this.signInTime = signInTime;
        calculateValue();
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    private void calculateValue(){
        if (signInTime == null || signOutTime == null)
            return;
        DateFormat df = new SimpleDateFormat("HH:mm");
        realwork = df.format(signInTime) + " - " + df.format(signOutTime);
        hours = ((double)(signOutTime.getTime() - signInTime.getTime())) / ((double)1000 * 60 * 60);
        salary = staff.getPerSalary() * hours;

    }

    public void setSignOutTime(Date signOutTime) throws Exception {
        if (this.signOutTime != null)
            throw new Exception("There are more times signout record");
        this.signOutTime = signOutTime;
        calculateValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignReportCell that = (SignReportCell) o;
        return Double.compare(that.hours, hours) == 0 &&
                Double.compare(that.salary, salary) == 0 &&
                Objects.equals(schedule, that.schedule) &&
                Objects.equals(realwork, that.realwork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, salary, schedule, realwork);
    }

    @Override
    public String toString() {
        return hours + " hours\n"
                + "salary $" + salary + "\n"
                + schedule + "\n"
                + realwork;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRealwork() {
        return realwork;
    }

    public void setRealwork(String realwork) {
        this.realwork = realwork;
    }
}
