package Util;
//HomeDelivery>HDeli

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class HandleHomeDeliveryJ {

    @Test
    public void testManningsThroatWesternPage() {
        Browser browser = Playwright.create().chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
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
            // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
            // Quantity")).click();
            // }
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Quantity")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle mini cart. You have 2"))
                    .click();
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
    // improved code starts here

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
        // //
        // page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I
        // understand")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to
        // cart")).nth(0).click();
        // // for (int i = 0; i < 2; i++) {
        // // page.getByRole(AriaRole.BUTTON, new
        // Page.GetByRoleOptions().setName("Increase Quantity")).click();
        // // }
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
        // Quantity")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle
        // mini cart. You have 2")).click();
        // page.getByText("Hong Kong").nth(1).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout
        // securely")).click();
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest
        // checkout")).click();
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

    public static void increaseQty(Page page, int targetQty) {
        Locator increaseQtyButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Increase Quantity"));
        Locator qtyInput = page.locator("xpath=//input[@aria-label=\"Item Quantity\"]");
        int currentQty;
        currentQty = Integer.parseInt(qtyInput.inputValue());

        if (targetQty > currentQty) {
            do {
                increaseQtyButton.click();
                page.waitForTimeout(1000);
                currentQty = Integer.parseInt(qtyInput.inputValue());
            } while (targetQty != currentQty);
        }
    }

    public static void decreaseQty(Page page, int targetQty) {
        Locator decreaseQtyButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Decrease Quantity"));
        Locator qtyInput = page.locator("xpath=//input[@aria-label=\"Item Quantity\"]");
        int currentQty;

        currentQty = Integer.parseInt(qtyInput.inputValue());

        if (targetQty < currentQty) {
            do {
                decreaseQtyButton.click();
                page.waitForTimeout(1000);
                currentQty = Integer.parseInt(qtyInput.inputValue());
            } while (targetQty != currentQty);
        }
    }

    public static void inputEmail(Page page, String email) {
        Locator emailInput = page
                .locator("xpath=//form[contains(@class,\"guestForm\")]/div/div/span/span/input[@id=\"email\"]");
        emailInput.scrollIntoViewIfNeeded();
        emailInput.fill(email);
    }

    public static void inputFullName(Page page, String fullName) {
        Locator fullNameInput = page.locator("#firstname");
        fullNameInput.scrollIntoViewIfNeeded();
        fullNameInput.fill(fullName);
    }

    public static void selectRegion(Page page, String region) {
        Locator regionInput = page.locator("#region-root-aGR");
        regionInput.scrollIntoViewIfNeeded();
        regionInput.click();
        Locator regionInputOption = page
                .locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + region + "\"]");
        regionInputOption.scrollIntoViewIfNeeded();
        regionInputOption.click();
    }

    public static void selectDistrict(Page page, String district) {
        Locator districtInput = page.locator("#region-root-k7a");
        districtInput.scrollIntoViewIfNeeded();
        districtInput.click();
        Locator districtInputOption = page
                .locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + district + "\"]");
        districtInputOption.scrollIntoViewIfNeeded();
        districtInputOption.click();
    }

    public static void inputStreetNumber(Page page, String streetNumber) {
        Locator streetInput = page.locator("#street0");
        streetInput.scrollIntoViewIfNeeded();
        streetInput.fill(streetNumber);
    }

    public static void inputBuildingAndEstate(Page page, String buildingName) {
        Locator buildingInput = page.locator("#street1");
        buildingInput.scrollIntoViewIfNeeded();
        buildingInput.fill(buildingName);
    }

    public static void inputUnitAndFloor(Page page, String unitFloor) {
        Locator unitFloorInput = page.locator("#street2");
        unitFloorInput.scrollIntoViewIfNeeded();
        unitFloorInput.fill(unitFloor);
    }

    public static void selectCountryCode(Page page, String countryCode) {
        if (countryCode.equals("852")) {
            countryCode = "+852";
        }
        if (countryCode.equals("853")) {
            countryCode = "+853";
        }
        if (countryCode.equals("86")) {
            countryCode = "+86";
        }

        Locator countryCodeInput = page.locator("#areaCode");
        countryCodeInput.scrollIntoViewIfNeeded();
        countryCodeInput.click();
        page.locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + countryCode + "\"]").click();
    }

    public static void inputMobileNumber(Page page, String countryCode, String mobileNumber) {
        if (countryCode.equals("852")) {
            countryCode = "+852";
        }
        if (countryCode.equals("853")) {
            countryCode = "+853";
        }
        if (countryCode.equals("86")) {
            countryCode = "+86";
        }
        Locator phoneInput = page.locator("#telephone");
        phoneInput.fill(mobileNumber);
        if (!verifyMobile(countryCode, mobileNumber)) {
            Locator errorMessageLocator = page.locator("xpath=//p[text()=\"Please enter a valid mobile number\"]");
            String errorMessage = "Please enter a valid mobile number";
            PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
        }
    }

    public static void clickConfirmAddess(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm address")).click();
    }

    public static void clickConfirmDetails(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();
    }

    private static boolean verifyMobile(String countryCode, String mobileNumber) {
        String regex = "";
        if (countryCode.equals("+852")) {
            regex = "^[2-9][0-9]{7}$";
        } else if (countryCode.equals("+853")) {
            regex = "^[6][0-9]{7}$";
        } else if (countryCode.equals("+86")) {
            regex = "^1[3-9][0-9]{10}$";
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }

}
