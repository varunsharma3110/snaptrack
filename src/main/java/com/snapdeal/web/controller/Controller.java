package com.snapdeal.web.controller;

import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import com.snapdeal.web.services.GeoLocationService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Controller {

    Logger LOG = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private GeoLocationService geoLocationService;

    @PostMapping("/getGeoLocation")
    @ResponseBody
    public GeoPointSRO getGeoPoint(@RequestBody AddressSRO addressSRO) {
        GeoPointSRO sro = new GeoPointSRO();
        JSONObject object = geoLocationService.getGeoLocation(addressSRO);
        GeoAngleSRO lat = new GeoAngleSRO();
        GeoAngleSRO lon = new GeoAngleSRO();
        object = object.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        lat.setAngle(object.getDouble("lat"));
        lat.setDirection('N');
        if (lat.getAngle() < 0)
            lat.setDirection('S');
        lon.setAngle(object.getDouble("lng"));
        lon.setDirection('W');
        if (lat.getAngle() < 0)
            lat.setDirection('E');
        sro.setLongitude(lon);
        sro.setLattitude(lat);
        return sro;
    }

    @PostMapping("compareTwoPoints")
    public void compareTwoPoints(GeoPointSRO point1, GeoPointSRO point2) {

    }
}
