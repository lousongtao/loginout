package com.zheng.upms.dao.model;

import java.io.Serializable;

public class UpmsUser implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 帐号
     *
     * @mbg.generated
     */
    private String loginname;

    /**
     * 密码MD5(密码+盐)
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 盐
     *
     * @mbg.generated
     */
    private String salt;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    private String realname;

    /**
     * 头像
     *
     * @mbg.generated
     */
    private String avatar;

    /**
     * 电话
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 性别 1男 0女
     *
     * @mbg.generated
     */
    private Byte sex;

    /**
     * 状态(0:正常,1:锁定)
     *
     * @mbg.generated
     */
    private Byte locked;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long createTime;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private Integer parentId;

    /**
     * 底薪 单位(分)
     *
     * @mbg.generated
     */
    private Long baseSalary;

    /**
     * 每小时薪酬,单位(分)
     *
     * @mbg.generated
     */
    private Long perSalary;

    /**
     * 是否参与排班(0=不参与，1=参与)
     *
     * @mbg.generated
     */
    private Byte schedulestatus;

    /**
     * type, emulate
     *
     * @mbg.generated
     */
    private String type;

    /**
     * work type(0=full time; 1=parttime; 2=Casual)
     *
     * @mbg.generated
     */
    private Byte workType;

    private Long birth;

    private Long startWorkday;

    private String visaType;

    private Long visaExpire;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Long baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Long getPerSalary() {
        return perSalary;
    }

    public void setPerSalary(Long perSalary) {
        this.perSalary = perSalary;
    }

    public Byte getSchedulestatus() {
        return schedulestatus;
    }

    public void setSchedulestatus(Byte schedulestatus) {
        this.schedulestatus = schedulestatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getWorkType() {
        return workType;
    }

    public void setWorkType(Byte workType) {
        this.workType = workType;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Long getStartWorkday() {
        return startWorkday;
    }

    public void setStartWorkday(Long startWorkday) {
        this.startWorkday = startWorkday;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public Long getVisaExpire() {
        return visaExpire;
    }

    public void setVisaExpire(Long visaExpire) {
        this.visaExpire = visaExpire;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", loginname=").append(loginname);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", realname=").append(realname);
        sb.append(", avatar=").append(avatar);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", locked=").append(locked);
        sb.append(", createTime=").append(createTime);
        sb.append(", parentId=").append(parentId);
        sb.append(", baseSalary=").append(baseSalary);
        sb.append(", perSalary=").append(perSalary);
        sb.append(", schedulestatus=").append(schedulestatus);
        sb.append(", type=").append(type);
        sb.append(", workType=").append(workType);
        sb.append(", birth=").append(birth);
        sb.append(", startWorkday=").append(startWorkday);
        sb.append(", visaType=").append(visaType);
        sb.append(", visaExpire=").append(visaExpire);
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
        UpmsUser other = (UpmsUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getLoginname() == null ? other.getLoginname() == null : this.getLoginname().equals(other.getLoginname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getBaseSalary() == null ? other.getBaseSalary() == null : this.getBaseSalary().equals(other.getBaseSalary()))
            && (this.getPerSalary() == null ? other.getPerSalary() == null : this.getPerSalary().equals(other.getPerSalary()))
            && (this.getSchedulestatus() == null ? other.getSchedulestatus() == null : this.getSchedulestatus().equals(other.getSchedulestatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getWorkType() == null ? other.getWorkType() == null : this.getWorkType().equals(other.getWorkType()))
            && (this.getBirth() == null ? other.getBirth() == null : this.getBirth().equals(other.getBirth()))
            && (this.getStartWorkday() == null ? other.getStartWorkday() == null : this.getStartWorkday().equals(other.getStartWorkday()))
            && (this.getVisaType() == null ? other.getVisaType() == null : this.getVisaType().equals(other.getVisaType()))
            && (this.getVisaExpire() == null ? other.getVisaExpire() == null : this.getVisaExpire().equals(other.getVisaExpire()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getLoginname() == null) ? 0 : getLoginname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getBaseSalary() == null) ? 0 : getBaseSalary().hashCode());
        result = prime * result + ((getPerSalary() == null) ? 0 : getPerSalary().hashCode());
        result = prime * result + ((getSchedulestatus() == null) ? 0 : getSchedulestatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getWorkType() == null) ? 0 : getWorkType().hashCode());
        result = prime * result + ((getBirth() == null) ? 0 : getBirth().hashCode());
        result = prime * result + ((getStartWorkday() == null) ? 0 : getStartWorkday().hashCode());
        result = prime * result + ((getVisaType() == null) ? 0 : getVisaType().hashCode());
        result = prime * result + ((getVisaExpire() == null) ? 0 : getVisaExpire().hashCode());
        return result;
    }
}