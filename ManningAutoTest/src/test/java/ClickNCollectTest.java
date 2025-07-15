
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
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

        Faker faker = new Faker();
        String email = faker.name().firstName() + "_" + faker.name().lastName() + "@gmail.com";
        String fullName = faker.name().firstName() + " " + faker.name().lastName();
        int regionCode = faker.number().numberBetween(0, 2);
        String region = "Kowloon";
        String district = faker.address().city();
        String streetNumber = faker.address().streetAddress();
        String buildingName = faker.address().buildingNumber();
        String floor = faker.number().randomDigitNotZero() + "/F";
        String countryCode = "+852";
        String mobile = "61236123";

        System.out.println("email is : " + email);
        System.out.println("Full Name is : " + fullName);
        System.out.println("Region is : " + region);
        System.out.println("District is : " + district);
        System.out.println("Street Number is : " + streetNumber);
        System.out.println("Building is : " + buildingName);
        System.out.println("Floor is : " + floor);
        System.out.println("Country Code is : " + countryCode);
        System.out.println("Mobile is : " + mobile);

        try (Playwright playwright = Playwright.create()) {
            page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
            page.locator(".deliveryMethod-item_content--Fx > div:nth-child(3) > .icon-root-W-v > svg").first().click(); //select Click and collect
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).fill("Lai Chi Kok");
            page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v > svg").first().click();
            page.locator("input[name=\"firstName\"]").click();
            page.locator("input[name=\"firstName\"]").fill(fullName);
            page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").click();
            page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").fill(email);
            page.locator("#areaCode div").nth(4).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("+852")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).fill(mobile);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();

            browser.close();
        }
    }

// improved code starts here
    public static void AddToCart(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).nth(0).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2")).click();
    }

    public static void selectClickNCollect(Page page) {
        page.locator(".deliveryMethod-item_content--Fx > div:nth-child(3) > .icon-root-W-v > svg").first().click();
    }

    public static void increaseQtyNCheckoutSecurely(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
    }

    public static void GuestCheckout(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
    }

    public static void InputCheckOutInfo(Page page, String email, String fullname, String mobile) {
        // Faker faker = new Faker();
        //   String fullName = faker.name().firstName() + " " + faker.name().lastName();
        // int regionCode = faker.number().numberBetween(0, 2);
        // String region = "Kowloon";
        // String district = faker.address().city();
        // String streetNumber = faker.address().streetAddress();
        // String buildingName = faker.address().buildingNumber();
        // String floor = faker.number().randomDigitNotZero() + "/F";
        // String countryCode = "+852";

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location")).fill("Lai Chi Kok");
        page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v > svg").first().click();
        page.locator("input[name=\"firstName\"]").click();
        page.locator("input[name=\"firstName\"]").fill(fullname);
        page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").click();
        page.locator("form").filter(new Locator.FilterOptions().setHasText("Full NameEmailCountry")).locator("input[name=\"email\"]").fill(email);
        page.locator("#areaCode div").nth(4).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("+852")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile number")).fill(mobile);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();
    }
}
