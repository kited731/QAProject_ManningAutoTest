import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

public class RegistrationManningTest {  @Test
  void contextLoads() {
    Playwright playwright = Playwright.create();
    BrowserType browserType = playwright.chromium();
    Page page = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();

    page.navigate("https://www.mannings.com.hk/en");
    // Locator closePop = page.getByRole(AriaRole.BUTTON, new
    // Page.GetByRoleOptions().setName("X"));
    // closePop.click();
    Locator closeUnderstand = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
    closeUnderstand.click();
    Locator clickRegistration = page.getByText("Sign up | Log in").nth(1);
    clickRegistration.click();
    Locator clickCreate = page.getByText("Create an account").nth(1);
    clickCreate.click();
    // Locator clickOption1 = page.getByText("Mr.").nth(1);
    // clickOption1.click();
    Locator fillTitle = page.locator("#title");
    fillTitle.click();
    Locator fillTitle1 = page.getByText("Mrs");
    fillTitle1.click();
    Locator fillFirstName = page.locator("#firstName");
    fillFirstName.fill("JK");
    Locator fillLastName = page.locator("#lastName");
    fillLastName.fill("Lee");
    Locator fillEmail = page.locator("#Email");
    fillEmail.fill("lee@example.com");
    Locator fillPhoneID = page.locator("#countryCode").nth(0);
    fillPhoneID.click();
    Locator fillPhoneID1 = page.getByText("+853");
    fillPhoneID1.click();
    Locator fillPhoneNum = page.locator("#telephone");
    fillPhoneNum.fill("12345678");
    Locator fillPassword = page.locator("#Password");
    fillPassword.fill("12345678");
    Locator ConfirmPassword = page.locator("#ConfirmPassword");
    ConfirmPassword.fill("12345678");
    Locator clickTnC = page.getByText("Terms & Conditions");
    clickTnC.click();
    Locator closeTCPop = page.locator("xpath=//*[name()='path' and contains(@d,'M15.172 2.')]");
    closeTCPop.click();
    Locator registrationbox = page.locator("#subscribe");
    registrationbox.click();
    Locator clickDMP = page.getByText("Direct Marketing purposes");
    clickDMP.click();
    Locator closeDMP = page.locator("xpath= //*[name()='path' and contains(@d,'M15.172 2.')]");
    closeDMP.click();
    // Locator shopLocator = page.getByText("OL");
    // shopLocator.click();
    page.pause();
    Locator createanaccount = page.getByRole(AriaRole.BUTTON,
    new Page.GetByRoleOptions().setName("Create an account"));
    createanaccount.click();
    // Close Playwright =================
    playwright.close();

  }

  public static void selectTitle(Page page, int titleOption) {
    Locator fillTitle = page.locator("#title");
    fillTitle.click();

    switch (titleOption) {
      case 1: // Mr
        Locator mrLocator = page.getByText("Mr");
        mrLocator.click();
        break;
      case 2: // Mrs
        Locator mrsLocator = page.getByText("Mrs");
        mrsLocator.click();
        break;
      case 3: // Ms
        Locator msLocator = page.getByText("Ms");
        msLocator.click();
        break;
      default:
        Locator defaultLocator = page.getByText("Mr");
        defaultLocator.click();
        break;
    }
  }

  public static void selectTitle(Page page, String title) {
    Locator fillTitle = page.locator("#title");
    fillTitle.click();

    if (title.equals("Mr")) {
      page.getByText("Mr").click();
    } else if (title.equals("Mrs")) {
      page.getByText("Mrs").click();
    } else if (title.equals("Ms")) {
      page.getByText("Ms").click();
    } else {
      page.getByText("Mr").click();
    }
  }

  public static void inputFirstName(Page page, String firstName){
    String regex = "^[A-Z][a-zA-Z' -]{1,}$";
    Locator fillFirstName = page.locator("#firstName");
    if (firstName.matches(regex)){
      fillFirstName.fill(firstName);
      page.pause();
    } else {
      Locator fillFirstNameError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillFirstNameError).hasText("This field can't be empty or input number/special characters");
    }
   }

  public static void inputLastName(Page page, String lastName){
    String regex = "^[A-Z][a-zA-Z' -]{1,}$";
    Locator fillLastName = page.locator("#lastName");
    if (lastName.matches(regex)){
      fillLastName.fill(lastName);
      page.pause();
    } else {
      Locator fillFirstNameError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillFirstNameError).hasText("This field can't be empty or input number/special characters");
    }
   }

  public static void inputemailAddress(Page page, String emailaddress) {
    String regex = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6})$";
    Locator fillEmail = page.locator("#Email");
    if (emailaddress.matches(regex)){
      fillEmail.fill(emailaddress);
      page.pause();
    } else {
      Locator fillEmailError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillEmailError).hasText("Please enter a valid email addres");
    }
   }

  public static void selectHKphone(Page page, String countrycode, String phonenNumber) {
    Locator fillPhoneID = page.locator("#countryCode").nth(0);
    Locator fillPhoneNum = page.locator("#telephone");
    String regex = "^[2-9]\\d{7}$";

    if (countrycode.equals("+852") && phonenNumber.matches(regex)) {
      fillPhoneID.click();
      Locator fillPhoneID1 = page.getByText("+852");
      fillPhoneID1.click();
      page.getByText("+852");
      fillPhoneNum.fill(phonenNumber);
    }  else {
      Locator mcphoenError = page.locator("xpath= //p[normalize-space()='Please enter a valid mobile number']");
      PlaywrightAssertions.assertThat(mcphoenError).hasText("Please enter a valid mobile number");
    }
  }
    public static void selectMCphone(Page page, String countrycode, String phonenNumber) {
    Locator fillPhoneID = page.locator("#countryCode").nth(0);
    Locator fillPhoneNum = page.locator("#telephone");
    String regex = "^[2-9]\\d{7}$";

    if (countrycode.equals("+853") && phonenNumber.matches(regex)) {
      fillPhoneID.click();
      Locator fillPhoneID1 = page.getByText("+853");
      fillPhoneID1.click();
      page.getByText("+853");
      fillPhoneNum.fill(phonenNumber);
    }  else {
      Locator mcphoenError = page.locator("xpath= //p[normalize-space()='Please enter a valid mobile number']");
      PlaywrightAssertions.assertThat(mcphoenError).hasText("Please enter a valid mobile number");
    }
  }

    public static void selectCNphone(Page page, String countrycode, String phonenNumber) {
    Locator fillPhoneID = page.locator("#countryCode").nth(0);
    Locator fillPhoneNum = page.locator("#telephone");
    String regex = "^1[0-9]{10}$";

    if (countrycode.equals("+86") && phonenNumber.matches(regex)) {
      fillPhoneID.click();
      Locator fillPhoneID1 = page.getByText("+86");
      fillPhoneID1.click();
      page.getByText("+86");
      fillPhoneNum.fill(phonenNumber);
    }  else {
      Locator mcphoenError = page.locator("xpath= //p[normalize-space()='Please enter a valid mobile number']");
      PlaywrightAssertions.assertThat(mcphoenError).hasText("Please enter a valid mobile number");
    }
  }
  
  public static void inputPassword(Page page, String originalPassword, String confirmPassword) {
    Locator fillPassword = page.locator("#Password");
    Locator ConfirmPassword = page.locator("#ConfirmPassword");
    String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
    if (originalPassword.matches(regex) && originalPassword.equals(confirmPassword)) {
      fillPassword.fill(originalPassword);
      ConfirmPassword.fill(confirmPassword);
      page.pause();
    } else if (originalPassword != confirmPassword){
      Locator passwordError = page.locator("xpath= //p[normalize-space()='Please enter the same password as above']");
      PlaywrightAssertions.assertThat(passwordError).hasText("Please enter the same password as above");
    } else {
      Locator passwordError = page.locator("xpath= //p[contains(text(),'Password must contain minimum 6, maximum 20 charac')]");
      PlaywrightAssertions.assertThat(passwordError).hasText("Password must contain minimum 6, maximum 20 character, at least one numeric and one alphabetic character");
    }

  }

  public static void clickTNC(Page page) {
    Locator clickTnC = page.getByText("Terms & Conditions");
    clickTnC.click();
    Locator closeTCPop = page.locator("xpath=//*[name()='path' and contains(@d,'M15.172 2.')]");
    closeTCPop.click();
  }

  public static void subscribe(Page page) {
    Locator registrationbox = page.locator("#subscribe");
    registrationbox.click();
  }

  public static void directMarketingPurposes(Page page) {
    Locator clickDMP = page.getByText("Direct Marketing purposes");
    clickDMP.click();
    Locator closeDMP = page.locator("xpath= //*[name()='path' and contains(@d,'M15.172 2.')]");
    closeDMP.click();
  }

  public static void createanaccount(Page page) {
    Locator createanaccount = page.getByRole(AriaRole.BUTTON,
    new Page.GetByRoleOptions().setName("Create an account"));
    createanaccount.click(); 
  }
}
