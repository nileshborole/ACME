package com.turvo.acme.entity.notif;

import com.turvo.acme.entity.EntityQuery;

public enum NotificationQuery implements EntityQuery {

    NOTIF_PARAM_INSERT("notif_params_insert");


    private String queryId;

    private NotificationQuery(String queryId){
        this.queryId = "notif."+queryId;
    }

    @Override
    public String query() {
        return null;
    }
}
