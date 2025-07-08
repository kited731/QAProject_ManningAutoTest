import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class HandleProductDetailPage {
    public static void AddProductToCart(Page page, String targetProductName, int quantity) {
        PlaywrightAssertions.assertThat(page.locator("xpath=//div[contains(@class,\"productFullDetail-productName\")]"))
                .containsText(targetProductName);

        Locator addToCartBtn = page
                .locator("xpath=//section[contains(@class,\"productFullDetail-actions\")]/*/*/*/*/*/button");

        addToCartBtn.click();

        if (quantity > 1) {
            Locator quantityInput = page.locator("xpath=//input[contains(@aria-label,\"Item Quantity\")]");
            quantityInput.fill(String.valueOf(quantity));
            quantityInput.blur();
        }

        page.waitForTimeout(3000);
    }
}
