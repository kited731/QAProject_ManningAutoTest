package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClickAndCollectTestData {
    @JsonProperty("SearchKeyword")
    private String searchKeyword;

    @JsonProperty("ResultPreference")
    private int resultPreference;

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String value) {
        this.searchKeyword = value;
    }

    public long getResultPreference() {
        return resultPreference;
    }

    public void setResultPreference(int value) {
        this.resultPreference = value;
    }
}
