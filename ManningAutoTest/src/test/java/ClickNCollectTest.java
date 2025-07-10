package Project.demo;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class ClickNCollectTest {

    @Test
    public void testManningsThroatWesternPage() { // <-- Make method public
        Browser browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
        Page page = browser.newPage();

        try (Playwright playwright = Playwright.create()) {
            page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
            page.locator(".deliveryMethod-item_content--Fx > div:nth-child(3) > .icon-root-W-v > svg").first().click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).fill("Lai Chi Kok");
            page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v > svg").first().click();
            page.locator("input[name=\"firstName\"]").click();
            page.locator("input[name=\"firstName\"]").fill("John Doe");
            page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").click();
            page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").fill("abc@gmail.com");
            page.locator("#areaCode div").nth(4).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("+852")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).fill("61236123");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();

            page.pause();
            browser.close();
        }
    }
}
