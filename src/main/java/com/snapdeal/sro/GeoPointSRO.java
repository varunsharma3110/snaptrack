package com.snapdeal.sro;

public class GeoPointSRO {

    private GeoAngleSRO lattitude;
    private GeoAngleSRO longitude;

    public GeoAngleSRO getLattitude() {
        return lattitude;
    }

    public void setLattitude(GeoAngleSRO lattitude) {
        this.lattitude = lattitude;
    }

    public GeoAngleSRO getLongitude() {
        return longitude;
    }

    public void setLongitude(GeoAngleSRO longitude) {
        this.longitude = longitude;
    }

    public GeoPointSRO() {
    }

    public GeoPointSRO(GeoAngleSRO lattitude, GeoAngleSRO longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }
}
