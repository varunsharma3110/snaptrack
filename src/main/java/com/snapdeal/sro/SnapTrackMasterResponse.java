package com.snapdeal.sro;

import com.snapdeal.entity.SnapTrackMaster;

import java.io.Serializable;
import java.util.List;

public class SnapTrackMasterResponse extends  ServiceResponse  {

    private List<SnapTrackMaster> results;

    public List<SnapTrackMaster> getResults() {
        return results;
    }

    public void setResults(List<SnapTrackMaster> results) {
        this.results = results;
    }



}
