package TestingData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseTestCaseData {
    @JsonProperty("PurchaseTestCase")
    private List<PurchaseTestCase> purchaseTestCases;

    public List<PurchaseTestCase> getPurchaseTestCases() {
        return purchaseTestCases;
    }

    public void setPurchaseTestCases(List<PurchaseTestCase> purchaseTestCases) {
        this.purchaseTestCases = purchaseTestCases;
    }

}
