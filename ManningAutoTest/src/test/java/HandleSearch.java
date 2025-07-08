
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HandleSearch {
    public static void search(Page page, String keywords, int searchMode) {
        System.out.println("Second Test");
        Locator searchBar = page.getByPlaceholder("Search for a product or brand");
        searchBar.click();
        searchBar.fill(keywords);

        switch (searchMode) {
            case 0:
                enterMode(page);
                break;
            case 1:
                viewAllMode(page);
                break;
            case 2:
                categoryMode(page);
                break;
            default:
                enterMode(page);
                break;
        }
    }

    // mode = 0 [Press Enter]
    public static void enterMode(Page page) {
        page.keyboard().press("Enter");
    }

    // mode = 1 [Click View all]
    public static void viewAllMode(Page page) {
        Locator autoComplete = page.locator(".autocomplete-root_visible-DfX");
        autoComplete.getByText("View all").scrollIntoViewIfNeeded();
        autoComplete.getByText("View all").click();
    }

    // mode = 2 [Click the first category]
    public static void categoryMode(Page page) {
        Locator searchResults = page.locator("xpath=//div[contains(@class,\"popover-CategoryItem--MT\")][1]/a");
        searchResults.click();
    }
}
