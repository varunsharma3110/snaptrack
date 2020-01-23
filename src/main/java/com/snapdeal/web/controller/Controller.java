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



    private double deg2rad(double degree){
         return degree * (Math.PI/180);
    }
}
