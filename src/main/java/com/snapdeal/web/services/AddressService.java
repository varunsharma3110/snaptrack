package com.snapdeal.web.services;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.repository.ISnapTrackRepository;
import com.snapdeal.sro.AddressSRO;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AddressService {


    @PostConstruct
    public void init() {
        findDiffBetweenTwoLocations();
    }

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private ISnapTrackRepository snapTrackRepository;


    public AddressSRO getAddressByOrderId(String orderId) {

        AddressSRO address = new AddressSRO();
        address.setHouseNo("House No. 61");
        address.setLocality("Sector 56");
        address.setCity("Gurugram");
        address.setState("Haryana");
        address.setPinCode(122011l);
        return address;

    }


    public GeoPointSRO getGeoLocationFromAddress(AddressSRO address) {
        GeoPointSRO sro = new GeoPointSRO();
        JSONObject object = geoLocationService.getGeoLocation(address);
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


    private void findDiffBetweenTwoLocations() {

        List<String> orderIds = snapTrackRepository.findAllOrderIds();

        for (String orderId : orderIds) {
            AddressSRO address = getAddressByOrderId(orderId);
            GeoPointSRO geoPointSRO = getGeoLocationFromAddress(address);
            System.out.println(geoPointSRO.getLattitude().getAngle());
            SnapTrackMaster obj = snapTrackRepository.findOneByOrderId(orderId).get(0);
            obj.setLatitude(geoPointSRO.getLattitude().getAngle());
            snapTrackRepository.saveAndFlush(obj);

        }


    }


}
