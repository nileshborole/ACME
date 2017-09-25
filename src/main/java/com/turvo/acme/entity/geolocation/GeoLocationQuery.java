package com.turvo.acme.entity.geolocation;

import com.turvo.acme.entity.EntityQuery;

public enum GeoLocationQuery implements EntityQuery {
    GEOPOINT_LOCATION_COUNT("geoPoint_location_count_select"),
    REMOVE_GEOPOINT_BY_LOCATION("geopoint_by_locationId_delete"),
    REMOVE_GEOPOINT_BY_ID("geopt_delete"),
    GEOPOINT_BY_ROUTE_SELECT("geopt_select"),
    GEOPOINT_ROUTE_MAPPING_INSERT("geoPoint_route_mapping_insert");

    private String queryId;

    private GeoLocationQuery(String queryId){
        this.queryId = "geolocation."+queryId;
    }

    @Override
    public String query() {
        return this.queryId;
    }
}
