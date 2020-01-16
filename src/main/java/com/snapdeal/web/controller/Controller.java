package com.snapdeal.web.controller;

import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/getGeoLocation")
    public GeoPointSRO getGeoPoint(AddressSRO addressSRO) {
        GeoPointSRO sro = new GeoPointSRO();
        sro.setLattitude(new GeoAngleSRO(12.1233D, 'N'));
        sro.setLongitude(new GeoAngleSRO(12.1233D, 'E'));
        return sro;
    }

    @PostMapping("compareTwoPoints")
    public void compareTwoPoints(GeoPointSRO point1, GeoPointSRO point2) {

    }
}
