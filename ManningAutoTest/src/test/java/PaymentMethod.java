
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class PaymentMethod {

    @Test
    public void testManningsThroatWesternPage() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000)
            );
            // Create incognito context
            try (BrowserContext context = browser.newContext()) {
                Page page = context.newPage();

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
                //PaymentMehod starts here
                // 1 = alipayHK
                // 2 = Visa
                // 3 = alipayCN
                // 4 = Payme
                // 5 = Octopus
                // 6 = WeChatpay
                // int min = 1;
                // int max = 6;
                // int paymentOptions = min + (int) (Math.random() * ((max - min) + 1));

                int paymentOptions = 1;

                switch (paymentOptions) {
                    case 1:
                        page.locator("#paymentMethod--alipay_hk").first().click();
                        break;
                    case 2:
                        page.locator("#paymentMethod--chcybersource").first().click();
                        break;
                    case 3:
                        page.locator("#paymentMethod--alipay_cn").first().click();
                        break;
                    case 4:
                        page.locator("#paymentMethod--payme").first().click();
                        break;
                    case 5:
                        page.locator("#paymentMethod--octopus").first().click();
                        break;
                    case 6:
                        page.locator("#paymentMethod--wechatpay_hk").first().click();
                        break;
                }

                page.locator("xpath=//*[name()='path' and contains(@d,'M4 5a1 1 0')]").click();
                page.locator("xpath=//span[normalize-space()='Pay Now']").click();

                page.waitForTimeout(5000);
                String paymentUrl = page.url();
                System.out.println("Current Page URL is: " + paymentUrl);

                String verifyUrl = paymentUrl.substring(0, paymentUrl.indexOf('?'));
                System.out.println("verifyUrl Page URL is: " + verifyUrl);
                page.locator("xpath=//button[normalize-space()='Cancel Transaction']").click();
                page.locator("xpath=//a[normalize-space()='Yes']").click();
                PlaywrightAssertions.assertThat(page).hasURL(Pattern.compile(verifyUrl));
                page.locator("xpath=//button[normalize-space()='go back']").click();
                page.pause();
            }
        }
    }
}
