package com.snapdeal.sro;

import java.io.Serializable;
import java.util.List;

public class CustomerAddressRequest implements Serializable {

    private String requestProtocol;
    private String responseProtocol;
    private List<String> referenceCodes;

    public String getRequestProtocol() {
        return requestProtocol;
    }

    public void setRequestProtocol(String requestProtocol) {
        this.requestProtocol = requestProtocol;
    }

    public String getResponseProtocol() {
        return responseProtocol;
    }

    public void setResponseProtocol(String responseProtocol) {
        this.responseProtocol = responseProtocol;
    }

    public List<String> getReferenceCodes() {
        return referenceCodes;
    }

    public void setReferenceCodes(List<String> referenceCodes) {
        this.referenceCodes = referenceCodes;
    }

    @Override
    public String toString() {
        return "CustomerAddressRequest{" +
                "requestProtocol='" + requestProtocol + '\'' +
                ", responseProtocol='" + responseProtocol + '\'' +
                ", referenceCodes=" + referenceCodes +
                '}';
    }
}
