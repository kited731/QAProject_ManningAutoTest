import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class checkoutMemberTest {
    @Test
    public void checkoutTest() {

        // Initialize Search keywords
        String targetProduct = "Maro Volume Up Shampoo (Non-Silicone) 460ml";
        String targetProductMainCategory = "Hair";
        String targetProductSubCategory = "Shampoo";
        String targetProductBrand = "Maro";
        int purchaseQuantity = 2;

        // Settings
        int clickInteval = 1000;
        int waitForTimeout = 5000;

        // Initialize Playwright
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--start-maximized"))
                        .setSlowMo(clickInteval));
        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null));

        Page page = context.newPage();

        // Add try-catch-finally for playwright
        try {

            page.navigate("https://www.mannings.com.hk/en");
            page.waitForTimeout(waitForTimeout);
            HandlePopUp.closeCookiesPopUp(page);
            HandlePopUp.closePromotionPopUp(page);

            String mainCategoryXpath = "xpath=//div[contains(@class,\"megaMenuItem-megaMenuItem\")]/a/span[text()=\""
                    + targetProductMainCategory + "\"]";
            System.out.println(mainCategoryXpath);
            Locator mainCategoryMenuBtn = page
                    .locator(mainCategoryXpath)
                    .first();
            mainCategoryMenuBtn.hover();

            String subCategoryXpath = "xpath=//div/div/a[text()=\"" + targetProductSubCategory + "\"]";
            Locator subCategoryMenuBtn = page.locator(subCategoryXpath);
            subCategoryMenuBtn.hover();
            subCategoryMenuBtn.click();

            HandleSearchResult.filterBrand(page, targetProductBrand, true);
            page.waitForTimeout(waitForTimeout);
            HandleSearchResult.addItem(page, targetProduct);
            HandleProductDetailPage.AddProductToCart(page, targetProduct, purchaseQuantity);
            HandleShoppingCart.goToShoppingCart(page);
        } finally {
            page.close();
            browser.close();
            playwright.close();
        }
    }
}
