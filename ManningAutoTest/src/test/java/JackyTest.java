import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class JackyTest {
    @Test
    public void JackyTest() {
    Playwright playwright = Playwright.create();
    BrowserType browserType = playwright.chromium();
    Page page = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();

    page.navigate("https://www.mannings.com.hk/en/register");
    Locator closeUnderstand = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
    closeUnderstand.click();
    Locator fillFirstName = page.locator("#firstName");
    String teststring1 = "2";
    RegistrationManningTest.inputFirstName(page, teststring1);   
}

}