package com.snapdeal.sro;

import com.snapdeal.entity.SnaptrackMasterDecision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SnapTrackMasterDecisonResponse  extends  ServiceResponse  {

    private List<SnaptrackMasterDecision> result;

    public List<SnaptrackMasterDecision> getResult() {
        return result;
    }

    public void setResult(List<SnaptrackMasterDecision> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SnapTrackMasterDecisonResponse{" +
                "result=" + result +
                '}';
    }
}
