package Util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandleCaptcha {
    public static void clickCaptchaTextbox(Page page) {
        System.out.println("In Enter Captcha");

        Locator captcha = page.locator("iframe[title=\"mtcaptcha\"]").contentFrame().getByRole(AriaRole.TEXTBOX,
                new FrameLocator.GetByRoleOptions().setName("enter text from captcha to"));

        if (captcha.isVisible()) {
            captcha.click();
            // Pause for the captcha input
            // page.pause();
            // Change to wait for 15s for recording purposes
            page.waitForTimeout(15000);
        }

    }
}
