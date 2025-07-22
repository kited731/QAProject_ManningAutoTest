package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class HandleRegistration {

  public static void startRegister(Page page) {
    page.locator(
        "xpath=//div[contains(@class,\"header-rightIconsWrap-p7s\")]/div[contains(@class,\"accountChip-root-zst\")]/div/span[text()=\"Sign up | Log in\"]")
        .click();
    page.waitForTimeout(2000);
    page.locator("xpath=//button/span[text()=\"Create an account\"]").click();
  }

  public static void selectTitle(Page page, int titleOption) {
    Locator fillTitle = page.locator("#title");
    fillTitle.scrollIntoViewIfNeeded();
    fillTitle.click();

    switch (titleOption) {
      case 1: // Mr
        Locator mrLocator = page.locator(
            "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mr.\" or text()=\"Mr\"]");
        mrLocator.click();
        break;
      case 2: // Mrs
        Locator mrsLocator = page.locator(
            "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mrs.\" or text()=\"Mrs\"]");
        mrsLocator.click();
        break;
      case 3: // Ms
        Locator msLocator = page.locator(
            "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Ms.\" or text()=\"Ms\"]");
        msLocator.click();
        break;
      default:
        Locator defaultLocator = page.locator(
            "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mr.\" or text()=\"Mr\"]");
        defaultLocator.click();
        break;
    }
  }

  public static void selectTitle(Page page, String title) {
    Locator fillTitle = page.locator("#title");
    fillTitle.scrollIntoViewIfNeeded();
    fillTitle.click();

    if (title.equals("Mr") || title.equals("Mr.")) {
      page.locator(
          "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mr.\" or text()=\"Mr\"]")
          .click();
    } else if (title.equals("Mrs") || title.equals("Mrs.")) {
      page.locator(
          "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mrs.\" or text()=\"Mrs\"]")
          .click();
    } else if (title.equals("Ms") || title.equals("Ms.")) {
      page.locator(
          "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Ms.\" or text()=\"Ms\"]")
          .click();
    } else {
      page.locator(
          "xpath=//div[contains(@class,\"react-select__menu\")]/div[@role=\"listbox\"]/div[text()=\"Mr.\" or text()=\"Mr\"]")
          .click();
    }
  }

  public static void inputFirstName(Page page, String firstName) {
    String regex = "[\\W\\d]"; // Text contains special characters and digits
    Locator fillFirstName = page.locator("#firstName");
    fillFirstName.scrollIntoViewIfNeeded();
    fillFirstName.fill(firstName);
    fillFirstName.blur();

    if (regexChecking(regex, firstName, false) || firstName.isEmpty()) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page.locator("xpath=//div[@class=\"field-root-BRZ\"][2]/p");
      String errorMessage = "This field can't be empty or input number/special characters";

      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }
  }

  public static void inputLastName(Page page, String lastName) {
    String regex = "[\\W\\d]"; // Text contains special characters and digits
    Locator fillLastName = page.locator("#lastName");

    fillLastName.scrollIntoViewIfNeeded();
    fillLastName.fill(lastName);
    fillLastName.blur();

    if (regexChecking(regex, lastName, false) || lastName.isEmpty()) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page.locator("xpath=//div[@class=\"field-root-BRZ\"][3]/p");
      String errorMessage = "This field can't be empty or input number/special characters";

      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }

  }

  public static void inputEmailAddress(Page page, String emailAddress) {
    String emailRegex = "[\\w-._+]+@[\\w-._+]+.\\w{2,4}";
    Locator fillEmail = page.locator("#Email");
    fillEmail.scrollIntoViewIfNeeded();
    fillEmail.fill(emailAddress);
    fillEmail.blur();

    if (!regexChecking(emailRegex, emailAddress, true)) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page.locator("xpath=//div[@class=\"field-root-BRZ\"][4]/p");
      String errorMessage = "Please enter a valid email address";
      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }
  }

  public static void selectCountryCode(Page page, String countryCode, String phonenNumber) {
    if (countryCode.equals("+852")) { // Hong Kong number
      selectHKphone(page, countryCode, phonenNumber);
    } else if (countryCode.equals("+853")) { // Macau number
      selectMCphone(page, countryCode, phonenNumber);
    } else if (countryCode.equals("+86")) { // China number
      selectCNphone(page, countryCode, phonenNumber);
    } else { // fallback failsafe
      selectHKphone(page, countryCode, phonenNumber);
    }
  }

  public static void selectHKphone(Page page, String countrycode, String phonenNumber) {
    String hkPhoneRegex = "^[2-9][0-9]{7}$"; // 8 digits, start with 2 to 9
    Locator fillPhoneID = page.locator("#countryCode");
    Locator fillPhoneNum = page.locator("#telephone");
    // Select countrycode
    fillPhoneID.scrollIntoViewIfNeeded();
    fillPhoneID.click();
    page.locator("xpath=//div[@role=\"option\"][text()=\"+852\"]").click();
    // fill phone number
    fillPhoneNum.fill(phonenNumber);
    fillPhoneNum.blur();
    // Verify the phone numner
    if (!regexChecking(hkPhoneRegex, phonenNumber, true)) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page
          .locator("xpath=//div[contains(@class,\"createAccount-phonePickerContainer\")]/div[2]/div/p");
      String errorMessage = "Please enter a valid mobile number";
      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }
  }

  public static void selectMCphone(Page page, String countrycode, String phonenNumber) {
    String mcPhoneRegex = "^[6][0-9]{7}$"; // 8 digits, start with 6
    Locator fillPhoneID = page.locator("#countryCode");
    Locator fillPhoneNum = page.locator("#telephone");
    // Select countrycode
    fillPhoneID.scrollIntoViewIfNeeded();
    fillPhoneID.click();
    page.locator("xpath=//div[@role=\"option\"][text()=\"+853\"]").click();
    // fill phone number
    fillPhoneNum.fill(phonenNumber);
    fillPhoneNum.blur();
    // Verify the phone numner
    if (!regexChecking(mcPhoneRegex, phonenNumber, true)) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page
          .locator("xpath=//div[contains(@class,\"createAccount-phonePickerContainer\")]/div[2]/div/p");
      String errorMessage = "Please enter a valid mobile number";
      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }
  }

  public static void selectCNphone(Page page, String countrycode, String phonenNumber) {
    String cnPhoneRegex = "^1[3-9][0-9]{10}$"; // 11 digits, start with 13X to 19X
    Locator fillPhoneID = page.locator("#countryCode");
    Locator fillPhoneNum = page.locator("#telephone");
    // Select countrycode
    fillPhoneID.scrollIntoViewIfNeeded();
    fillPhoneID.click();
    page.locator("xpath=//div[@role=\"option\"][text()=\"+86\"]").click();
    // fill phone number
    fillPhoneNum.fill(phonenNumber);
    fillPhoneNum.blur();
    // Verify the phone numner
    if (!regexChecking(cnPhoneRegex, phonenNumber, true)) {
      page.waitForTimeout(500);
      Locator errorMessageLocator = page
          .locator("xpath=//div[contains(@class,\"createAccount-phonePickerContainer\")]/div[2]/div/p");
      String errorMessage = "Please enter a valid mobile number";
      PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
    }
  }

  public static void inputPassword(Page page, String originalPassword, String confirmPassword) {
    Locator passwordInput = page.locator("#Password");
    Locator confirmPasswordInput = page.locator("#ConfirmPassword");

    // 8-20 alphanumeric, at least 1 alphabet and 1 number, allow special characters
    String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$";

    passwordInput.scrollIntoViewIfNeeded();
    passwordInput.fill(originalPassword);
    passwordInput.blur();
    confirmPasswordInput.scrollIntoViewIfNeeded();
    confirmPasswordInput.fill(confirmPassword);
    confirmPasswordInput.blur();

    // Verfiy password - Not matching password policy
    if (originalPassword.isEmpty()) {
      Locator passwordErrorLocator = page.locator("xpath=//div[@class=\"password-root-Vs9\"][1]/p");
      String passwordErrorMessage = "Please enter the OTP";
      PlaywrightAssertions.assertThat(passwordErrorLocator).hasText(passwordErrorMessage);
    } else if (!regexChecking(passwordRegex, originalPassword, true)) { // Verfiy password - no input
      Locator passwordErrorLocator = page.locator("xpath=//div[@class=\"password-root-Vs9\"][1]/p");
      String passwordErrorMessage = "Password must contain minimum 6, maximum 20 character, at least one numeric and one alphabetic character";
      PlaywrightAssertions.assertThat(passwordErrorLocator).hasText(passwordErrorMessage);
    }
    // Verify Confirm password
    if (!originalPassword.isEmpty() && !originalPassword.equals(confirmPassword)) { // Password are not matching
      Locator passwordErrorLocator = page.locator("xpath=//div[@class=\"password-root-Vs9\"][2]/p");
      String confirmPasswordErrorMessage = "Please enter the same password as above";
      PlaywrightAssertions.assertThat(passwordErrorLocator).hasText(confirmPasswordErrorMessage);
    }
  }

  public static void clickTNC(Page page) {
    Locator clickTnC = page.getByText("Terms & Conditions");
    clickTnC.scrollIntoViewIfNeeded();
    clickTnC.click();
    page.waitForTimeout(2000);
    Locator closeTCPop = page.locator("xpath=//*[name()='path' and contains(@d,'M15.172 2.')]");
    closeTCPop.click();
    page.waitForTimeout(2000);
  }

  public static void clickPICS(Page page) {
    Locator clickIPC = page.locator("xpath=//p/span[text()=\"PICS\"]");
    clickIPC.scrollIntoViewIfNeeded();
    page.waitForTimeout(2000);
    clickIPC.click();
    page.locator("xpath=//button[@class=\"popup-popupClose-TDK\"]").click();
    page.waitForTimeout(2000);
  }

  public static void clickPrivacyPolicyA(Page page) {
    Locator clickPrivacyPolicy = page.locator("xpath=//p/span[text()=\"Privacy Policy\"]");
    clickPrivacyPolicy.scrollIntoViewIfNeeded();
    page.waitForTimeout(2000);
    clickPrivacyPolicy.click();
    page.locator("xpath=//button[@class=\"popup-popupClose-TDK\"]").click();
    page.waitForTimeout(2000);
  }

  public static void subscribe(Page page) {
    Locator registrationbox = page.locator("#subscribe");
    registrationbox.click();
    page.waitForTimeout(2000);
  }

  public static void directMarketingPurposes(Page page) {
    Locator clickDMP = page.getByText("Direct Marketing purposes");
    clickDMP.click();
    page.waitForTimeout(2000);
    Locator closeDMP = page.locator("xpath= //*[name()='path' and contains(@d,'M15.172 2.')]");
    closeDMP.click();
    page.waitForTimeout(2000);
  }

  public static void createAnAccount(Page page) {
    Locator createanaccount = page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Create an account"));
    createanaccount.click();
  }

  public static void clickPrivacyPolicyB(Page page) {
    Locator clickPrivacyPolicy = page
        .locator("xpath=//span[contains(@class,\"checkbox-label-PlJ\")]/span[text()=\"Privacy Policy\"]");
    clickPrivacyPolicy.scrollIntoViewIfNeeded();
    page.waitForTimeout(2000);
    clickPrivacyPolicy.click();
    page.locator("xpath=//button[@class=\"popup-popupClose-TDK\"]").click();
    page.waitForTimeout(2000);
  }

  public static void waitForCaptcha(Page page) {
    page.waitForTimeout(15000);
  }

  public static void clickCreate(Page page) {
    Locator createanaccount = page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Create an account"));
    createanaccount.scrollIntoViewIfNeeded();
    createanaccount.click();
    page.waitForTimeout(2000);
    page.locator("xpath=//button/span[text()=\"Confirm\"]").click();
  }

  public static void waitForOTP(Page page) {
    page.waitForTimeout(30000);
    Locator verifyAccountButton = page.locator("xpath=//div/button[@type=\"submit\"]");
    verifyAccountButton.scrollIntoViewIfNeeded();
    if (!verifyAccountButton.isDisabled()) {
      verifyAccountButton.click();
    }
  }

  private static boolean regexChecking(String regex, String textToVerify, boolean exactMatch) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(textToVerify);

    if (exactMatch) {
      return matcher.matches();
    } else {
      return matcher.find(); // Return true if the regex is found
    }
  }
}

/*
 * Old Code
 * 
 * @Test
 * void contextLoads() {
 * Playwright playwright = Playwright.create();
 * BrowserType browserType = playwright.chromium();
 * Page page = browserType.launch(new
 * BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();
 * 
 * page.navigate("https://www.mannings.com.hk/en");
 * // Locator closePop = page.getByRole(AriaRole.BUTTON, new
 * // Page.GetByRoleOptions().setName("X"));
 * // closePop.click();
 * Locator closeUnderstand = page.getByRole(AriaRole.BUTTON, new
 * Page.GetByRoleOptions().setName("I understand"));
 * closeUnderstand.click();
 * Locator clickRegistration = page.getByText("Sign up | Log in").nth(1);
 * clickRegistration.click();
 * Locator clickCreate = page.getByText("Create an account").nth(1);
 * clickCreate.click();
 * // Locator clickOption1 = page.getByText("Mr.").nth(1);
 * // clickOption1.click();
 * Locator fillTitle = page.locator("#title");
 * fillTitle.click();
 * Locator fillTitle1 = page.getByText("Mrs");
 * fillTitle1.click();
 * Locator fillFirstName = page.locator("#firstName");
 * fillFirstName.fill("JK");
 * Locator fillLastName = page.locator("#lastName");
 * fillLastName.fill("Lee");
 * Locator fillEmail = page.locator("#Email");
 * fillEmail.fill("lee@example.com");
 * Locator fillPhoneID = page.locator("#countryCode").nth(0);
 * fillPhoneID.click();
 * Locator fillPhoneID1 = page.getByText("+853");
 * fillPhoneID1.click();
 * Locator fillPhoneNum = page.locator("#telephone");
 * fillPhoneNum.fill("12345678");
 * Locator fillPassword = page.locator("#Password");
 * fillPassword.fill("12345678");
 * Locator ConfirmPassword = page.locator("#ConfirmPassword");
 * ConfirmPassword.fill("12345678");
 * Locator clickTnC = page.getByText("Terms & Conditions");
 * clickTnC.click();
 * Locator closeTCPop =
 * page.locator("xpath=//*[name()='path' and contains(@d,'M15.172 2.')]");
 * closeTCPop.click();
 * Locator registrationbox = page.locator("#subscribe");
 * registrationbox.click();
 * Locator clickDMP = page.getByText("Direct Marketing purposes");
 * clickDMP.click();
 * Locator closeDMP =
 * page.locator("xpath= //*[name()='path' and contains(@d,'M15.172 2.')]");
 * closeDMP.click();
 * // Locator shopLocator = page.getByText("OL");
 * // shopLocator.click();
 * page.pause();
 * Locator createanaccount = page.getByRole(AriaRole.BUTTON,
 * new Page.GetByRoleOptions().setName("Create an account"));
 * createanaccount.click();
 * // Close Playwright =================
 * playwright.close();
 * 
 * }
 */
