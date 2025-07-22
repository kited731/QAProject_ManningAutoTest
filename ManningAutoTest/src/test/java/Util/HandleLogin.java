package Util;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

//Try Push
public class HandleLogin {
        public static void goToLoginPage(Page page) {
                page.locator("#header").getByText("Sign up | Log in").click();
        }

        public static void inputLoginEmailOrPhone(Page page, String emailOrPhone) {
                Locator loginEmailOrPhone = page.getByRole(AriaRole.TEXTBOX,
                                new Page.GetByRoleOptions().setName("Please enter a valid email/"));
                loginEmailOrPhone.scrollIntoViewIfNeeded();
                loginEmailOrPhone.click();
                loginEmailOrPhone.fill(emailOrPhone);
        }

        public static void inputPassword(Page page, String password) {
                Locator passwordInput = page.getByRole(AriaRole.TEXTBOX,
                                new Page.GetByRoleOptions().setName("Please provide password"));
                passwordInput.scrollIntoViewIfNeeded();
                passwordInput.click();
                passwordInput.fill(password);
        }

        public static void clickLoginButton(Page page) {
                Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
                loginButton.scrollIntoViewIfNeeded();
                loginButton.click();
        }

        // Old Code
        // public void Login() {
        // System.out.println("In Login");

        // // Initialize Personal Details
        // // "new_user@example.com"
        // // Replace with your email
        // String email = "new_user@example.com";
        // String password = "NewSecure123!";

        // // Settings
        // int clickInteval = 1000;
        // int waitForTimeout = 5000;

        // // Initialize Playwright
        // Playwright playwright = Playwright.create();
        // Browser browser = playwright.chromium().launch(
        // new LaunchOptions()
        // .setHeadless(false)
        // .setArgs(Arrays.asList("--start-maximized"))
        // .setSlowMo(clickInteval));
        // BrowserContext context = browser.newContext(
        // new Browser.NewContextOptions().setViewportSize(null));

        // Page page = context.newPage();

        // // Add try-catch-finally for playwright
        // try {

        // page.navigate("https://www.mannings.com.hk/en");
        // page.waitForTimeout(waitForTimeout);
        // HandlePopUp.closeCookiesPopUp(page);
        // HandlePopUp.closePromotionPopUp(page);

        // page.locator("#header").getByText("Sign up | Log in").click();
        // page.getByRole(AriaRole.TEXTBOX,
        // new Page.GetByRoleOptions().setName("Please enter a valid email/"))
        // .click();
        // page.getByRole(AriaRole.TEXTBOX,
        // new Page.GetByRoleOptions().setName("Please enter a valid email/"))
        // .fill(email);
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please
        // provide password"))
        // .click();
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please
        // provide password"))
        // .fill(password);

        // HandleCaptcha.clickCaptchaTextbox(page);

        // page.getByRole(AriaRole.CHECKBOX, new
        // Page.GetByRoleOptions().setName("Remember me")).check();
        // page.getByRole(AriaRole.BUTTON, new
        // Page.GetByRoleOptions().setName("Login")).click();

        // HandleOTP.clickOTPtextbox(page);

        // page.waitForTimeout(waitForTimeout);
        // HandleRestoreShoppingCart.restoreShoppingCart(page);

        // // assertTrue(page.isVisible("#user-profile-icon"), "User profile icon should
        // be
        // // visible after login.");
        // page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("My
        // account")).click();

        // } // catch (Exception e) {
        // // System.out.println(e.getMessage());
        // // }
        // finally {
        // page.waitForTimeout(waitForTimeout);
        // page.close();
        // browser.close();
        // playwright.close();
        // }

        // }

}
