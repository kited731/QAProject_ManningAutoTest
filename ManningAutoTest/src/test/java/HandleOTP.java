import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class HandleOTP {
    public static void clickOTPtextbox(Page page) {
        System.out.println("In Enter OTP");

        Locator otp = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("OTP Required"));

        if (otp.isVisible()) {
            otp.click();
            // Pause for the OTP input
            page.pause();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify Account")).click();
        }
    }
}
