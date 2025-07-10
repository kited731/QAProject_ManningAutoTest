import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class RegistrationManningTest {
	@Test
	void contextLoads() {
	  Playwright playwright = Playwright.create();
      BrowserType browserType = playwright.chromium();
      Page page = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();

      page.navigate("https://www.mannings.com.hk/en");
      //Locator closePop = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("X"));
      //closePop.click();
      Locator closeUnderstand = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
      closeUnderstand.click();
      Locator clickRegistration= page.getByText("Sign up | Log in").nth(1);
      clickRegistration.click();
      Locator clickCreate= page.getByText("Create an account").nth(1);
      clickCreate.click();
      //Locator clickOption1 = page.getByText("Mr.").nth(1);
      //clickOption1.click();
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
      Locator clickTnC= page.getByText("Terms & Conditions");
      clickTnC.click();
      Locator closeTCPop = page.locator("xpath=//*[name()='path' and contains(@d,'M15.172 2.')]");
      closeTCPop.click(); 
      Locator registrationbox= page.locator("#subscribe");
      registrationbox.click();
      Locator clickDMP = page.getByText("Direct Marketing purposes");
      clickDMP.click();
      Locator closeDMP = page.locator("xpath= //*[name()='path' and contains(@d,'M15.172 2.')]");
      closeDMP.click();
    //   Locator shopLocator = page.getByText("OL");
    //   shopLocator.click();
      Locator createanaccount = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an account"));
      createanaccount.click();
      page.pause();
      // Close Playwright =================
      playwright.close();

	}

}
