package com.snapdeal.sro;

import com.snapdeal.entity.SnaptrackMasterDecision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SnapTrackMasterDecisonResponse  extends  ServiceResponse  {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
