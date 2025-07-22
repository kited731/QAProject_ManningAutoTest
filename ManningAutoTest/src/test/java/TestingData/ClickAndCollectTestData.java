package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClickAndCollectTestData {
    @JsonProperty("SearchKeyword")
    private String searchKeyword;

    @JsonProperty("PurchaseQty")
    private int purchaseQty;

    @JsonProperty("ResultPreference")
    private int resultPreference;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("CountryCode")
    private String countryCode;

    @JsonProperty("Mobile")
    private String mobile;

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String value) {
        this.searchKeyword = value;
    }

    public int getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(int purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public int getResultPreference() {
        return resultPreference;
    }

    public void setResultPreference(int value) {
        this.resultPreference = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
