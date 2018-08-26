package com.zheng.upms.dao.model;

import java.io.Serializable;

public class McUserGroup implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 商户下的用户id
     *
     * @mbg.generated
     */
    private Integer mcUserId;

    /**
     * 组id
     *
     * @mbg.generated
     */
    private Integer mcGroupId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMcUserId() {
        return mcUserId;
    }

    public void setMcUserId(Integer mcUserId) {
        this.mcUserId = mcUserId;
    }

    public Integer getMcGroupId() {
        return mcGroupId;
    }

    public void setMcGroupId(Integer mcGroupId) {
        this.mcGroupId = mcGroupId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mcUserId=").append(mcUserId);
        sb.append(", mcGroupId=").append(mcGroupId);
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
        McUserGroup other = (McUserGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMcUserId() == null ? other.getMcUserId() == null : this.getMcUserId().equals(other.getMcUserId()))
            && (this.getMcGroupId() == null ? other.getMcGroupId() == null : this.getMcGroupId().equals(other.getMcGroupId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMcUserId() == null) ? 0 : getMcUserId().hashCode());
        result = prime * result + ((getMcGroupId() == null) ? 0 : getMcGroupId().hashCode());
        return result;
    }
}