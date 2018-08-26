package com.zheng.upms.dao.model;

import java.io.Serializable;
import java.util.Date;

public class McCountDay implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 客户id
     *
     * @mbg.generated
     */
    private Integer mId;

    /**
     * 日期
     *
     * @mbg.generated
     */
    private Date countDate;

    /**
     * 人数
     *
     * @mbg.generated
     */
    private Integer personNum;

    /**
     * 支出薪资
     *
     * @mbg.generated
     */
    private Long outPay;

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

    public Date getCountDate() {
        return countDate;
    }

    public void setCountDate(Date countDate) {
        this.countDate = countDate;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Long getOutPay() {
        return outPay;
    }

    public void setOutPay(Long outPay) {
        this.outPay = outPay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mId=").append(mId);
        sb.append(", countDate=").append(countDate);
        sb.append(", personNum=").append(personNum);
        sb.append(", outPay=").append(outPay);
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
        McCountDay other = (McCountDay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getCountDate() == null ? other.getCountDate() == null : this.getCountDate().equals(other.getCountDate()))
            && (this.getPersonNum() == null ? other.getPersonNum() == null : this.getPersonNum().equals(other.getPersonNum()))
            && (this.getOutPay() == null ? other.getOutPay() == null : this.getOutPay().equals(other.getOutPay()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getCountDate() == null) ? 0 : getCountDate().hashCode());
        result = prime * result + ((getPersonNum() == null) ? 0 : getPersonNum().hashCode());
        result = prime * result + ((getOutPay() == null) ? 0 : getOutPay().hashCode());
        return result;
    }
}