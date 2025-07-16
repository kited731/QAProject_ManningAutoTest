import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class HandleCheckoutCollect {
    public static void selectCollect(Page page) {
        Locator homeDelieveryOption = page.locator(
                "xpath=//div[contains(@class,\"deliveryMethod-content\")]/div/div/div/span[text()=\"click and collect\"]");
        homeDelieveryOption.scrollIntoViewIfNeeded();
        homeDelieveryOption.click();
    }

    public static void verifyCollectFee(Page page) {
        Locator subtotalSpan = page.locator(
                "xpath=(//div[@class='summary-fee_wrapper-Bgk']//div)[2]");
        
        String freeCollectXpath = "xpath=//div[contains(@class,'deliveryMethod-content-i6x flex-col lg_flex-row')]/div[1]";
        String disabledCollectXpath = "xpath=//div[contains(@class,'deliveryMethod-item_disabled-fMj')]";

        // Xpath need to be updated
        // String deliveryFeeXpath =
        // "xpath=//div[@class=\"summary-fee_wrapper-Bgk\"]/div[text()=\"Delivery
        // fee\"]/following-sibling::div/span";

        String subTotalStr = subtotalSpan.innerText();
        double subTotalPrice = Double.parseDouble(subTotalStr.replace("$", "").replace(",", "").trim());

        System.out.println("Subtotal is: " + subTotalPrice);

        subtotalSpan.first().scrollIntoViewIfNeeded();

        if (subTotalPrice >= 50) {
            System.out.println("In >= 50");
            Locator freeCollectLabel = page.locator(freeCollectXpath);
            PlaywrightAssertions.assertThat(freeCollectLabel).not().containsText("disabled");

            // Locator deliveryFeeLabel = page.locator(deliveryFeeXpath);
            // PlaywrightAssertions.assertThat(deliveryFeeLabel).containsText("Free");
        } else {
            System.out.println("In < 50");
            Locator disabledCollectlabel = page.locator(disabledCollectXpath).nth(1);
            PlaywrightAssertions.assertThat(disabledCollectlabel).containsText("deliveryMethod-item_disabled-fMj");
        }

    }

    public static void checkoutSecurely(Page page) {
        page.waitForTimeout(3000);
        Locator checkOutSecurelyBtn = page
                .locator("xpath=//div/button[contains(@class,\"btnGroup-btn\")]/span[text()=\"Checkout securely\"]");
        checkOutSecurelyBtn.scrollIntoViewIfNeeded();
        checkOutSecurelyBtn.click();
    }

    public static void checkoutSubmit(Page page) {
        storeCollect(page);
        HandleCheckoutHomeDelivery.confirmDetail(page);
    }

    public static void storeCollect(Page page) {
        String location = "Lai Chi Kok";
        Locator locationInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter location"));
        locationInput.click();
        locationInput.fill(location);
        locationInput.press("Enter");
        page.waitForTimeout(2000);
        page.locator(".storeCollect-radioGroup-hz8 > .icon-root-W-v > svg").first().click();
        page.waitForTimeout(2000);
    }
}