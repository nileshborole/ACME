package com.turvo.acme.entity.logistic;

import com.turvo.acme.entity.EntityQuery;

public enum LogisticQuery implements EntityQuery {

    ACTIVE_VEHICLE_SELECT("active_vehicle_select");

    private String queryId;

    private LogisticQuery(String queryId){
        this.queryId = "logistic."+queryId;
    }

    @Override
    public String query() {
        return this.queryId;
    }
}
