package com.turvo.acme.persistence.entity;

import java.util.Date;


public abstract class StandardEntity implements ACMEEntity, AuditableEntity {

    private long id;

    private Date createdOn;

    private Date updatedOn;

    private long createdBy;

    private long updatedBy;

    private long status;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setCreatedBy(long createdById) {
        this.createdBy = createdById;
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public void setUpdatedBy(long updatedById) {
        this.updatedBy = updatedById;
    }

    @Override
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public void setStatus(long statusId) {
        this.status = statusId;
    }

    @Override
    public long getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public Date getCreatedOn() {
        return this.createdOn;
    }

    @Override
    public long getUpdatedBy() {
        return this.updatedBy;
    }

    @Override
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    @Override
    public long getStatus() {
        return this.status;
    }


}
