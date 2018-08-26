package com.zheng.upms.dao.model;

import java.io.Serializable;

public class McBranch implements Serializable {
    private Integer id;

    private String branchName;

    private Float gpsX;

    private Float gpsY;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Float getGpsX() {
        return gpsX;
    }

    public void setGpsX(Float gpsX) {
        this.gpsX = gpsX;
    }

    public Float getGpsY() {
        return gpsY;
    }

    public void setGpsY(Float gpsY) {
        this.gpsY = gpsY;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", branchName=").append(branchName);
        sb.append(", gpsX=").append(gpsX);
        sb.append(", gpsY=").append(gpsY);
        sb.append(", userId=").append(userId);
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
        McBranch other = (McBranch) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBranchName() == null ? other.getBranchName() == null : this.getBranchName().equals(other.getBranchName()))
            && (this.getGpsX() == null ? other.getGpsX() == null : this.getGpsX().equals(other.getGpsX()))
            && (this.getGpsY() == null ? other.getGpsY() == null : this.getGpsY().equals(other.getGpsY()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBranchName() == null) ? 0 : getBranchName().hashCode());
        result = prime * result + ((getGpsX() == null) ? 0 : getGpsX().hashCode());
        result = prime * result + ((getGpsY() == null) ? 0 : getGpsY().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }
}