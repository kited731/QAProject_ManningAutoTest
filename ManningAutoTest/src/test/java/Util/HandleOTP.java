package Util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandleOTP {
    public static void clickOTPtextbox(Page page) {
        System.out.println("In Enter OTP");

        Locator otp = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("OTP Required"));

        // OTP no need if, since must input
        otp.click();
        // Pause for the OTP input
        // page.pause();
        // Change to wait for 30s for recording purposes
        page.waitForTimeout(30000);

        // Move this down to ensure we must click the button
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify Account")).click();
    }
}
