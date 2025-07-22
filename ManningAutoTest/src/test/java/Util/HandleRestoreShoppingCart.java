package Util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandleRestoreShoppingCart {
    public static void restoreShoppingCart(Page page) {
        System.out.println("In Restore Shopping Cart");

        // Default to restore the shopping cart
        // Set restore to false if you do not want to restore the shopping cart
        boolean restore = true;

        if (restore) {
            Locator restoreYesBtn = page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Yes"));
            if (restoreYesBtn.isVisible()) {
                restoreYesBtn.click();
            }
        } else {
            Locator restoreNoBtn = page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("No").setExact(true));
            if (restoreNoBtn.isVisible()) {
                restoreNoBtn.click();

            }
        }
    }
}
