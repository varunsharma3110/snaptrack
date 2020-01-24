package com.snapdeal.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "snaptrack_master")
public class SnapTrackMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private double custLat;
    private double custLong;
    private String otp;


    private String callStatus;
    private double feLat;
    private double feLong;
    private String callDuration;
    private Date created;
    private String rtoReason;
    private String dtReason;
    private double distance; // distance between courier and customer lat log
    private float customerRtoScore;
    private int customerCancellationTickets;
    private int probabilityRecommendation;
    private Boolean processed;

    public float getCustomerRtoScore() {
        return customerRtoScore;
    }

    public int getCustomerCancellationTickets() {
        return customerCancellationTickets;
    }

    public int getProbabilityRecommendation() {
        return probabilityRecommendation;
    }

    public void setCustomerRtoScore(float customerRtoScore) {
        this.customerRtoScore = customerRtoScore;
    }

    public void setProbabilityRecommendation(int probabilityRecommendation) {
        this.probabilityRecommendation = probabilityRecommendation;
    }

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

    public double getCustLat() {
        return custLat;
    }

    public void setCustLat(double custLat) {
        this.custLat = custLat;
    }

    public double getCustLong() {
        return custLong;
    }

    public void setCustLong(double custLong) {
        this.custLong = custLong;
    }

    public double getFeLat() {
        return feLat;
    }

    public void setFeLat(double feLat) {
        this.feLat = feLat;
    }

    public double getFeLong() {
        return feLong;
    }

    public void setFeLong(double feLong) {
        this.feLong = feLong;
    }

    @Override
    public String toString() {
        return "SnapTrackMaster{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", custLat=" + custLat +
                ", custLong=" + custLong +
                ", otp='" + otp + '\'' +
                ", callStatus='" + callStatus + '\'' +
                ", feLat=" + feLat +
                ", feLong=" + feLong +
                ", callDuration='" + callDuration + '\'' +
                ", created=" + created +
                ", rtoReason='" + rtoReason + '\'' +
                ", dtReason='" + dtReason + '\'' +
                ", distance=" + distance +
                '}';
    }
}
