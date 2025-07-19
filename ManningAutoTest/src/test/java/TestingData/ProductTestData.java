package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductTestData {
    @JsonProperty("ProductName")
    private String productName;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("isSearchCategory")
    private boolean isSearchCategory;

    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("isSearchBrand")
    private boolean isSearchBrand;

    @JsonProperty("SearchKeyword")
    private String searchKeyword;

    @JsonProperty("PurchaseQty")
    private int purchaseQty;

    @JsonProperty("SearchMode")
    private int searchMode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String value) {
        this.category = value;
    }

    public boolean getIsSearchCategory() {
        return isSearchCategory;
    }

    public void setIsSearchCategory(boolean bySearch) {
        this.isSearchCategory = bySearch;
    }

    public boolean getIsSearchBrand() {
        return isSearchBrand;
    }

    public void setIsSearchBrand(boolean bySearch) {
        this.isSearchBrand = bySearch;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String value) {
        this.brand = value;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String value) {
        this.searchKeyword = value;
    }

    public int getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(int value) {
        this.purchaseQty = value;
    }

    public int getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(int value) {
        this.searchMode = value;
    }
}
