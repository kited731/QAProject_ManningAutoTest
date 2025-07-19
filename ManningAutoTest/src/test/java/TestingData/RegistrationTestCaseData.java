package TestingData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationTestCaseData {
    @JsonProperty("RegistrationTestCase")
    private List<RegistrationTestCase> registrationTestCase;

    public List<RegistrationTestCase> getRegistrationTestCases() {
        return registrationTestCase;
    }

    public void setRegistrationTestCases(List<RegistrationTestCase> registrationTestCases) {
        this.registrationTestCase = registrationTestCases;
    }
}
