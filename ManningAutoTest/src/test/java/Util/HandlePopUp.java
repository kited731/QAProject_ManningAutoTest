package Util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandlePopUp {
    public static void closePromotionPopUp(Page page) {
        System.out.println("In Close Promotion Pop Up");
        Locator closePromotionBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("X"));
        if (closePromotionBtn.isVisible()) {
            closePromotionBtn.click();
        }
    }

    public static void closeCookiesPopUp(Page page) {
        System.out.println("In Close Cookies Pop Up");
        Locator closeCookiesBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I understand"));
        if (closeCookiesBtn.isVisible()) {
            closeCookiesBtn.click();
        }

    }
}
