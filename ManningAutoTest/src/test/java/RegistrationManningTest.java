import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
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

  public void inputFirstName(Page page, String firstName){
    Locator fillFirstName = page.locator("#firstName");
    fillFirstName.fill(firstName);
    Locator fillFirstNameError = page.locator("xpath= //p[@class='message-root_error-UMR message-root-PRA font-normal leading-none mt-1 text-colorDefault text-xs text-left leading-4 font-semibold text-error']");
    switch (fillOption) {
      case 1: //Fill Correct in First Name Field
        firstName = "Test";
        fillFirstName.fill(firstName);
        break;
        case 2: //Fill . in First Name Field
          firstName = ".";
          fillFirstNameError.getByText("This field can't be empty or input number/special characters");
          System.out.println("First Name shows error");
          break;
        case 3; //Fill space in First Name Field
          firstName = " ";
          fillFirstNameError.getByText("This field can't be empty or input number/special characters");
          System.out.println("First Name shows error");
          break;
        case 4: //Fill emty in First Name Field
          firstName = null;
          fillFirstNameError.getByText("This field can't be empty or input number/special characters");
          System.out.println("First Name shows error");
          break;
      default:
        break;
    }
  }

  public void inputLastName() {

  }

  public void inputPassword(Page page, String originalPassword, String confirmPassword) {
    // 123456 pw
    // 234567 conf.pw
  }
}
