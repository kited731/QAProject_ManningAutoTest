import com.github.javafaker.Faker;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class HandleCheckoutHomeDelivery {
    public static void selectDelivery(Page page) {
        Locator homeDelieveryOption = page.locator(
                "xpath=//div[contains(@class,\"deliveryMethod-content\")]/div/div/div/span[text()=\"home delivery\"]");
        homeDelieveryOption.scrollIntoViewIfNeeded();
        homeDelieveryOption.click();
    }

    public static void increaseQty(Page page, int amount) {
        Locator increaseQtyBtn = page.locator("xpath=//button[@aria-label=\"Increase Quantity\"]");
        increaseQtyBtn.scrollIntoViewIfNeeded();
        for (int i = 0; i < amount; i++) {
            increaseQtyBtn.click();
            page.waitForTimeout(3000);
        }
    }

    public static void decreaseQty(Page page, int amount) {
        Locator decreaseQtyBtn = page.locator("xpath=//button[@aria-label=\"Decrease Quantity\"]");
        int currentQty = Integer.valueOf(
                page.locator("xpath=//input[@aria-label=\"Item Quantity\"]").getAttribute("value"));
        decreaseQtyBtn.scrollIntoViewIfNeeded();
        for (int i = 0; i < amount; i++) {
            if (currentQty >= 1) {
                decreaseQtyBtn.click();
                currentQty--;
                page.waitForTimeout(1000);
            } else {
                break;
            }
        }
    }

    public static void updateToQty(Page page, int amount) {
        Locator qtyInput = page.locator("xpath=//input[@aria-label=\"Item Quantity\"]");
        qtyInput.scrollIntoViewIfNeeded();
        qtyInput.fill(String.valueOf(amount));
        qtyInput.blur();
    }

    public static void applyCouponCode(Page page, String couponCode) {
        Locator couponCodeInput = page.locator("#couponCode");
        Locator applyCouponCodeBtn = page.locator("xpath=//button[@class=\"couponCode-apply_btn-hes\"]");

        couponCodeInput.scrollIntoViewIfNeeded();
        couponCodeInput.fill(couponCode);
        page.waitForTimeout(5000);
        applyCouponCodeBtn.click();
    }

    public static void verifyDeliveryFee(Page page) {
        Locator subtotalSpan = page.locator(
                // "xpath=//div[@class=\"summary-fee_wrapper-Bgk\"]/div[text()=\"Subtotal\"]/following-sibling::div/span");
                "xpath=(//div[@class='summary-fee_wrapper-Bgk']//div)[2]");

        String freeShipingXpath = "xpath=//div[@class=\"summary-fee_wrapper-Bgk\"]/div[text()=\"Delivery fee\"]/following-sibling::div";
        String nonFreeShippingXpath = "xpath=//div[@class=\"summary-fee_wrapper-Bgk\"]/div[text()=\"Delivery fee\"]/following-sibling::div/span";
        
        String subTotalStr = subtotalSpan.innerText();
        double subTotalPrice = Double.parseDouble(subTotalStr.replace("$", "").replace(",", "").trim());

        System.out.println("Subtotal is: " + subTotalPrice);

        subtotalSpan.first().scrollIntoViewIfNeeded();

        if (subTotalPrice >= 399) {
            System.out.println("In >= 399");
            Locator freeShippingLabel = page.locator(freeShipingXpath);
            PlaywrightAssertions.assertThat(freeShippingLabel).containsText("Free");
        } else {
            System.out.println("In < 399");
            Locator nonFreeShippinglabel = page.locator(nonFreeShippingXpath).nth(1);
            PlaywrightAssertions.assertThat(nonFreeShippinglabel).containsText("45");
        }

    }

    public static void checkout(Page page) {
        page.waitForTimeout(3000);
        Locator checkOutSecurelyBtn = page
                .locator("xpath=//div/button[contains(@class,\"btnGroup-btn\")]/span[text()=\"Checkout securely\"]");
        checkOutSecurelyBtn.scrollIntoViewIfNeeded();
        checkOutSecurelyBtn.click();

        page.waitForTimeout(5000);
        Locator guestCheckoutBtn = page
                .locator("//button[contains(@class,\"signIn-guestButton\")]");
        guestCheckoutBtn.scrollIntoViewIfNeeded();
        guestCheckoutBtn.click();
        page.waitForTimeout(3000);
    }

    public static void completeDeliveryDetail(Page page) {
        fillAddressForm(page);
        confirmAddress(page);
        confirmDetail(page);
    }

    public static void changeDeliveryDetail(Page page) {
        Locator changeDeliveryBtn = page
                .locator("xpath=//div[@class=\"deliveryDetail-headTitle-X-I\"]/div");
        changeDeliveryBtn.scrollIntoViewIfNeeded();
        changeDeliveryBtn.click();

        Locator shipToDiffAddressBtn = page.locator("xpath=//span[text()=\"Ship to different address\"]");
        shipToDiffAddressBtn.scrollIntoViewIfNeeded();
        shipToDiffAddressBtn.click();

        fillAddressForm(page);
        confirmAddress(page);
        confirmDetail(page);
    }

    public static void fillAddressForm(Page page) {
        Faker faker = new Faker();
        String email = faker.name().firstName() + "_" + faker.name().lastName() + "@gmail.com";
        String fullName = faker.name().firstName() + " " + faker.name().lastName();
        // int regionCode = faker.number().numberBetween(0, 2);
        int regionCode = 1;
        String region = "Kowloon";
        String district = "Cheung Sha Wan";
        String streetNumber = faker.address().streetAddress();
        String buildingName = faker.address().buildingNumber();
        String floor = faker.number().randomDigitNotZero() + "/F";
        String countryCode = "+852"; // +852 or +853 or +86
        String mobile = "61235123";

        switch (regionCode) {
            case 0:
                region = "Hong Kong Island";
                break;
            case 1:
                region = "Kowloon";
                break;
            case 2:
                region = "New Territories";
                break;
        }

        // Email
        Locator emailInput = page
                .locator("xpath=//form[contains(@class,\"guestForm\")]/div/div/span/span/input[@id=\"email\"]");
        emailInput.scrollIntoViewIfNeeded();
        emailInput.fill(email);
        // Full Name
        Locator fullNameInput = page.locator("#firstname");
        fullNameInput.scrollIntoViewIfNeeded();
        fullNameInput.fill(fullName);
        // Region Dropdown
        Locator regionInput = page.locator("#region-root-aGR");
        regionInput.scrollIntoViewIfNeeded();
        regionInput.click();
        Locator regionInputOption = page
                .locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + region + "\"]");
        regionInputOption.scrollIntoViewIfNeeded();
        regionInputOption.click();
        // District
        Locator districtInput = page.locator("#region-root-k7a");
        districtInput.scrollIntoViewIfNeeded();
        districtInput.click();
        Locator districtInputOption = page
                .locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + district + "\"]");
        districtInputOption.scrollIntoViewIfNeeded();
        districtInputOption.click();
        // Street Number & Name
        Locator streetInput = page.locator("#street0");
        streetInput.scrollIntoViewIfNeeded();
        streetInput.fill(streetNumber);
        // Building & Estate
        Locator buildingInput = page.locator("#street1");
        buildingInput.scrollIntoViewIfNeeded();
        buildingInput.fill(buildingName);
        // Unit & Floor
        Locator floorInput = page.locator("#street2");
        floorInput.scrollIntoViewIfNeeded();
        floorInput.fill(floor);
        // Country Code
        Locator countryCodeInput = page.locator("#areaCode");
        countryCodeInput.scrollIntoViewIfNeeded();
        countryCodeInput.click();
        page.locator("xpath=//div[contains(@class,\"react-select__option\")][text()=\"" + countryCode + "\"]").click();

        // Mobile Number
        Locator phoneInput = page.locator("#telephone");
        phoneInput.fill(mobile);
    }

    public static void confirmAddress(Page page) {
        // Confirm address button
        Locator confirmAddressBtn = page.locator("xpath=//button[@type=\"submit\"]/span[text()=\"Confirm address\"]");
        confirmAddressBtn.scrollIntoViewIfNeeded();
        confirmAddressBtn.click();
        page.waitForTimeout(5000);
    }

    public static void confirmDetail(Page page) {
        // Confirm Details button
        Locator confirmDetailBtn = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Confirm Details"));
        confirmDetailBtn.scrollIntoViewIfNeeded();
        confirmDetailBtn.click();
    }
}
