package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class McUserSign implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 签到人id
     *
     * @mbg.generated
     */
    private Integer uid;

    /**
     * 签到时间
     *
     * @mbg.generated
     */
    private Date signTime;

    /**
     * 签到类型 (0:B端 1:C端  2 其他 )
     *
     * @mbg.generated
     */
    private Byte signType;

    /**
     * 经度
     *
     * @mbg.generated
     */
    private BigDecimal signLon;

    /**
     * 纬度
     *
     * @mbg.generated
     */
    private BigDecimal signLat;

    /**
     * 签到地点
     *
     * @mbg.generated
     */
    private String singAddress;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Byte getSignType() {
        return signType;
    }

    public void setSignType(Byte signType) {
        this.signType = signType;
    }

    public BigDecimal getSignLon() {
        return signLon;
    }

    public void setSignLon(BigDecimal signLon) {
        this.signLon = signLon;
    }

    public BigDecimal getSignLat() {
        return signLat;
    }

    public void setSignLat(BigDecimal signLat) {
        this.signLat = signLat;
    }

    public String getSingAddress() {
        return singAddress;
    }

    public void setSingAddress(String singAddress) {
        this.singAddress = singAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", signTime=").append(signTime);
        sb.append(", signType=").append(signType);
        sb.append(", signLon=").append(signLon);
        sb.append(", signLat=").append(signLat);
        sb.append(", singAddress=").append(singAddress);
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
        McUserSign other = (McUserSign) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))
            && (this.getSignType() == null ? other.getSignType() == null : this.getSignType().equals(other.getSignType()))
            && (this.getSignLon() == null ? other.getSignLon() == null : this.getSignLon().equals(other.getSignLon()))
            && (this.getSignLat() == null ? other.getSignLat() == null : this.getSignLat().equals(other.getSignLat()))
            && (this.getSingAddress() == null ? other.getSingAddress() == null : this.getSingAddress().equals(other.getSingAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getSignType() == null) ? 0 : getSignType().hashCode());
        result = prime * result + ((getSignLon() == null) ? 0 : getSignLon().hashCode());
        result = prime * result + ((getSignLat() == null) ? 0 : getSignLat().hashCode());
        result = prime * result + ((getSingAddress() == null) ? 0 : getSingAddress().hashCode());
        return result;
    }
}