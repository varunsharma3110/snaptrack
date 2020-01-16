package com.snapdeal.sro;

public class GeoAngleSRO {

    private Double angle;
    private Character direction;

    public GeoAngleSRO() {

    }

    public GeoAngleSRO(Double angle, Character direction) {
        this.angle = angle;
        this.direction = direction;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Character getDirection() {
        return direction;
    }

    public void setDirection(Character direction) {
        this.direction = direction;
    }
}
