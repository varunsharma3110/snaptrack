package com.snapdeal.web.services;

import com.snapdeal.sro.AddressSRO;
import com.snapdeal.util.HTTPUtility;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GeoLocationService {


    HTTPUtility httpUtility;

    @PostConstruct
    public void init() {
        httpUtility = new HTTPUtility();
    }

    public JSONObject getGeoLocation(AddressSRO addressSRO) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = httpUtility.makeGetRequest(addressSRO.getStringAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
