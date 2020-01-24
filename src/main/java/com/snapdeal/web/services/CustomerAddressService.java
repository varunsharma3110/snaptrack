package com.snapdeal.web.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.snapdeal.sro.CustomerAddressRequest;
import com.snapdeal.sro.dropAddress;
import com.snapdeal.util.HTTPUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAddressService {

    public void getCustomerAddress(List<String> referenceCodes)

    {
        String url = "http://10.65.82.45:8080/service/shipping/getShipmentInfo";

        CustomerAddressRequest request = new CustomerAddressRequest();
        request.setRequestProtocol("PROTOCOL_JSON");
        request.setResponseProtocol("PROTOCOL_JSON");
         request.setReferenceCodes(referenceCodes);


        HTTPUtility httpUtility = new HTTPUtility();
        String response = httpUtility.postRequest(url,new Gson().toJson(request));
        Gson g = new Gson();
        dropAddress p = g.fromJson(response, dropAddress.class);





    }

}
