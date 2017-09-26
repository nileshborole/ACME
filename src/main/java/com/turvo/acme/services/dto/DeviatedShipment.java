package com.turvo.acme.services.dto;

import java.util.Date;

public class DeviatedShipment {

    private long shipmentId;
    private long vehicleId;
    private long deviationPointId;
    private Date deviationTime;


    public long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getDeviationPointId() {
        return deviationPointId;
    }

    public void setDeviationPointId(long deviationPointId) {
        this.deviationPointId = deviationPointId;
    }

    public Date getDeviationTime() {
        return deviationTime;
    }

    public void setDeviationTime(Date deviationTime) {
        this.deviationTime = deviationTime;
    }
}
