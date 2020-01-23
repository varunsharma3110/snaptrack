package com.snapdeal.snaptrack;
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
    private double latitude;
    private double longitude;
    private String otp;
    private String callerStatus;
    private String calledStatus;
    private String callDuration;
    private Date created;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCallerStatus() {
        return callerStatus;
    }

    public void setCallerStatus(String callerStatus) {
        this.callerStatus = callerStatus;
    }

    public String getCalledStatus() {
        return calledStatus;
    }

    public void setCalledStatus(String calledStatus) {
        this.calledStatus = calledStatus;
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

    @Override
    public String toString() {
        return "SnapTrackMaster{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", otp=" + otp +
                ", callerStatus='" + callerStatus + '\'' +
                ", calledStatus='" + calledStatus + '\'' +
                ", callDuration='" + callDuration + '\'' +
                ", created=" + created +
                '}';
    }
}
