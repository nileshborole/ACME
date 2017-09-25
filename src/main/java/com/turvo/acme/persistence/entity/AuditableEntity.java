package com.turvo.acme.persistence.entity;

import java.util.Date;

public interface AuditableEntity {

    public void setCreatedBy(long createdById);

    public void setCreatedOn(Date createdOn);

    public void setUpdatedBy(long updatedById);

    public void setUpdatedOn(Date updatedOn);

    public void setStatus(long statusId);


    public long getCreatedBy();

    public Date getCreatedOn();

    public long getUpdatedBy();

    public Date getUpdatedOn();

    public long getStatus();

}
