package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class HandleClickAndCollect {
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

    public static void clickCheckoutSecurely(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout securely")).click();
    }

    public static void increaseQty(Page page, int targetQty) {
        Locator increaseQtyButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Increase Quantity"));
        Locator qtyInput = page.locator("xpath=//input[@aria-label=\"Item Quantity\"]");
        int currentQty;

        System.out.println(qtyInput);

        currentQty = Integer.parseInt(qtyInput.inputValue());

        System.out.println(currentQty);

        if (targetQty > currentQty) {
            do {
                increaseQtyButton.click();
                page.waitForTimeout(1000);
                currentQty = Integer.parseInt(qtyInput.textContent());
            } while (targetQty != currentQty);
        }
    }

    public static void decreaseQty(Page page, int targetQty) {
        Locator decreaseQtyButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Decrease Quantity"));
        Locator qtyInput = page.locator("xpath=//input[@aria-label=\"Item Quantity\"]");
        int currentQty;

        System.out.println(qtyInput);

        currentQty = Integer.parseInt(qtyInput.inputValue());

        System.out.println(currentQty);

        if (targetQty < currentQty) {
            do {
                decreaseQtyButton.click();
                page.waitForTimeout(1000);
                currentQty = Integer.parseInt(qtyInput.textContent());
            } while (targetQty != currentQty);
        }
    }

    public static void guestCheckout(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest checkout")).click();
    }

    public static void inputFullName(Page page, String fullName) {
        Locator nameInput = page.locator("xpath=//input[@name=\"firstName\"]");
        nameInput.scrollIntoViewIfNeeded();
        nameInput.fill(fullName);
        nameInput.blur();

        if (fullName.isEmpty()) {
            Locator errorMessageLocator = page
                    .locator("xpath=//p[contains(@class,\"message-root_error-UMR\") and text()=\"Is required.\"]");
            String errorMessage = "Is required.";
            PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
        }
    }

    public static void inputEmailAddress(Page page, String emailAddress) {
        String emailRegex = "[\\w-._+]+@[\\w-._+]+.\\w{2,4}";
        Locator emailInput = page.locator("xpath=//input[@name=\"email\"]").first();
        emailInput.scrollIntoViewIfNeeded();
        emailInput.fill(emailAddress);
        emailInput.blur();

        if (!regexChecking(emailRegex, emailAddress, true)) {
            page.waitForTimeout(500);
            Locator errorMessageLocator = page.locator(
                    "//p[contains(@class,\"message-root_error-UMR\") and text()=\"Please enter a valid email address\"]");
            String errorMessage = "Please enter a valid email address";
            PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
        }
    }

    public static void selectCountryCode(Page page, String countryCode, String phonenNumber) {
        if (countryCode.equals("+852")) { // Hong Kong number
            String hkPhoneRegex = "^[2-9][0-9]{7}$"; // 8 digits, start with 2 to 9
            Locator countryCodeSelector = page.locator("#areaCode");
            countryCodeSelector.scrollIntoViewIfNeeded();
            countryCodeSelector.click();

            Locator optionLocator = page.locator("xpath=//div[@role=\"option\"][text()=\"+852\"]");
            optionLocator.click();

            if (!regexChecking(hkPhoneRegex, phonenNumber, true)) {
                page.waitForTimeout(500);
                Locator errorMessageLocator = page.locator(
                        "//p[contains(@class,\"message-root_error-UMR\") and text()=\"Please enter a valid mobile number\"]");
                String errorMessage = "Please enter a valid mobile number";
                PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
            }
        } else if (countryCode.equals("+853")) { // Macau number
            String mcPhoneRegex = "^[6][0-9]{7}$"; // 8 digits, start with 6
            Locator countryCodeSelector = page.locator("#areaCode");
            countryCodeSelector.scrollIntoViewIfNeeded();
            countryCodeSelector.click();

            Locator optionLocator = page.locator("xpath=//div[@role=\"option\"][text()=\"+853\"]");
            optionLocator.click();

            if (!regexChecking(mcPhoneRegex, phonenNumber, true)) {
                page.waitForTimeout(500);
                Locator errorMessageLocator = page.locator(
                        "//p[contains(@class,\"message-root_error-UMR\") and text()=\"Please enter a valid mobile number\"]");
                String errorMessage = "Please enter a valid mobile number";
                PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
            }

        } else if (countryCode.equals("+86")) { // China number
            String cnPhoneRegex = "^1[3-9][0-9]{10}$"; // 11 digits, start with 13X to 19X
            Locator countryCodeSelector = page.locator("#areaCode");
            countryCodeSelector.scrollIntoViewIfNeeded();
            countryCodeSelector.click();

            Locator optionLocator = page.locator("xpath=//div[@role=\"option\"][text()=\"+86\"]");
            optionLocator.click();

            if (!regexChecking(cnPhoneRegex, phonenNumber, true)) {
                page.waitForTimeout(500);
                Locator errorMessageLocator = page.locator(
                        "//p[contains(@class,\"message-root_error-UMR\") and text()=\"Please enter a valid mobile number\"]");
                String errorMessage = "Please enter a valid mobile number";
                PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
            }

        } else { // fallback failsafe
            String hkPhoneRegex = "^[2-9][0-9]{7}$"; // 8 digits, start with 2 to 9
            Locator countryCodeSelector = page.locator("#areaCode");
            countryCodeSelector.scrollIntoViewIfNeeded();
            countryCodeSelector.click();

            Locator optionLocator = page.locator("xpath=//div[@role=\"option\"][text()=\"+852\"]");
            optionLocator.click();

            if (!regexChecking(hkPhoneRegex, phonenNumber, true)) {
                page.waitForTimeout(500);
                Locator errorMessageLocator = page.locator(
                        "//p[contains(@class,\"message-root_error-UMR\") and text()=\"Please enter a valid mobile number\"]");
                String errorMessage = "Please enter a valid mobile number";
                PlaywrightAssertions.assertThat(errorMessageLocator).hasText(errorMessage);
            }
        }
    }

    public static void clickConfirmDetails(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Details")).click();
    }

    private static boolean regexChecking(String regex, String textToVerify, boolean exactMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textToVerify);

        if (exactMatch) {
            return matcher.matches();
        } else {
            return matcher.find(); // Return true if the regex is found
        }
    }

    public static void searchStore(Page page, String searchKeyword, int selectOption) {
        Locator searchInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location"));
        searchInput.scrollIntoViewIfNeeded();
        searchInput.click();
        searchInput.fill(searchKeyword);

        Locator searchResult = page.locator(
                "xpath=//div[@class=\"storeCollect-store_list_content-him\"]/div[contains(@class,\"storeCollect-storeContainer-tXX\")]");

        if (searchResult.all().size() >= 1) {
            if (searchResult.all().size() >= selectOption) {
                Locator target = searchResult.nth(selectOption);
                target.scrollIntoViewIfNeeded();
                target.click();
            } else {
                Locator target = searchResult.first();
                target.scrollIntoViewIfNeeded();
                target.click();
            }
        }
    }

    // @Test
    // public void testManningsThroatWesternPage() { // <-- Make method public
    // Browser browser = Playwright.create().chromium().launch(new
    // BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
    // Page page = browser.newPage();

    // Faker faker = new Faker();
    // String email = faker.name().firstName() + "_" + faker.name().lastName() +
    // "@gmail.com";
    // String fullName = faker.name().firstName() + " " + faker.name().lastName();
    // int regionCode = faker.number().numberBetween(0, 2);
    // String region = "Kowloon";
    // String district = faker.address().city();
    // String streetNumber = faker.address().streetAddress();
    // String buildingName = faker.address().buildingNumber();
    // String floor = faker.number().randomDigitNotZero() + "/F";
    // String countryCode = "+852";
    // String mobile = "61236123";

    // System.out.println("email is : " + email);
    // System.out.println("Full Name is : " + fullName);
    // System.out.println("Region is : " + region);
    // System.out.println("District is : " + district);
    // System.out.println("Street Number is : " + streetNumber);
    // System.out.println("Building is : " + buildingName);
    // System.out.println("Floor is : " + floor);
    // System.out.println("Country Code is : " + countryCode);
    // System.out.println("Mobile is : " + mobile);

    // try (Playwright playwright = Playwright.create()) {
    // page.navigate("https://www.mannings.com.hk/en/strepsils-sugarfree-lemon-lozenge-16pcs/p/492595");
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I
    // understand")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to
    // cart")).nth(0).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
    // Quantity")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle
    // mini cart. You have 2")).click();
    // page.locator(".deliveryMethod-item_content--Fx > div:nth-child(3) >
    // .icon-root-W-v > svg").first().click(); //select Click and collect
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase
    // Quantity")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout
    // securely")).click();
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest
    // checkout")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location")).fill("Lai Chi Kok");
    // page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v >
    // svg").first().click();
    // page.locator("input[name=\"firstName\"]").click();
    // page.locator("input[name=\"firstName\"]").fill(fullName);
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry")).locator("input[name=\"email\"]").click();
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry")).locator("input[name=\"email\"]").fill(email);
    // page.locator("#areaCode div").nth(4).click();
    // page.getByRole(AriaRole.OPTION, new
    // Page.GetByRoleOptions().setName("+852")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).fill(mobile);
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm
    // Details")).click();

    // browser.close();
    // }
    // }

    // public static void CNCInputCheckOutInfo(Page page, String email, String
    // fullname, String mobile) {
    // Faker faker = new Faker();
    // String fullName = faker.name().firstName() + " " +
    // faker.name().lastName();
    // int regionCode = faker.number().numberBetween(0, 2);
    // String region = "Kowloon";
    // String district = faker.address().city();
    // String streetNumber = faker.address().streetAddress();
    // String buildingName = faker.address().buildingNumber();
    // String floor = faker.number().randomDigitNotZero() + "/F";
    // String countryCode = "+852";

    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter
    // location")).fill("Lai Chi Kok");
    // page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v >
    // svg").first().click();
    // page.locator("input[name=\"firstName\"]").click();
    // page.locator("input[name=\"firstName\"]").fill(fullname);
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry"))
    // .locator("input[name=\"email\"]").click();
    // page.locator("form").filter(new Locator.FilterOptions().setHasText("Full
    // NameEmailCountry"))
    // .locator("input[name=\"email\"]").fill(email);
    // page.locator("#areaCode div").nth(4).click();
    // page.getByRole(AriaRole.OPTION, new
    // Page.GetByRoleOptions().setName("+852")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).click();
    // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile
    // number")).fill(mobile);
    // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm
    // Details")).click();
    // }
}
