package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeDeliveryTestData {
    @JsonProperty("IsUpdateAddress")
    private boolean isUpdateAddress;

    @JsonProperty("IsUpdateQty")
    private boolean isUpdateQty;

    @JsonProperty("newQty")
    private int newQty;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("District")
    private String district;

    @JsonProperty("StreetNumber")
    private String streetNumber;

    @JsonProperty("Building")
    private String building;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("CountryCode")
    private String countryCode;

    @JsonProperty("Mobile")
    private String mobile;

    public boolean getIsUpdateAddress() {
        return isUpdateAddress;
    }

    public void setIsUpdateAddress(boolean value) {
        this.isUpdateAddress = value;
    }

    public boolean getIsUpdateQty() {
        return isUpdateQty;
    }

    public void setIsUpdateQty(boolean value) {
        this.isUpdateQty = value;
    }

    public int getNewQty() {
        return newQty;
    }

    public void setNewQty(int value) {
        this.newQty = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String value) {
        this.region = value;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String value) {
        this.district = value;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String value) {
        this.streetNumber = value;
    }

    public String getBuidling() {
        return building;
    }

    public void setBuidling(String value) {
        this.building = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String value) {
        this.unit = value;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }
}
