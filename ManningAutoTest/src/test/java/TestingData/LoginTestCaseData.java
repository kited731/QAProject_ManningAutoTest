package TestingData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginTestCaseData {
    @JsonProperty("LoginTestCase")
    private List<LoginTestCase> loginTestCases;

    public List<LoginTestCase> getLoginTestCases() {
        return loginTestCases;
    }

    public void setLoginTestCase(List<LoginTestCase> loginTestCase) {
        this.loginTestCases = loginTestCase;
    }

}
