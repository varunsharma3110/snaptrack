package com.snapdeal.web.controller;

import com.snapdeal.snaptrack.SnapTrackMaster;
import com.snapdeal.snaptrack.SnapTrackRepository;
import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import com.snapdeal.sro.SnapTrackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class SnapTrackController {

    Logger LOG = LoggerFactory.getLogger(SnapTrackController.class);

    @Autowired
    SnapTrackRepository repository;


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

    @GetMapping ("getFromData")
    public SnapTrackResponse getAll(){
        SnapTrackResponse response = new SnapTrackResponse();
        response.setResults( repository.findAll());
        response.setSuccess(true);
        response.setMessage("Success");
        return  response;
    }
}
