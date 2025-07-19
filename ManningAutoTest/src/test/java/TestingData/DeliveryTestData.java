package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryTestData {
    @JsonProperty("isHomeDelivery")
    private boolean isHomeDelivery;

    @JsonProperty("HomeDeliveryTestData")
    private HomeDeliveryTestData homeDeliveryTestData;

    @JsonProperty("ClickAndCollectTestData")
    private ClickAndCollectTestData clickAndCollectTestData;

    public boolean getIsHomeDelivery() {
        return isHomeDelivery;
    }

    public void setIsHomeDelivery(boolean value) {
        this.isHomeDelivery = value;
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
