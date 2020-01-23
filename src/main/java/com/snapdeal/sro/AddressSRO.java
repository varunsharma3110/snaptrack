package com.snapdeal.sro;

public class AddressSRO {

    private String houseNo;
    private String streetName;
    private String locality;
    private String city;
    private String state;
    private Long pinCode;
    private String apiKey;

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "AddressSRO{" +
                "houseNo='" + houseNo + '\'' +
                ", streetName='" + streetName + '\'' +
                ", locality='" + locality + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }

    public String getStringAddress() {
        String s = houseNo + "," + streetName + "," + locality + "," + city + "," + state + "," + pinCode + "&key=" + apiKey;
        return s.replaceAll("\\s", "+");
    }

    public static void main(String[] args) {
        AddressSRO sro = new AddressSRO();
        sro.setHouseNo("Flat No 1");
        sro.setStreetName("Begum Zaidi Market");
        sro.setLocality("Moti Bagh");
        sro.setCity("New Delhi");
        sro.setState("Delhi");
        sro.setPinCode(110021L);
        System.out.println(sro.getStringAddress());
    }
}
