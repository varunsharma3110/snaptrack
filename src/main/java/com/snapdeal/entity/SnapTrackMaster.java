package com.snapdeal.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "snaptrack_master")
public class SnapTrackMaster implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private double custlat;
    private double custlong;
    private String otp;
    private String callStatus;
    private double felat;
    private double felong;
    private String callDuration;
    private Date created;
    private String rtoReason;
    private String dtReason;
    private double distance; // distance between courier and customer lat log

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public String getRtoReason() {
        return rtoReason;
    }

    public void setRtoReason(String rtoReason) {
        this.rtoReason = rtoReason;
    }

    public double getCustlat() {
        return custlat;
    }

    public void setCustlat(double custlat) {
        this.custlat = custlat;
    }

    public double getCustlong() {
        return custlong;
    }

    public void setCustlong(double custlong) {
        this.custlong = custlong;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public double getFelat() {
        return felat;
    }

    public void setFelat(double felat) {
        this.felat = felat;
    }

    public double getFelong() {
        return felong;
    }

    public void setFelong(double felong) {
        this.felong = felong;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDtReason() {
        return dtReason;
    }

    public void setDtReason(String dtReason) {
        this.dtReason = dtReason;
    }

    @Override
    public String toString() {
        return "SnapTrackMaster{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", custlat=" + custlat +
                ", custlong=" + custlong +
                ", otp='" + otp + '\'' +
                ", callStatus='" + callStatus + '\'' +
                ", felat=" + felat +
                ", felong=" + felong +
                ", callDuration='" + callDuration + '\'' +
                ", created=" + created +
                ", rtoReason='" + rtoReason + '\'' +
                ", dtReason='" + dtReason + '\'' +
                ", distance=" + distance +
                '}';
    }
}
