package Util;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class HandlePaymentMethod {
    public static void selectPaymentMethod(Page page, int paymentOptions) {
        // paymentOptions
        // 1 = alipayHK
        // 2 = Visa
        // 3 = alipayCN
        // 4 = Payme
        // 5 = Octopus
        // 6 = WeChatpay
        switch (paymentOptions) {
            case 1:
                Locator alipayHk = page.locator("#paymentMethod--alipay_hk");
                alipayHk.scrollIntoViewIfNeeded();
                alipayHk.click();
                break;
            case 2:
                Locator creditCard = page.locator("#paymentMethod--chcybersource");
                creditCard.scrollIntoViewIfNeeded();
                creditCard.click();
                FrameLocator frame1 = page
                        .frameLocator("xpath=//div[@id=\"number-container\"]/iframe[@title=\"secure payment field\"]");
                Locator creditCardNumberInput = frame1.locator("xpath=//form/input[@id=\"number\"]");
                creditCardNumberInput.scrollIntoViewIfNeeded();
                creditCardNumberInput.click();
                creditCardNumberInput.fill("4242424242424242");
                Locator cardExpiryMonthDropDown = page.locator("xpath=//select[@class=\"dateMonth\"]");
                cardExpiryMonthDropDown.scrollIntoViewIfNeeded();
                cardExpiryMonthDropDown.click();
                cardExpiryMonthDropDown.selectOption(new SelectOption().setLabel("7"));
                // Locator cardExpiryMonth = frame
                // .locator("xpath=//div[@id=\"datePicker\"]/div[@class=\"dateMonth-box\"]");
                // cardExpiryMonth.scrollIntoViewIfNeeded();
                // cardExpiryMonth.selectOption(new SelectOption().setLabel("7"));
                Locator cardExpiryYearDropDown = page.locator("xpath=//select[@class=\"dateYear\"]");
                cardExpiryYearDropDown.scrollIntoViewIfNeeded();
                cardExpiryYearDropDown.click();
                cardExpiryYearDropDown.selectOption(new SelectOption().setLabel("27"));
                // Locator cardExpiryYear =
                // frame.locator("xpath=//div[@id=\"datePicker\"]/div[@class=\"dateYear-box\"]");
                // cardExpiryYear.scrollIntoViewIfNeeded();
                // cardExpiryMonth.selectOption(new SelectOption().setLabel("2027"));
                FrameLocator frame2 = page.frameLocator(
                        "xpath=//div[@id=\"securityCode-container\"]/iframe[@title=\"secure payment field\"]");
                Locator cardSecurityCode = frame2.locator("xpath=//input[@id=\"securityCode\"]");
                cardSecurityCode.scrollIntoViewIfNeeded();
                cardSecurityCode.fill("123");
                cardSecurityCode.blur();
                break;
            case 3:
                Locator alipayCn = page.locator("#paymentMethod--alipay_cn");
                alipayCn.scrollIntoViewIfNeeded();
                alipayCn.click();
                break;
            case 4:
                Locator payMe = page.locator("#paymentMethod--payme");
                payMe.scrollIntoViewIfNeeded();
                payMe.click();
                break;
            case 5:
                Locator octopus = page.locator("#paymentMethod--octopus");
                octopus.scrollIntoViewIfNeeded();
                octopus.click();
                break;
            case 6:
                Locator weChatPayHk = page.locator("#paymentMethod--wechatpay_hk");
                weChatPayHk.scrollIntoViewIfNeeded();
                weChatPayHk.click();
                break;
        }
    }

    public static void confirmCheckout(Page page) {
        page.waitForTimeout(3000);
        // Agree T&C checkbox
        Locator termAndConditionButton = page.locator("xpath=//div[@class=\"checkoutPage-payTips-bZi\"]/button");
        termAndConditionButton.scrollIntoViewIfNeeded();
        termAndConditionButton.click();
        // Pay now button
        Locator payNowButton = page.locator("xpath=//div[@class=\"checkoutPage-btnbox-dGw\"]/button[@type=\"submit\"]");
        payNowButton.scrollIntoViewIfNeeded();
        payNowButton.click();
    }

    public static void cancelTransaction(Page page) {
        page.waitForTimeout(5000);
        page.locator("xpath=//td/button[text()=\"Cancel Transaction\"]").click();
        page.locator("xpath=//a[@class=\"am-dialog-button\"][text()=\"Yes\"]").click();
    }

    // @Test
    // public void testManningsThroatWesternPage() {
    // try (Playwright playwright = Playwright.create()) {
    // Browser browser = playwright.chromium().launch(
    // new BrowserType.LaunchOptions()
    // .setHeadless(false)
    // .setArgs(Arrays.asList("--start-maximized"))
    // .setSlowMo(2000));
    // // Create incognito context
    // try (BrowserContext context = browser.newContext()) {
    // Page page = context.newPage();

    // page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I
    // understand")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to
    // cart")).nth(0).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
    // Quantity")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle
    // mini cart. You have 2"))
    // .click();
    // page.locator(".deliveryMethod-item_content--Fx > div:nth-child(3) >
    // .icon-root-W-v > svg").first()
    // .click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
    // Quantity")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout
    // securely")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest
    // checkout")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location"))
    // .fill("Lai Chi Kok");
    // page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v >
    // svg").first().click();
    // page.locator("input[name=\"firstName\"]").click();
    // page.locator("input[name=\"firstName\"]").fill("John Doe");
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry"))
    // .locator("input[name=\"email\"]").click();
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry"))
    // .locator("input[name=\"email\"]").fill("abc@gmail.com");
    // page.locator("#areaCode div").nth(4).click();
    // page.getByRole(AriaRole.OPTION, new
    // Page.GetByRoleOptions().setName("+852")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).fill("61236123");
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm
    // Details")).click();
    // }
    // }
    // }
    // PaymentMehod starts here
    // 1 = alipayHK
    // 2 = Visa
    // 3 = alipayCN
    // 4 = Payme
    // 5 = Octopus
    // 6 = WeChatpay
    // int min = 1;
    // int max = 6;
    // int paymentOptions = min + (int) (Math.random() * ((max - min) + 1));

    // Obsolete code
    // int paymentOptions = 1;
    // switch (paymentOptions) {
    // case 1:
    // page.locator("#paymentMethod--alipay_hk").first().click();
    // break;
    // case 2:
    // page.locator("#paymentMethod--chcybersource").first().click();
    // break;
    // case 3:
    // page.locator("#paymentMethod--alipay_cn").first().click();
    // break;
    // case 4:
    // page.locator("#paymentMethod--payme").first().click();
    // break;
    // case 5:
    // page.locator("#paymentMethod--octopus").first().click();
    // break;
    // case 6:
    // page.locator("#paymentMethod--wechatpay_hk").first().click();
    // break;
    // }
    // page.locator("xpath=//*[name()='path' and contains(@d,'M4 5a1 1
    // 0')]").click();
    // page.locator("xpath=//span[normalize-space()='Pay Now']").click();
    // page.waitForTimeout(5000);
    // String paymentUrl = page.url();
    // System.out.println("Current Page URL is: " + paymentUrl);
    // String verifyUrl = paymentUrl.substring(0, paymentUrl.indexOf('?'));
    // System.out.println("verifyUrl Page URL is: " + verifyUrl);
    // page.locator("xpath=//button[normalize-space()='Cancel
    // Transaction']").click();
    // page.locator("xpath=//a[normalize-space()='Yes']").click();
    // PlaywrightAssertions.assertThat(page).hasURL(Pattern.compile(verifyUrl));
    // page.locator("xpath=//button[normalize-space()='go back']").click();
    // }
    // }
    // }

}
