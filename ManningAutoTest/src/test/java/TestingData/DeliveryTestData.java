package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryTestData {
    @JsonProperty("TestCaseID")
    private String testCaseId;

    @JsonProperty("isHomeDelivery")
    private boolean isHomeDelivery;

    @JsonProperty("YuuID")
    private String yuuId;

    @JsonProperty("CouponCode")
    private String couponCode;

    @JsonProperty("HomeDeliveryTestData")
    private HomeDeliveryTestData homeDeliveryTestData;

    @JsonProperty("ClickAndCollectTestData")
    private ClickAndCollectTestData clickAndCollectTestData;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public boolean getIsHomeDelivery() {
        return isHomeDelivery;
    }

    public void setIsHomeDelivery(boolean value) {
        this.isHomeDelivery = value;
    }

    public String getYuuId() {
        return yuuId;
    }

    public void setYuuId(String yuuId) {
        this.yuuId = yuuId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public HomeDeliveryTestData getHomeDeliveryTestData() {
        return homeDeliveryTestData;
    }

    public void setHomeDeliveryTestData(HomeDeliveryTestData value) {
        this.homeDeliveryTestData = value;
    }

    public ClickAndCollectTestData getClickAndCollectTestData() {
        return clickAndCollectTestData;
    }

    public void setClickAndCollectTestData(ClickAndCollectTestData value) {
        this.clickAndCollectTestData = value;
    }
}
