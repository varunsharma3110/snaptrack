package com.snapdeal.web.services;

import com.google.gson.Gson;
import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.repository.ISnapTrackRepository;
import com.snapdeal.sro.*;
import com.snapdeal.util.HTTPUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAddressService {

    @Autowired
    AddressService addressService;

    @Autowired
    ISnapTrackRepository snapTrackRepository;

    public  void getCustomerAddress(List<String> referenceCodes)

    {
        String url = "http://10.65.82.45:8080/service/shipping/getShipmentInfo";

        CustomerAddressRequest request = new CustomerAddressRequest();
        request.setRequestProtocol("PROTOCOL_JSON");
        request.setResponseProtocol("PROTOCOL_JSON");
         request.setReferenceCodes(referenceCodes);


        HTTPUtility httpUtility = new HTTPUtility();
        String response = httpUtility.postRequest(url,new Gson().toJson(request));
        System.out.println(response);
        Gson g = new Gson();
        ShipmentInfoResponse p = g.fromJson(response, ShipmentInfoResponse.class);
        //System.out.println(p.toString());
        GeoPointSRO geoPointSRO = addressService.getGeoLocationFromAddress(getAddressFromDropAddress(p));
        List<SnapTrackMaster> sp = snapTrackRepository.findOneByOrderId(p.getShipmentInfoSROS().get(0).getReferenceCode());
        SnapTrackMaster sm = sp.get(0);
        sm.setCustLat(geoPointSRO.getLattitude().getAngle());
        sm.setCustLong(geoPointSRO.getLongitude().getAngle());
        snapTrackRepository.save(sm);






    }
    private AddressSRO getAddressFromDropAddress(ShipmentInfoResponse response){
       AddressSRO addressSRO = new AddressSRO();
       addressSRO.setCity(response.getShipmentInfoSROS().get(0).getDropAddress().getCity());
       addressSRO.setHouseNo(response.getShipmentInfoSROS().get(0).getDropAddress().getAddressLine1());
       addressSRO.setLocality(response.getShipmentInfoSROS().get(0).getDropAddress().getAddressLine2());
       addressSRO.setPinCode(Long.valueOf(response.getShipmentInfoSROS().get(0).getDropAddress().getPincode()));
       //addressSRO.setStreetName(response.getShipmentInfoSROS().get(0).getDropAddress().);
        addressSRO.setState(response.getShipmentInfoSROS().get(0).getDropAddress().getState());
        return addressSRO;

    }

//    public static  void main(String args[]){
//        List<String> referenceCodes = new ArrayList<String>();
//        referenceCodes.add("SLP2702936939");
//        getCustomerAddress(referenceCodes);
//    }

}
