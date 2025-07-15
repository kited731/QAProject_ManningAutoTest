import java.util.Arrays;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CheckoutMemberCollectTest {
    @Test
    public void checkoutTest() {

        // Initialize Search keywords
        String targetProduct = "Maro Volume Up Shampoo (Non-Silicone) 460ml";
        // String targetProductMainCategory = "Hair";
        // String targetProductSubCategory = "Shampoo";
        // String targetProductBrand = "Maro";
        int purchaseQuantity = 2;

        // Settings
        int clickInteval = 1000;
        int waitForTimeout = 5000;

        // Use a persistent context by specifying a user data directory
        String userDataDir = "./user-data/chromium";
        Path path = FileSystems.getDefault().getPath(userDataDir);

        // Initialize Playwright
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();

        BrowserContext bc = browserType.launchPersistentContext(
                path,
                new BrowserType.LaunchPersistentContextOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--start-maximized"))
                        .setSlowMo(clickInteval)
                        .setViewportSize(null));

        Page page = bc.newPage();

        // Add try-catch-finally for playwright
        try {

            page.navigate("https://www.mannings.com.hk/en/maro-volume-up-shampoo-non-silicone-460ml/p/430728");
            page.waitForTimeout(waitForTimeout);
            HandlePopUp.closeCookiesPopUp(page);
            HandlePopUp.closePromotionPopUp(page);

            // Add product to cart in previous page
            // Commented out as it is not used in this test
            // HandleProductDetailPage.AddProductToCart(page, targetProduct,
            // purchaseQuantity);
            HandleShoppingCart.goToShoppingCart(page);

            HandleCheckoutCollect.selectCollect(page);
            // HandleCheckoutHomeDelivery.increaseQty(page, 1);
            HandleCheckoutHomeDelivery.updateToQty(page, 10);
            // HandleCheckoutHomeDelivery.applyCouponCode(page, "25SHOP40");

            page.waitForTimeout(waitForTimeout);
            HandleCheckoutCollect.verifyCollectFee(page);
            HandleCheckoutCollect.checkoutSecurely(page);
            HandleCheckoutCollect.checkoutSubmit(page);

            page.waitForTimeout(waitForTimeout);
        } finally {
            page.close();
            bc.close();
            playwright.close();
        }
    }
}
