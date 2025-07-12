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
        page.waitForTimeout(5000);

        Locator categoryFilterSearch = page
                .locator("xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Categories\"]]/div/input");
        Locator categoryFilterShowAllBtn = page
                .locator(
                        "xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Categories\"]]/div[@class=\"space-y-2\"]/div/button");
        Locator categoryFilterCheckList = page.locator(
                "xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Categories\"]]/div/div/div/label[contains(text(),\""
                        + CategoryName + "\")]");

        // If filter by search, check if search field exist
        if (bySearch && categoryFilterSearch.isVisible()) {
            categoryFilterSearch.scrollIntoViewIfNeeded();
            categoryFilterSearch.fill(CategoryName);
            categoryFilterSearch.blur();
        } else if (categoryFilterShowAllBtn.isVisible()) { // Not by search or search not exist, find show all button
            categoryFilterShowAllBtn.scrollIntoViewIfNeeded();
            categoryFilterShowAllBtn.click();
        }

        // After filter is searched or Show All button is click, find the target
        // category checkbox to check
        if (categoryFilterCheckList.isVisible()) {
            categoryFilterCheckList.scrollIntoViewIfNeeded();
            categoryFilterCheckList.click();
        }
    }

    public static void filterBrand(Page page, String BrandName, Boolean bySearch) {
        page.waitForTimeout(5000);
        Locator brandFilterSearch = page
                .locator("xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Brand\"]]/div/input");
        Locator brandFilterShowAllBtn = page
                .locator(
                        "xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Brand\"]]/div[@class=\"space-y-2\"]/div/button");
        Locator brandFilterCheckList = page.locator(
                "xpath=//form/div/fieldset[preceding-sibling::label[text()=\"Brand\"]]/div/div/div/label[contains(text(),\""
                        + BrandName + "\")]");

        // If filter by search, check if search field exist
        if (bySearch && brandFilterSearch.isVisible()) {
            brandFilterSearch.scrollIntoViewIfNeeded();
            brandFilterSearch.fill(BrandName);
            brandFilterSearch.blur();
        } else if (brandFilterShowAllBtn.isVisible()) { // Not by search or search not exist, find show all button
            brandFilterShowAllBtn.scrollIntoViewIfNeeded();
            brandFilterShowAllBtn.click();
        }

        // After filter is searched or Show All button is click, find the target
        // category checkbox to check
        if (brandFilterCheckList.isVisible()) {
            brandFilterCheckList.scrollIntoViewIfNeeded();
            brandFilterCheckList.click();
        }
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
