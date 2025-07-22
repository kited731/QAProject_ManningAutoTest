package Workflow;

import Util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.awt.Toolkit;
import java.awt.Desktop;
import java.awt.Dimension;

import TestingData.PurchaseTestCase;

public class ManningGuestCheckoutTest {
        // Playwright
        private Playwright playwright;
        private Browser browser;
        private Page page;
        private BrowserContext context;
        // Test Config
        private int clickInteval = 500;
        private int waitForTimeout = 5000;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Json Test Cases
        private JsonDataReader jsonReader = new JsonDataReader();
        // Extent Report Config
        ExtentReports extent = new ExtentReports();
        ExtentTest purchaseTest;
        ExtentTest deliveryTest;
        String extentReportPath;
        // Timestamp related setup
        LocalDateTime now;
        DateTimeFormatter formatter;
        String timestamp;

        // Enum
        enum SearchStatus {
                Pending,
                Started,
                Completed
        }

        enum DeliveryPaymentStatus {
                Pending,
                Started,
                Completed
        }

        @BeforeEach
        void Initialize() {
                // Playwright Config
                playwright = Playwright.create();
                browser = playwright.chromium().launch(
                                new LaunchOptions()
                                                .setHeadless(false)
                                                .setArgs(Arrays.asList("--start-maximized"))
                                                .setSlowMo(clickInteval));
                context = browser.newContext(
                                new Browser.NewContextOptions()
                                                .setViewportSize(null)
                                                .setRecordVideoSize((int) dimension.getWidth(),
                                                                (int) dimension.getHeight())
                                                .setRecordVideoDir(Paths.get("recording/Guest_Checkout_Test")));
                page = context.newPage();
                // Datetime format for path creation
                now = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                timestamp = now.format(formatter);
                // Extent Report Config
                String reportName = "Guest_Checkout_Test";
                extentReportPath = System.getProperty("user.dir") + "/reports/" + reportName + "/" + timestamp
                                + ".html";
                ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
                spark.config().setTheme(Theme.DARK);
                spark.config().setDocumentTitle("Automation Test Report");
                spark.config().setReportName(reportName);
                extent.attachReporter(spark);
        }

        @Test
        void startCheckoutGuestTest() throws IOException {
                List<PurchaseTestCase> testCases = jsonReader.readPurchaseTestData("CheckoutGuestTestCaseData.json");
                SearchStatus searchStatus = SearchStatus.Pending;
                DeliveryPaymentStatus deliveryPaymentStatus = DeliveryPaymentStatus.Pending;

                for (PurchaseTestCase testCase : testCases) {
                        purchaseTest = extent.createTest("Search & Add Product to Shopping Cart");
                        searchStatus = SearchStatus.Started;
                        try {
                                if (page.isClosed()) {
                                        context = browser.newContext(
                                                        new Browser.NewContextOptions()
                                                                        .setViewportSize(null)
                                                                        .setRecordVideoSize((int) dimension.getWidth(),
                                                                                        (int) dimension.getHeight())
                                                                        .setRecordVideoDir(Paths.get(
                                                                                        "recording/Guest_Checkout_Test")));
                                        page = context.newPage();
                                }
                                page.navigate("https://www.mannings.com.hk/en");
                                page.waitForTimeout(waitForTimeout);
                                // Pre-start
                                purchaseTest.info("Popup Windows Handling");
                                HandlePopUp.closePromotionPopUp(page);
                                HandlePopUp.closeCookiesPopUp(page);

                                // Search Product
                                purchaseTest.info("Searching Products");
                                startProductSearch(
                                                testCase.getProductTestData().getSearchMode(),
                                                testCase.getProductTestData().getSearchKeyword(),
                                                testCase.getProductTestData().getProductName(),
                                                testCase.getProductTestData().getCategory(),
                                                testCase.getProductTestData().getIsSearchCategory(),
                                                testCase.getProductTestData().getBrand(),
                                                testCase.getProductTestData().getIsSearchBrand());

                                // Save screenshot into test report
                                searchStatus = SearchStatus.Completed;
                                now = LocalDateTime.now();
                                timestamp = now.format(formatter);
                                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                                                .setPath(Paths.get("screenshots/Guest_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                                purchaseTest.pass(
                                                "Product found and added to shopping cart",
                                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image)
                                                                .build())
                                                .assignCategory("Product-Search");
                                // Next test section starts
                                deliveryPaymentStatus = DeliveryPaymentStatus.Started;
                                HandleShoppingCart.goToShoppingCart(page);

                                // Delivery handling
                                deliveryTest = extent.createTest("Delivery and Payment Test");
                                if (testCase.getDeliveryTestData().getIsHomeDelivery()) {
                                        deliveryTest.info("Select Home Delivery");
                                        startHomeDelivery(testCase, deliveryTest);
                                } else {
                                        deliveryTest.info("Select Click and Collect");
                                        startClickAndCollect(testCase, deliveryTest);
                                }

                                // Select Payment
                                deliveryTest.info("Select Payment method");
                                HandlePaymentMethod.selectPaymentMethod(page, testCase.getPaymentOptions());
                                HandlePaymentMethod.confirmCheckout(page);
                                if (testCase.getPaymentOptions() != 2) { // Payment other than Credit Card
                                        HandlePaymentMethod.cancelTransaction(page);
                                }
                                // Screencap for success
                                deliveryPaymentStatus = DeliveryPaymentStatus.Completed;
                                now = LocalDateTime.now();
                                timestamp = now.format(formatter);
                                screenshot = page.screenshot(new Page.ScreenshotOptions()
                                                .setPath(Paths.get("screenshots/Guest_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                base64Image = Base64.getEncoder().encodeToString(screenshot);
                                page.waitForTimeout(waitForTimeout);
                                deliveryTest.pass("Completed delivery and payment selection",
                                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image)
                                                                .build())
                                                .assignCategory("Delivery and Payment");

                        } catch (Exception e) {
                                now = LocalDateTime.now();
                                timestamp = now.format(formatter);
                                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                                                .setPath(Paths.get("screenshots/Guest_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                                if (searchStatus == SearchStatus.Started) {
                                        purchaseTest.fail("Unable to found/add to shopping cart",
                                                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                                                        base64Image).build())
                                                        .assignCategory("Product-Search");
                                }

                                if (deliveryPaymentStatus == DeliveryPaymentStatus.Started) {
                                        deliveryTest.fail("Unable complete delivery and payment",
                                                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                                                        base64Image).build())
                                                        .assignCategory("Delivery and Payment");
                                }

                                System.out.println(e.getMessage() + "excception is found");
                        } finally {
                                String currentRecordingPath = page.video().path().toString();

                                extent.flush();

                                context.close();
                                page.close();

                                renameRecording(testCase.getTestCaseId(), currentRecordingPath);
                        }
                }
        }

        @AfterEach
        void terminate() throws IOException {
                Desktop.getDesktop().browse(new File(extentReportPath).toURI());
                browser.close();
                playwright.close();
        }

        void startProductSearch(int searchModeNumber, String searchKeywords,
                        String productName, String category, boolean searchForCategory,
                        String brand, boolean searchForBrand) {
                if (searchModeNumber == 2) { // Select section and filters
                        HandleSearch.search(page, searchKeywords, searchModeNumber);
                        HandleSearchResult.filterCategories(page, category, searchForCategory);
                        HandleSearchResult.filterBrand(page, brand, searchForBrand);
                        HandleSearchResult.addItem(page, productName);
                } else { // Search and (Enter / View all)
                        HandleSearch.search(page, searchKeywords, searchModeNumber);
                        HandleSearchResult.searchFor(page, searchKeywords, productName);
                }
        }

        void renameRecording(String currentTestId, String currentPath) {
                now = LocalDateTime.now();
                timestamp = now.format(formatter);

                String replaceString = "recording\\\\Guest_Checkout_Test\\\\" + currentTestId + "_" + timestamp
                                + ".webm";
                String newPath = currentPath.replaceAll("recording\\\\Guest_Checkout_Test\\\\[^\\\\]+\\.webm",
                                replaceString);

                File oldFile = new File(currentPath);
                File newFile = new File(newPath);

                if (oldFile.renameTo(newFile)) {
                        System.out.println("File renamed successfully.");
                } else {
                        System.out.println("Failed to rename file.");
                }

        }

        void startClickAndCollect(PurchaseTestCase testCase, ExtentTest deliveryTest) {
                HandleClickAndCollect.selectClickNCollect(page);
                page.waitForTimeout(3000);

                deliveryTest.info("Updating purchase quantity");
                HandleClickAndCollect.increaseQty(page,
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getPurchaseQty());
                HandleClickAndCollect.decreaseQty(page,
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getPurchaseQty());

                HandleClickAndCollect.clickCheckoutSecurely(page);
                HandleClickAndCollect.guestCheckout(page);

                deliveryTest.info("Searching Stores");
                HandleClickAndCollect.searchStore(page,
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getSearchKeyword(),
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getResultPreference());

                deliveryTest.info("Inputing Collect by information");
                HandleClickAndCollect.selectCountryCode(page,
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getCountryCode(),
                                testCase.getDeliveryTestData().getClickAndCollectTestData().getMobile());

                HandleClickAndCollect.clickConfirmDetails(page);

        }

        void startHomeDelivery(PurchaseTestCase testCase, ExtentTest deliveryTest) {
                HandleHomeDeliveryJ.SelectHomeDelivery(page);
                page.waitForTimeout(3000);

                deliveryTest.info("Updating purchase quantity");
                HandleHomeDeliveryJ.increaseQty(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getNewQty());
                HandleHomeDeliveryJ.decreaseQty(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getNewQty());
                HandleHomeDeliveryJ.CheckoutSecurely(page);
                HandleHomeDeliveryJ.GuestCheckout(page);

                deliveryTest.info("Handling Address Input");
                HandleHomeDeliveryJ.inputEmail(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getEmail());

                HandleHomeDeliveryJ.inputFullName(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getFullName());

                HandleHomeDeliveryJ.selectRegion(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getRegion());

                HandleHomeDeliveryJ.selectDistrict(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getDistrict());

                HandleHomeDeliveryJ.inputStreetNumber(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getStreetNumber());

                HandleHomeDeliveryJ.inputBuildingAndEstate(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getBuidling());

                HandleHomeDeliveryJ.inputUnitAndFloor(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getUnit());

                HandleHomeDeliveryJ.selectCountryCode(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getCountryCode());

                HandleHomeDeliveryJ.inputMobileNumber(page,
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getCountryCode(),
                                testCase.getDeliveryTestData().getHomeDeliveryTestData().getMobile());
                // Comfirm the order
                HandleHomeDeliveryJ.clickConfirmAddess(page);
                HandleHomeDeliveryJ.clickConfirmDetails(page);
        }
}
