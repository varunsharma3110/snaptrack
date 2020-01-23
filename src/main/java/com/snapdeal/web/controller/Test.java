package com.snapdeal.web.controller;

import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;

public class Test {


    public static void testCompareTwoPoints(){
        GeoAngleSRO latitude1 = new GeoAngleSRO(24.456,'W');
        GeoAngleSRO latitude2 = new GeoAngleSRO(23.456,'W');
        GeoAngleSRO longitude1 = new GeoAngleSRO(23.456,'W');
        GeoAngleSRO longitude2 = new GeoAngleSRO(23.456,'W');
        GeoPointSRO point1 = new GeoPointSRO(latitude1,longitude1);
        GeoPointSRO point2 = new GeoPointSRO(latitude2,longitude2);
        System.out.println(compareTwoPoints(point1,point2));

    }


    public static void main(String args[]){
        testCompareTwoPoints();
    }


    public static double compareTwoPoints(GeoPointSRO point1, GeoPointSRO point2) {
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

    private static double deg2rad(double degree){
        return degree * (Math.PI/180);
    }

}
