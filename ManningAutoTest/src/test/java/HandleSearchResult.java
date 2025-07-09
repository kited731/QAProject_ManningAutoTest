import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class HandleSearchResult {
    public static void searchFor(Page page, String keywordSearched, String targetItemName) {
        System.out.println("in result");
        page.waitForTimeout(5000);
        PlaywrightAssertions.assertThat(page.locator("xpath=//div[contains(@class,\"searchPage-searchHeader-o8F\")]"))
                .containsText(keywordSearched);

        // System.out.println(page.locator("xpath=//div[contains(@class,\"searchPage-searchHeader-o8F\")]").textContent());

        PlaywrightAssertions.assertThat(page.locator(
                "xpath=//div[contains(@class,\"breadcrumbs-root-MTL\")]/span[contains(@class,\"breadcrumbs-text\")][2]"))
                .hasText("Search");

        // Find all the search result of the page
        Locator searchResultItems = page
                .locator("xpath=//div[contains(@class,\"item-root\")]/a[2]/div[contains(@class,\"item-name\")]");

        Locator targetItem = null;

        for (int i = 0; i < searchResultItems.count(); i++) {
            // System.out.println(searchResultItems.nth(i).textContent());
            if (searchResultItems.nth(i).textContent().equals(targetItemName)) {
                targetItem = searchResultItems.nth(i);
                break;
            }
        }

        if (targetItem != null) {
            targetItem.scrollIntoViewIfNeeded();
            targetItem.hover();
            targetItem.click();
            page.waitForTimeout(3000);
        }
    }

    public static void filterCategories(Page page, String CategoryName, Boolean bySearch) {
        Locator categoryFilter = page.locator("xpath=//fieldset/div/div/button[contains(text(),\"Show all\")]").first();
        categoryFilter.scrollIntoViewIfNeeded();
        categoryFilter.click();

        String targetXpath = "xpath=//label[text()=\"" + CategoryName + "\"]";
        Locator filterOption = page.locator(targetXpath);
        filterOption.scrollIntoViewIfNeeded();
        filterOption.click();
    }

    public static void filterBrand(Page page, String BrandName, Boolean bySearch) {
        if (bySearch) {
            Locator brandFilterSearch = page.locator("#searchInput").last();
            brandFilterSearch.scrollIntoViewIfNeeded();
            brandFilterSearch.fill(BrandName);
            brandFilterSearch.blur();
        } else {
            Locator brandFilter = page.locator("xpath=//fieldset/div/div/button[contains(text(),\"Show all\")]").last();
            brandFilter.scrollIntoViewIfNeeded();
            brandFilter.click();
        }

        String targetXpath = "xpath=//label[text()=\"" + BrandName + "\"]";
        Locator filterOption = page.locator(targetXpath);
        filterOption.scrollIntoViewIfNeeded();
        filterOption.click();
    }

    public static void addItem(Page page, String targetItemName) {
        Locator filterResultItems = page
                .locator(
                        "xpath=//div[contains(@class,\"liveSearchPLP-items-gFg\")]/div[contains(@class,\"item-root-Chs\")]/a[2]/div[contains(@class,\"item-name-LPg\")]");

        Locator targetItem = null;

        for (int i = 0; i < filterResultItems.count(); i++) {
            if (filterResultItems.nth(i).textContent().equals(targetItemName)) {
                System.out.println(filterResultItems.nth(i).textContent());
                targetItem = filterResultItems.nth(i);
                break;
            }
        }

        if (targetItem != null) {
            targetItem.scrollIntoViewIfNeeded();
            targetItem.hover();
            targetItem.click();
            page.waitForTimeout(3000);
        }
    }
}
