package com.snapdeal.web.services;

import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoPointSRO;
import com.snapdeal.util.HTTPUtility;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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


    public double compareTwoPoints(GeoPointSRO point1, GeoPointSRO point2) {
        int R= 6371;
        double lat1 =point1.getLattitude().getAngle();
        double lat2 =point2.getLattitude().getAngle();
        double lon1 =point1.getLongitude().getAngle();
        double lon2 =point2.getLongitude().getAngle();

        double distanceLat = deg2rad(lat2-lat1);
        double distanceLon = deg2rad(lon2-lon1);

        double a= Math.sin(distanceLat/2) * Math.sin(distanceLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))* Math.sin(distanceLon/2) * Math.sin(distanceLon/2);
        double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R*c;
        return d;

    }

    private double deg2rad(double degree) {
        return degree * (Math.PI / 180);
    }


}
