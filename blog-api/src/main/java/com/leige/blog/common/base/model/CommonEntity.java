package com.leige.blog.common.base.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/1  11:14
 */
public abstract class CommonEntity implements Serializable {
    private static final long serialVersionUID = -5802669203869603329L;
    private Long creatorId;
    private Date creatorTime;
    private Integer updateCount;
    private Date updateTime;
    private Long updateId;
    private Integer isDel;
    @Transient
    private String creatorName;
    @Transient
    private String updateName;

    public CommonEntity() {
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
