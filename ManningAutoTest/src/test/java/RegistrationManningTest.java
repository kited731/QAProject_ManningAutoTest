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
      Locator fillTitle1 = page.getByText("Mr.");
      fillTitle1.click();
      Locator fillFirstName = page.locator("#firstName");
      fillFirstName.fill("JK");
      Locator fillLastName = page.locator("#lastName");
      fillLastName.fill("Lee");
      Locator fillEmail = page.locator("#Email");
      fillEmail.fill("Lee");
    //   Locator fillPhoneID = page.locator("#phoneId_label");
    //   Locator fillPhoneID1 = page.getByText("+852.");
    //   fillPhoneID1.click();
      page.pause();
    //   Locator shopLocator = page.getByText("OL");
    //   shopLocator.click();
      
      // Close Playwright =================
      playwright.close();

	}

}
