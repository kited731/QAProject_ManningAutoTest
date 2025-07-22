package Workflow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import TestingData.PurchaseTestCase;
import Util.HandleCheckoutHomeDelivery;
import Util.HandleHomeDeliveryJ;
import Util.HandlePaymentMethod;
import Util.HandlePopUp;
import Util.HandleProductDetailPage;
import Util.HandleSearch;
import Util.HandleSearchResult;
import Util.HandleShoppingCart;
import Util.JsonDataReader;

public class ManningMemberCheckoutTest {
        // Playwright
        private Playwright playwright;
        private BrowserType browserType;
        private BrowserContext browserContext;
        private Page page;
        private String userDataDir = "./user-data/chrome";
        private Path path = FileSystems.getDefault().getPath(userDataDir);
        // Test Config
        private int clickInteval = 500;
        private int waitForTimeout = 5000;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Json Test Cases
        private JsonDataReader jsonReader = new JsonDataReader();
        // Extent Report Config
        ExtentReports extent = new ExtentReports();
        ExtentTest memberPurchaseTest;
        ExtentTest memberDeliveryPaymentTest;
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
                browserType = playwright.chromium();

                browserContext = browserType.launchPersistentContext(path,
                                new BrowserType.LaunchPersistentContextOptions()
                                                .setHeadless(false)
                                                .setArgs(Arrays.asList("--start-maximized"))
                                                .setSlowMo(clickInteval)
                                                .setViewportSize(null)
                                                .setRecordVideoSize((int) dimension.getWidth(),
                                                                (int) dimension.getHeight())
                                                .setRecordVideoDir(Paths.get("recording/Member_Checkout_Test/")));

                page = browserContext.newPage();
                // Datetime format for path creation
                now = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                timestamp = now.format(formatter);
                // Extent Report Config
                String reportName = "Member_Checkout_Test";
                extentReportPath = System.getProperty("user.dir") + "/reports/" + reportName + "/" + timestamp
                                + ".html";
                ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
                spark.config().setTheme(Theme.DARK);
                spark.config().setDocumentTitle("Automation Test Report");
                spark.config().setReportName("Login");
                extent.attachReporter(spark);
        }

        @Test
        void startMemberCheckoutTest() throws IOException {
                List<PurchaseTestCase> testCases = jsonReader.readPurchaseTestData("CheckoutMemberTestCaseData.json");
                SearchStatus searchStatus = SearchStatus.Pending;
                DeliveryPaymentStatus deliveryPaymentStatus = DeliveryPaymentStatus.Pending;

                for (PurchaseTestCase testCase : testCases) {
                        memberPurchaseTest = extent.createTest("Search & Add Product to Shopping Cart");
                        searchStatus = SearchStatus.Started;

                        try {
                                if (page.isClosed()) {
                                        browserContext = browserType.launchPersistentContext(path,
                                                        new BrowserType.LaunchPersistentContextOptions()
                                                                        .setHeadless(false)
                                                                        .setArgs(Arrays.asList("--start-maximized"))
                                                                        .setSlowMo(clickInteval)
                                                                        .setViewportSize(null)
                                                                        .setRecordVideoSize((int) dimension.getWidth(),
                                                                                        (int) dimension.getHeight())
                                                                        .setRecordVideoDir(Paths.get(
                                                                                        "recording/Member_Checkout_Test/")));

                                        page = browserContext.newPage();
                                }
                                page.navigate("https://www.mannings.com.hk/en");
                                page.waitForTimeout(waitForTimeout);

                                // Pre-start
                                memberPurchaseTest.info("Popup Windows Handling");
                                HandlePopUp.closePromotionPopUp(page);
                                HandlePopUp.closeCookiesPopUp(page);

                                // Search Product
                                memberPurchaseTest.info("Searching Products");
                                startProductSearch(
                                                testCase.getProductTestData().getSearchMode(),
                                                testCase.getProductTestData().getSearchKeyword(),
                                                testCase.getProductTestData().getProductName(),
                                                testCase.getProductTestData().getCategory(),
                                                testCase.getProductTestData().getIsSearchCategory(),
                                                testCase.getProductTestData().getBrand(),
                                                testCase.getProductTestData().getIsSearchBrand());

                                HandleProductDetailPage.AddProductToCart(page,
                                                testCase.getProductTestData().getProductName(),
                                                testCase.getProductTestData().getPurchaseQty());

                                // Save screenshot into test report
                                searchStatus = SearchStatus.Completed;
                                now = LocalDateTime.now();
                                timestamp = now.format(formatter);
                                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                                                .setPath(Paths.get("screenshots/Member_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                                memberPurchaseTest.pass(
                                                "Product found and added to shopping cart",
                                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image)
                                                                .build())
                                                .assignCategory("Product-Search");

                                // Next test section starts
                                deliveryPaymentStatus = DeliveryPaymentStatus.Started;
                                HandleShoppingCart.goToShoppingCart(page);

                                // Delivery handling
                                memberDeliveryPaymentTest = extent.createTest("Delivery and Payment Test");
                                if (testCase.getDeliveryTestData().getIsHomeDelivery()) {
                                        memberDeliveryPaymentTest.info("Select Home Delivery");
                                        startHomeDelivery(testCase, memberDeliveryPaymentTest);
                                } else {
                                        memberDeliveryPaymentTest.info("Select Click and Collect");
                                        startClickAndCollect(testCase, memberDeliveryPaymentTest);
                                }

                                // Select Payment
                                memberDeliveryPaymentTest.info("Select Payment method");
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
                                                .setPath(Paths.get("screenshots/Member_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                base64Image = Base64.getEncoder().encodeToString(screenshot);
                                page.waitForTimeout(waitForTimeout);
                                memberDeliveryPaymentTest.pass("Completed delivery and payment selection",
                                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image)
                                                                .build())
                                                .assignCategory("Delivery and Payment");

                        } catch (Exception e) {
                                now = LocalDateTime.now();
                                timestamp = now.format(formatter);
                                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                                                .setPath(Paths.get("screenshots/Member_Checkout_Test/fullSize_"
                                                                + timestamp + ".png"))
                                                .setFullPage(true));
                                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                                if (searchStatus == SearchStatus.Started) {
                                        memberPurchaseTest.fail("Unable to found/add to shopping cart",
                                                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                                                        base64Image).build())
                                                        .assignCategory("Product-Search");
                                }

                                if (deliveryPaymentStatus == DeliveryPaymentStatus.Started) {
                                        memberDeliveryPaymentTest.fail("Unable complete delivery and payment",
                                                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                                                        base64Image).build())
                                                        .assignCategory("Delivery and Payment");
                                }

                                System.out.println(e.getMessage() + "excception is found");

                        } finally {
                                String currentRecordingPath = page.video().path().toString();
                                System.out.println(currentRecordingPath);

                                extent.flush();

                                browserContext.close();
                                page.close();

                                renameRecording(testCase.getTestCaseId(), currentRecordingPath);
                        }
                }
        }

        @AfterEach
        void terminate() throws IOException {
                Desktop.getDesktop().browse(new File(extentReportPath).toURI());
                browserContext.close();
                playwright.close();
        }

        void renameRecording(String currentTestId, String currentPath) {
                now = LocalDateTime.now();
                timestamp = now.format(formatter);

                String replaceString = "recording\\\\Member_Checkout_Test\\\\" + currentTestId + "_" + timestamp
                                + ".webm";
                String newPath = currentPath.replaceAll("recording\\\\Member_Checkout_Test\\\\[^\\\\]+\\.webm",
                                replaceString);

                File oldFile = new File(currentPath);
                File newFile = new File(newPath);

                if (oldFile.renameTo(newFile)) {
                        System.out.println("File renamed successfully.");
                } else {
                        System.out.println("Failed to rename file.");
                }

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

        // HandleCheckoutHomeDelivery
        void startHomeDelivery(PurchaseTestCase testCase, ExtentTest memberDeliveryTest) {
                HandleCheckoutHomeDelivery.selectDelivery(page);
                page.waitForTimeout(5000);

                memberDeliveryTest.info("Handle and verify delivery info");
                // Update Qty if needed
                if (testCase.getDeliveryTestData().getHomeDeliveryTestData().getIsUpdateQty()) {
                        HandleCheckoutHomeDelivery.updateToQty(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getNewQty());
                }
                // Apply coupon code if needed
                if (!testCase.getDeliveryTestData().getCouponCode().isEmpty()) {
                        HandleCheckoutHomeDelivery.applyCouponCode(page,
                                        testCase.getDeliveryTestData().getCouponCode());
                }
                // Check if the delivery fee calculate correctly
                memberDeliveryTest.info("Verifying Delivery Charges");
                page.waitForTimeout(3000);
                HandleCheckoutHomeDelivery.verifyDeliveryFee(page);
                // Checkout
                HandleCheckoutHomeDelivery.clickCheckoutSecurelyButton(page);
                // Update a generated address if want to test update address
                if (testCase.getDeliveryTestData().getHomeDeliveryTestData().getIsUpdateAddress()) {
                        HandleCheckoutHomeDelivery.changeDeliveryDetail(page);
                }

                page.waitForTimeout(5000);
                Locator addressForm = page.locator("xpath=//div/form[contains(@class,\"customerForm-root\")]");
                if (addressForm.isVisible()) {
                        HandleCheckoutHomeDelivery.inputFullName(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getFullName());
                        HandleCheckoutHomeDelivery.inputEmail(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getEmail());
                        HandleCheckoutHomeDelivery.selectRegion(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getRegion());
                        HandleCheckoutHomeDelivery.selectDistrict(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getDistrict());
                        HandleCheckoutHomeDelivery.inputStreetNumber(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getStreetNumber());
                        HandleCheckoutHomeDelivery.inputBuildingAndEstate(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getBuidling());
                        HandleCheckoutHomeDelivery.inputUnitAndFloor(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getUnit());
                        HandleCheckoutHomeDelivery.selectCountryCode(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getCountryCode());
                        HandleCheckoutHomeDelivery.inputMobileNumber(page,
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getCountryCode(),
                                        testCase.getDeliveryTestData().getHomeDeliveryTestData().getMobile());
                        HandleCheckoutHomeDelivery.clickAddAddresButton(page);
                }
                HandleCheckoutHomeDelivery.confirmDetail(page);
        }

        // HandleCheckoutCollect
        void startClickAndCollect(PurchaseTestCase testCase, ExtentTest memberDeliveryTest) {

        }
}
