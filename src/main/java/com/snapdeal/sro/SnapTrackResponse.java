package com.snapdeal.sro;

import com.snapdeal.snaptrack.SnapTrackMaster;

import java.io.Serializable;
import java.util.List;

public class SnapTrackResponse implements Serializable {

    private boolean success;
    private String message;
    private List<SnapTrackMaster> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SnapTrackMaster> getResults() {
        return results;
    }

    public void setResults(List<SnapTrackMaster> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SnapTrackResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", results=" + results +
                '}';
    }
}
