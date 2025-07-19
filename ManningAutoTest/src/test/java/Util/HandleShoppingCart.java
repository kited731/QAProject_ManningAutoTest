package Util;

import com.microsoft.playwright.Page;

public class HandleShoppingCart {
    public static void goToShoppingCart(Page page) {
        page.locator(
                "xpath=//div[contains(@class,\"header-rightIconsWrap\")]/div[contains(@class,\"cartTrigger-triggerContainer\")]/button[contains(@class,\"cartTrigger-trigger\")]")
                .click();
    }
}
