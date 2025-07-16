
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HDeliTest {

    @Test
    public void start() {
        Playwright pw = Playwright.create();
        Browser broswer = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("chromium").setSlowMo(2000));
        Page page = broswer.newPage();

        HomeDeliveryTest.AddToCart(page);
        HomeDeliveryTest.SelectHomeDelivery(page);
        HomeDeliveryTest.CheckoutSecurely(page);
        HomeDeliveryTest.GuestCheckout(page);
        HomeDeliveryTest.HDeliInputInfo(page);
        page.pause();
        
        broswer.close();
    }
}
