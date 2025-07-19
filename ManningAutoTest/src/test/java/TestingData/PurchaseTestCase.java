package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseTestCase {
    @JsonProperty("TestCaseID")
    private String testCaseId;

    @JsonProperty("ProductTestData")
    private ProductTestData productTestData;

    @JsonProperty("DeliveryTestData")
    private DeliveryTestData deliveryTestData;

    @JsonProperty("PaymentOptions")
    private int paymentOptions;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testId) {
        this.testCaseId = testId;
    }

    public ProductTestData getProductTestData() {
        return productTestData;
    }

    public void setProductTestData(ProductTestData value) {
        this.productTestData = value;
    }

    public DeliveryTestData getDeliveryTestData() {
        return deliveryTestData;
    }

    public void setDeliveryTestData(DeliveryTestData value) {
        this.deliveryTestData = value;
    }

    public long getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(int value) {
        this.paymentOptions = value;
    }
}
