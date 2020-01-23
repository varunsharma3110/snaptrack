package com.snapdeal.web.controller;

import com.snapdeal.snaptrack.SnapTrackMaster;
import com.snapdeal.snaptrack.SnapTrackRepository;
import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
<<<<<<< HEAD:src/main/java/com/snapdeal/web/controller/SnapTrackController.java
import com.snapdeal.sro.SnapTrackResponse;
=======
import com.snapdeal.web.services.GeoLocationService;
import org.json.JSONObject;
>>>>>>> 836552de12df10b61c46e4f3d97efe872e59eee6:src/main/java/com/snapdeal/web/controller/Controller.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class SnapTrackController {

    Logger LOG = LoggerFactory.getLogger(SnapTrackController.class);

    @Autowired
    SnapTrackRepository repository;


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
        if (lat.getAngle() < 0) {
            lat.setAngle(lat.getAngle() * 1);
            lat.setDirection('S');
        }
        lon.setAngle(object.getDouble("lng"));
        lon.setDirection('W');
        if (lon.getAngle() < 0) {
            lon.setAngle(lon.getAngle() * 1);
            lon.setDirection('E');
        }
        sro.setLongitude(lon);
        sro.setLattitude(lat);
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
