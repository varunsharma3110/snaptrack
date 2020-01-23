package com.snapdeal.web.controller;

import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Controller {

    Logger LOG = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/getGeoLocation")
    public GeoPointSRO getGeoPoint(AddressSRO addressSRO) {
        GeoPointSRO sro = new GeoPointSRO();
        sro.setLattitude(new GeoAngleSRO(12.1233D, 'N'));
        sro.setLongitude(new GeoAngleSRO(12.1233D, 'E'));
        LOG.info("hi");
        return sro;
    }

    @PostMapping("compareTwoPoints")
    public void compareTwoPoints(GeoPointSRO point1, GeoPointSRO point2) {

    }
}
