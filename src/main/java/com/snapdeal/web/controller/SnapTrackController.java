package com.snapdeal.web.controller;

import com.snapdeal.entity.SnaptrackMasterDecision;
import com.snapdeal.enums.RTOType;
import com.snapdeal.repository.ISnapTrackMasterDecisonRepository;
import com.snapdeal.repository.ISnapTrackRepository;
import com.snapdeal.sro.*;
import com.snapdeal.web.services.DecisionTreeService;
import com.snapdeal.web.services.GeoLocationService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class SnapTrackController {

    Logger LOG = LoggerFactory.getLogger(SnapTrackController.class);

    @Autowired
    ISnapTrackRepository repository;

    @Autowired
    ISnapTrackMasterDecisonRepository decisonRepository;

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    DecisionTreeService decisionTreeService;

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

    @GetMapping ("getFromData")
    public SnapTrackMasterResponse getAll(@RequestParam(value = "decision", required=false) String decision){
        SnapTrackMasterResponse response = new SnapTrackMasterResponse();
        if(!StringUtils.isEmpty(decision)){
            response.setResults(repository.findAllByDecison(decision));
        }else {
            response.setResults(repository.findAll());
        }
        response.setSuccess(true);
        response.setMessage("Success");
        return response;
    }

    private double deg2rad(double degree){
         return degree * (Math.PI/180);
    }


    @GetMapping ("getDecisonTreeFromOrderId")
    public SnapTrackMasterDecisonResponse getDecison(@RequestParam String orderId){
        SnapTrackMasterDecisonResponse response = new SnapTrackMasterDecisonResponse();
        List<SnaptrackMasterDecision> decisions = decisonRepository.findDecisonTreeByOrderId(orderId);
        SnaptrackMasterDecision decision = decisonRepository.findDecisonTreeByOrderId(orderId).get(0);
        String json = decisionTreeService.createDecisionJson(RTOType.valueOf(decision.getRtoReason()), decision);
        response.setJson(json);
        response.setSuccess(true);
        response.setMessage("Success");
        return  response;
    }
}
