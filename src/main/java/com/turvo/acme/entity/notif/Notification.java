package com.turvo.acme.entity.notif;

import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(queryPrefix = "notif", module = "notif", listener = "notificationListener")
public class Notification implements ACMEEntity {

    private long id;
    private String name;
    private long notificationType;
    private long notifMethTypeId;
    private long status;
    private Date createdOn;

    private Map<String, String> params;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(long notificationType) {
        this.notificationType = notificationType;
    }

    public long getNotifMethTypeId() {
        return notifMethTypeId;
    }

    public void setNotifMethTypeId(long notifMethTypeId) {
        this.notifMethTypeId = notifMethTypeId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public void addParam(String key, String value){
        if(this.params == null)
            this.params = new HashMap<>();

        this.params.put(key, value);
    }

}
