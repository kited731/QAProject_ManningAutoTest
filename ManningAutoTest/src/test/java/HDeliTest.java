
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Util.HandleHomeDeliveryJ;

public class HDeliTest {

    @Test
    public void start() {
        Playwright pw = Playwright.create();
        Browser broswer = pw.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("chromium").setSlowMo(2000));
        Page page = broswer.newPage();

        HandleHomeDeliveryJ.AddToCart(page);
        HandleHomeDeliveryJ.SelectHomeDelivery(page);
        HandleHomeDeliveryJ.CheckoutSecurely(page);
        HandleHomeDeliveryJ.GuestCheckout(page);
        HandleHomeDeliveryJ.HDeliInputInfo(page);
        page.pause();

        broswer.close();
    }
}
