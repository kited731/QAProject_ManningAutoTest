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

    // Way 1
    // Locator targetLocator = page.getByText(title);
    // targetLocator.click();

    // Way 2
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
    final Pattern pattern = Pattern.compile("^[a-zA-Z]\\s");
    final Matcher matcher = pattern.matcher(firstName);
    Locator fillFirstName = page.locator("#firstName");
    fillFirstName.fill(firstName);
    if (firstName.isEmpty() || matcher.matches() == false){
      fillFirstName.blur();
      Locator fillFirstNameError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillFirstNameError).hasText("This field can't be empty or input number/special characters");
    } else {
      fillFirstName.blur();
    }
   }

  public static void inputLastName(Page page, String lastName){
    final Pattern pattern = Pattern.compile("^[a-zA-Z]\\s");
    final Matcher matcher = pattern.matcher(lastName);
    Locator fillLastName = page.locator("#lastName");
    fillLastName.fill(lastName);
    if (lastName.isEmpty() || matcher.matches() == false){
      fillLastName.blur();
      Locator fillLastNameError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillLastNameError).hasText("This field can't be empty or input number/special characters");
    } else {
      fillLastName.blur();
    }
   }

  public static void inputemailAddress(Page page, String emailaddress) {
    final Pattern pattern = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6})$");
    final Matcher matcher = pattern.matcher(emailaddress);
    Locator fillEmail = page.locator("#Email");
    fillEmail.fill(emailaddress);
    if (emailaddress.isEmpty() || matcher.matches() == false){
      fillEmail.blur();
      Locator fillEmailError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
      PlaywrightAssertions.assertThat(fillEmailError).hasText("Please enter a valid email addres");
    } else {
      fillEmail.blur();
    }
   }

  public static void selectCountryCode(Page page, String countrycode) {
    Locator fillPhoneID = page.locator("#countryCode").nth(0);
    fillPhoneID.click();

    // Way 2
    if (countrycode.equals("+852")) {
      page.getByText("+852").click();
    } else if (countrycode.equals("+853")) {
      page.getByText("+853").click();
    } else if (countrycode.equals("+86")) {
      page.getByText("+86").click();
    } else {
      page.getByText("+852").click();
    }
  }

  //   public static void inputPhoneNumber(Page page, Number phone) {
    
  //   final Pattern pattern = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6})$");
  //   final Matcher matcher = pattern.matcher(phone);
  //   Locator fillEmail = page.locator("#Email");
  //   fillEmail.fill(emailaddress);
  //   if (emailaddress.isEmpty() || matcher.matches() == false){
  //     fillEmail.blur();
  //     Locator fillEmailError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
  //     PlaywrightAssertions.assertThat(fillEmailError).hasText("Please enter a valid email addres");
  //   } else {
  //     fillEmail.blur();
  //   }
  // }

  public void inputPassword(Page page, String originalPassword, String confirmPassword) {
    // 123456 pw
    // 234567 conf.pw
  }
}
