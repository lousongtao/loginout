package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class McUserSign implements Serializable {
    /**
     * 主键 支持高并发
     *
     * @mbg.generated
     */
    private Long signId;

    /**
     * 员工id
     *
     * @mbg.generated
     */
    private Integer uId;

    /**
     * 员工姓名
     *
     * @mbg.generated
     */
    private String uName;

    /**
     * 打卡时间
     *
     * @mbg.generated
     */
    private Date signTime;

    /**
     * 打卡类型(0 B端,1 C端,2 其他)
     *
     * @mbg.generated
     */
    private Integer signType;

    /**
     * 经度
     *
     * @mbg.generated
     */
    private BigDecimal lon;

    /**
     * 纬度
     *
     * @mbg.generated
     */
    private BigDecimal lat;

    private String signAddress;

    /**
     * 预留字段
     *
     * @mbg.generated
     */
    private String extend1;

    /**
     * 预留字段2
     *
     * @mbg.generated
     */
    private String extend2;

    /**
     * 预留字段3
     *
     * @mbg.generated
     */
    private String extend3;

    private static final long serialVersionUID = 1L;

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signId=").append(signId);
        sb.append(", uId=").append(uId);
        sb.append(", uName=").append(uName);
        sb.append(", signTime=").append(signTime);
        sb.append(", signType=").append(signType);
        sb.append(", lon=").append(lon);
        sb.append(", lat=").append(lat);
        sb.append(", signAddress=").append(signAddress);
        sb.append(", extend1=").append(extend1);
        sb.append(", extend2=").append(extend2);
        sb.append(", extend3=").append(extend3);
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
        return (this.getSignId() == null ? other.getSignId() == null : this.getSignId().equals(other.getSignId()))
            && (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getuName() == null ? other.getuName() == null : this.getuName().equals(other.getuName()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))
            && (this.getSignType() == null ? other.getSignType() == null : this.getSignType().equals(other.getSignType()))
            && (this.getLon() == null ? other.getLon() == null : this.getLon().equals(other.getLon()))
            && (this.getLat() == null ? other.getLat() == null : this.getLat().equals(other.getLat()))
            && (this.getSignAddress() == null ? other.getSignAddress() == null : this.getSignAddress().equals(other.getSignAddress()))
            && (this.getExtend1() == null ? other.getExtend1() == null : this.getExtend1().equals(other.getExtend1()))
            && (this.getExtend2() == null ? other.getExtend2() == null : this.getExtend2().equals(other.getExtend2()))
            && (this.getExtend3() == null ? other.getExtend3() == null : this.getExtend3().equals(other.getExtend3()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSignId() == null) ? 0 : getSignId().hashCode());
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getuName() == null) ? 0 : getuName().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getSignType() == null) ? 0 : getSignType().hashCode());
        result = prime * result + ((getLon() == null) ? 0 : getLon().hashCode());
        result = prime * result + ((getLat() == null) ? 0 : getLat().hashCode());
        result = prime * result + ((getSignAddress() == null) ? 0 : getSignAddress().hashCode());
        result = prime * result + ((getExtend1() == null) ? 0 : getExtend1().hashCode());
        result = prime * result + ((getExtend2() == null) ? 0 : getExtend2().hashCode());
        result = prime * result + ((getExtend3() == null) ? 0 : getExtend3().hashCode());
        return result;
    }
}