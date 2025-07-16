//HomeDelivery>HDeli

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class HomeDeliveryTest {

    @Test
    public void testManningsThroatWesternPage() {
        Browser browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
        Page page = browser.newPage();

        Faker faker = new Faker();
        String email = faker.name().firstName() + "_" + faker.name().lastName() + "@gmail.com";
        String fullName = faker.name().firstName() + " " + faker.name().lastName();
        int regionCode = faker.number().numberBetween(0, 2);
        String countryCode = "+852";

        System.out.println("email is : " + email);
        System.out.println("Full Name is : " + fullName);
        System.out.println("Country Code is : " + countryCode);

        try (Playwright playwright = Playwright.create()) {
            page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
            // for (int i = 0; i < 2; i++) {
            //     page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            // }
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
            page.getByText("Hong Kong").nth(1).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
            page.locator("xpath=//input[@id='email']").click();
            page.locator("xpath=//input[@id='email']").fill(email);
            page.locator("xpath=//input[@id='firstname']").fill(fullName);
            page.locator("xpath=//div[contains(@class,\"guestForm-region\")]").click();
            page.getByText("Kowloon").click();
            Locator districtInput = page.locator("#region-root-k7a");
            districtInput.scrollIntoViewIfNeeded();
            districtInput.click();
            districtInput.getByText("Cheung Sha Wan").click();
            page.locator("xpath=//input[@id='street0']").fill("1018 Tai Nan W St");
            page.locator("xpath=//input[@id='street1']").fill("Orient International Tower");
            page.locator("xpath=//input[@id='street2']").fill("1507");
            Locator areaCodeInput = page.locator("#areaCode");
            areaCodeInput.scrollIntoViewIfNeeded();
            areaCodeInput.click();
            areaCodeInput.getByText("+852").click();
            Locator telephone = page.locator("#telephone");
            telephone.fill("61236123");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm address")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();
            page.pause();
            browser.close();
        }
    }
//improved code starts here

    public static void AddToCart(Page page) {
        page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
    }

    public static void SelectHomeDelivery(Page page) {
        page.getByText("Hong Kong").nth(1).click();
    }

    public static void CheckoutSecurely(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
    }

    public static void GuestCheckout(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
    }

    public static void HDeliInputInfo(Page page) {
        Faker faker = new Faker();
        String email = faker.name().firstName() + "_" + faker.name().lastName() + "@gmail.com";
        String fullName = faker.name().firstName() + " " + faker.name().lastName();
        // // page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
        // // for (int i = 0; i < 2; i++) {
        // //     page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
        // // }
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
        // page.getByText("Hong Kong").nth(1).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
        page.locator("xpath=//input[@id='email']").click();
        page.locator("xpath=//input[@id='email']").fill(email);
        page.locator("xpath=//input[@id='firstname']").fill(fullName);
        page.locator("xpath=//div[contains(@class,\"guestForm-region\")]").click();
        page.getByText("Kowloon").click();
        Locator districtInput = page.locator("#region-root-k7a");
        districtInput.scrollIntoViewIfNeeded();
        districtInput.click();
        districtInput.getByText("Cheung Sha Wan").click();
        page.locator("xpath=//input[@id='street0']").fill("1018 Tai Nan W St");
        page.locator("xpath=//input[@id='street1']").fill("Orient International Tower");
        page.locator("xpath=//input[@id='street2']").fill("1507");
        Locator areaCodeInput = page.locator("#areaCode");
        areaCodeInput.scrollIntoViewIfNeeded();
        areaCodeInput.click();
        areaCodeInput.getByText("+852").click();
        Locator telephone = page.locator("#telephone");
        telephone.fill("61236123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm address")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();
        page.pause();
    }

}
