package TestingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationTestData {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("CountryCode")
    private String countryCode;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("PasswordConfirm")
    private String passwordConfirm;

    @JsonProperty("ClickTNC")
    private boolean clickTNC;

    @JsonProperty("ClickPICS")
    private boolean clickPICS;

    @JsonProperty("ClickPrivacyA")
    private boolean clickPrivacyA;

    @JsonProperty("ClickDirectMarkteting")
    private boolean clickDirectMarketing;

    @JsonProperty("ClickPrivacyB")
    private boolean clickPrivacyB;

    @JsonProperty("ClickSubscribe")
    private boolean clickSubscribe;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isClickTNC() {
        return clickTNC;
    }

    public void setClickTNC(boolean clickTNC) {
        this.clickTNC = clickTNC;
    }

    public boolean isClickPICS() {
        return clickPICS;
    }

    public void setClickPICS(boolean clickPICS) {
        this.clickPICS = clickPICS;
    }

    public boolean isClickPrivacyA() {
        return clickPrivacyA;
    }

    public void setClickPrivacyA(boolean clickPrivacyA) {
        this.clickPrivacyA = clickPrivacyA;
    }

    public boolean isClickDirectMarketing() {
        return clickDirectMarketing;
    }

    public void setClickDirectMarketing(boolean clickDirectMarketing) {
        this.clickDirectMarketing = clickDirectMarketing;
    }

    public boolean isClickPrivacyB() {
        return clickPrivacyB;
    }

    public void setClickPrivacyB(boolean clickPrivacyB) {
        this.clickPrivacyB = clickPrivacyB;
    }

    public boolean isClickSubscribe() {
        return clickSubscribe;
    }

    public void setClickSubscribe(boolean clickSubscribe) {
        this.clickSubscribe = clickSubscribe;
    }

}
