import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
//Try Push
public class MainTest {
    @Test
    public void Main() {
        System.out.println("In Main");

        // Initialize Search keywords
        String searchKeyword = "Lozenge";
        String targetProduct = "Strepsils Sugarfree Lemon Lozenge 16pcs";
        String targetProductCategory = "Throat";
        String targetProductBrand = "Strepsils";
        int purchaseQuantity = 2;

        // Settings
        int clickInteval = 1000;
        int waitForTimeout = 5000;

        // 0 (Search and enter)
        // 1 (Search and click view all)
        // 2 (Search and click category)
        int searchMode = 2;

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
            if (searchMode == 0) {
                HandleSearch.search(page, searchKeyword, searchMode);
                HandleSearchResult.searchFor(page, searchKeyword, targetProduct);
            } else if (searchMode == 1) {
                HandleSearch.search(page, searchKeyword, searchMode);
                HandleSearchResult.searchFor(page, searchKeyword, targetProduct);
            } else {
                HandleSearch.search(page, searchKeyword, searchMode);
                HandleSearchResult.filterCategories(page, targetProductCategory, false);
                HandleSearchResult.filterBrand(page, targetProductBrand, false);
                HandleSearchResult.addItem(page, targetProduct);
            }

            HandleProductDetailPage.AddProductToCart(page, targetProduct, purchaseQuantity);
            HandleShoppingCart.goToShoppingCart(page);

        } // catch (Exception e) {
          // System.out.println(e.getMessage());
          // }
        finally {
            page.close();
            browser.close();
            playwright.close();
        }

    }

}
