package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginTestCase {
    @JsonProperty("TestCaseId")
    private String testCaseId;

    @JsonProperty("LoginByEmail")
    private boolean loginByEmail;

    @JsonProperty("EmailAddress")
    private String emailAddress;

    @JsonProperty("CountryCode")
    private String countryCode;

    @JsonProperty("MobileNumber")
    private String mobileNumber;

    @JsonProperty("Password")
    private String password;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String value) {
        this.testCaseId = value;
    }

    public boolean getLoginByEmail() {
        return loginByEmail;
    }

    public void setLoginByEmail(boolean value) {
        this.loginByEmail = value;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    public String getCountryCode() {
        if (countryCode.equals("852") || countryCode.equals("+852")) {
            return "+852";
        } else if (countryCode.equals("853") || countryCode.equals("+853")) {
            return "+853";
        } else if (countryCode.equals("86") || countryCode.equals("+86")) {
            return "+86";
        } else {
            return "+852";
        }
    }

    public void setCountryCode(String value) {
        if (value.equals("852") || value.equals("+852")) {
            this.countryCode = "+852";
        } else if (value.equals("853") || value.equals("+853")) {
            this.countryCode = "+853";
        } else if (value.equals("86") || value.equals("+86")) {
            this.countryCode = "+86";
        } else {
            this.countryCode = "+852";
        }
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }
}
