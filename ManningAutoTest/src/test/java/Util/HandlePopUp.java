package Util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandlePopUp {
    public static void closePromotionPopUp(Page page) {
        Locator closePromotionBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("X"));
        if (closePromotionBtn.isVisible()) {
            closePromotionBtn.click();
        }
    }

    public static void closeCookiesPopUp(Page page) {
        Locator closeCookiesBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
        if (closeCookiesBtn.isVisible()) {
            closeCookiesBtn.click();
        }

    }
}
