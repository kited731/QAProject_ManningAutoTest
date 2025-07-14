import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

public class RegistrationManningTest {
    @Test
    void testUserRegistrationFlow() {
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newContext().newPage();

        page.navigate("https://www.mannings.com.hk/en");
        Locator closeUnderstand = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
        closeUnderstand.click();
        Locator clickRegistration = page.getByText("Sign up | Log in");
        clickRegistration.click();
        Locator clickCreate = page.getByText("Create an account");
        clickCreate.click();
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
        Locator confirmPassword = page.locator("#ConfirmPassword");
        confirmPassword.fill("12345678");
        Locator clickTnC = page.getByText("Terms & Conditions");
        clickTnC.click();
        Locator closeTCPop = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close"));
        closeTCPop.click();
        Locator registrationBox = page.locator("#subscribe");
        registrationBox.click();
        Locator clickDMP = page.getByText("Direct Marketing purposes");
        clickDMP.click();
        Locator closeDMP = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close"));
        closeDMP.click();
        page.waitForTimeout(3000);
        Locator createAnAccount = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an account"));
        createAnAccount.click();
        // Added: Assertion to verify successful registration
        assertTrue(page.isVisible(page.getByText("Registration successful")), "Registration was not successful");

        // Close Playwright =================
        playwright.close();
    }
}
