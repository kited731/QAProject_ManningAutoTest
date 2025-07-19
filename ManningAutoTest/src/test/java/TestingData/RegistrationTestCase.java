package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationTestCase {
    @JsonProperty("TestCaseID")
    private String testCaseId;

    @JsonProperty("RegistrationTestData")
    private RegistrationTestData registrationTestData;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testId) {
        this.testCaseId = testId;
    }

    public RegistrationTestData getRegistrationTestData() {
        return registrationTestData;
    }

    public void setRegistrationTestData(RegistrationTestData registrationTestData) {
        this.registrationTestData = registrationTestData;
    }
}
