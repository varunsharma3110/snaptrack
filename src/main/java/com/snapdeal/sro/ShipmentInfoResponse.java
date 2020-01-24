package com.snapdeal.sro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class ShipmentInfoResponse {

    private boolean successful;

    private List<ShipmentInfoSROS> shipmentInfoSROS;

    public List<ShipmentInfoSROS> getShipmentInfoSROS() {
        return shipmentInfoSROS;
    }

    public void setShipmentInfoSROS(List<ShipmentInfoSROS> shipmentInfoSROS) {
        this.shipmentInfoSROS = shipmentInfoSROS;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public String toString() {
        return "shipmentInfoResponse{" +
                "successful=" + successful +
                ", shipmentInfoSROS=" + shipmentInfoSROS +
                '}';
    }
}
