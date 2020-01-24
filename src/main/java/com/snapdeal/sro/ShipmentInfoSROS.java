package com.snapdeal.sro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ShipmentInfoSROS {

    private DropAddress dropAddress;
    private  String referenceCode;

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public DropAddress getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(DropAddress dropAddress) {
        this.dropAddress = dropAddress;
    }

    @Override
    public String toString() {
        return "ShipmentInfoSROS{" +
                "dropAddress=" + dropAddress +
                ", referenceCode='" + referenceCode + '\'' +
                '}';
    }
}
