package com.zheng.upms.dao.model;

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
     * GPS X
     *
     * @mbg.generated
     */
    private Float signGpsx;

    /**
     * GPS Y
     *
     * @mbg.generated
     */
    private Float signGpsy;

    /**
     * sign tool, 0=browser; 1=phone app;
     *
     * @mbg.generated
     */
    private Integer signVia;

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

    public Float getSignGpsx() {
        return signGpsx;
    }

    public void setSignGpsx(Float signGpsx) {
        this.signGpsx = signGpsx;
    }

    public Float getSignGpsy() {
        return signGpsy;
    }

    public void setSignGpsy(Float signGpsy) {
        this.signGpsy = signGpsy;
    }

    public Integer getSignVia() {
        return signVia;
    }

    public void setSignVia(Integer signVia) {
        this.signVia = signVia;
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
        sb.append(", signGpsx=").append(signGpsx);
        sb.append(", signGpsy=").append(signGpsy);
        sb.append(", signVia=").append(signVia);
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
            && (this.getSignGpsx() == null ? other.getSignGpsx() == null : this.getSignGpsx().equals(other.getSignGpsx()))
            && (this.getSignGpsy() == null ? other.getSignGpsy() == null : this.getSignGpsy().equals(other.getSignGpsy()))
            && (this.getSignVia() == null ? other.getSignVia() == null : this.getSignVia().equals(other.getSignVia()));
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
        result = prime * result + ((getSignGpsx() == null) ? 0 : getSignGpsx().hashCode());
        result = prime * result + ((getSignGpsy() == null) ? 0 : getSignGpsy().hashCode());
        result = prime * result + ((getSignVia() == null) ? 0 : getSignVia().hashCode());
        return result;
    }
}