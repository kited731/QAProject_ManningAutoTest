import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class HandleSearchResult {
    public static void searchFor(Page page, String keywordSearched, String targetItemName) {
        System.out.println("in result");
        page.waitForTimeout(5000);
        PlaywrightAssertions.assertThat(page.locator("xpath=//div[contains(@class,\"searchPage-searchHeader-o8F\")]"))
                .containsText(keywordSearched);

        PlaywrightAssertions.assertThat(page.locator(
                "xpath=//div[contains(@class,\"breadcrumbs-root-MTL\")]/span[contains(@class,\"breadcrumbs-text\")][2]"))
                .hasText("Search");

        searchItemAllPages(page, targetItemName);
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

    public static void searchItemAllPages(Page page, String targetItemName) {
        // Find out how many total items
        String totalItemTitleText = page.locator("xpath=//div[@class=\"liveSearchPLP-totalPages-3Ew\"]").innerText();
        int totalItems = Integer.parseInt(totalItemTitleText.substring(0, totalItemTitleText.indexOf(" ")));
        System.out.println("There is a total of " + totalItems + " items.");

        // Find the pagination drop down
        Locator paginationOption = page.locator(
                "xpath=//div[contains(@class,\"pagination-perPageWrapper\")]/div[contains(@class,\"pagination-perPageWrapperInner\")]")
                .first();
        paginationOption.scrollIntoViewIfNeeded();

        if (totalItems >= 64) { // change pagination to 64 per page
            paginationOption.click();
            page.locator("xpath=//div/div[@role=\"listbox\"]/div[contains(text(),\"64 Per Page\")]").click();
            page.waitForTimeout(5000);
        } else if (totalItems > 12 && totalItems <= 36) { // change pagination to 36 per page
            paginationOption.click();
            page.locator("xpath=//div/div[@role=\"listbox\"]/div[contains(text(),\"36 Per Page\")]").click();
            page.waitForTimeout(5000);
        } // no need to change pagination for items count less than or equal to 12

        // Find the next page button
        Locator nextPageBtn = page.locator("xpath=//button[@aria-label=\"move to the next page\"]").first();

        List<Locator> searchResultItems = page.locator(
                "xpath=//div[contains(@class,\"liveSearchPLP-items-gFg\")]/div[contains(@class,\"product-list-item\")]/a[2]/div[1]")
                .all();

        boolean itemIsFound = false;

        do {
            // Scan through all the item name to see if it matches
            for (Locator item : searchResultItems) {
                if (item.textContent().equals(targetItemName)) {
                    itemIsFound = true;
                    item.scrollIntoViewIfNeeded();
                    page.waitForTimeout(3000);
                    item.click();
                    break;
                }
            }

            if (itemIsFound) {
                break;
            } else if (!nextPageBtn.isDisabled()) {
                nextPageBtn.click();
                page.waitForTimeout(5000);
                // Update item list from new page
                searchResultItems = page.locator(
                        "xpath=//div[contains(@class,\"liveSearchPLP-items-gFg\")]/div[contains(@class,\"product-list-item\")]/a[2]/div[1]")
                        .all();
            } else if (nextPageBtn.isDisabled() && !itemIsFound) {
                System.out.println("Item Not Found");
                break;
            }

        } while (!nextPageBtn.isDisabled() || !itemIsFound);

    }
}
