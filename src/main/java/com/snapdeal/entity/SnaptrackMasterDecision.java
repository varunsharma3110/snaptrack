package com.snapdeal.entity;

import com.snapdeal.enums.RTOType;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "snaptrack_master_decision")
public class SnaptrackMasterDecision implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private Boolean otp_validated;
    private Boolean call_validated;
    private Boolean loc_validated;
    private Date created;
    private String rtoReason;

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

    public Boolean getOtp_validated() {
        return otp_validated;
    }

    public void setOtp_validated(Boolean otp_validated) {
        this.otp_validated = otp_validated;
    }

    public Boolean getCall_validated() {
        return call_validated;
    }

    public void setCall_validated(Boolean call_validated) {
        this.call_validated = call_validated;
    }

    public Boolean getLoc_validated() {
        return loc_validated;
    }

    public void setLoc_validated(Boolean loc_validated) {
        this.loc_validated = loc_validated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getRtoReason() {
        return rtoReason;
    }

    public void setRtoReason(String rtoReason) {
        this.rtoReason = rtoReason;
    }

    @Override
    public String toString() {
        return "SnaptrackMasterDecision{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", otp_validated=" + otp_validated +
                ", call_validated=" + call_validated +
                ", loc_validated=" + loc_validated +
                ", created=" + created +
                ", rtoReason='" + rtoReason + '\'' +
                '}';
    }
}
