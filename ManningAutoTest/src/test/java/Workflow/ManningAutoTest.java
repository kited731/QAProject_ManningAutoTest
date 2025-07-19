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
import org.junit.jupiter.api.Order;
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

public class ManningAutoTest {
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
    String extentReportPath;
    // Timestamp related setup
    LocalDateTime now;
    DateTimeFormatter formatter;
    String timestamp;

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
                        .setRecordVideoSize((int) dimension.getWidth(), (int) dimension.getHeight())
                        .setRecordVideoDir(Paths.get("recording/")));
        page = context.newPage();
        // Datetime format for path creation
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        timestamp = now.format(formatter);
        // Extent Report Config
        String reportName = "Checkout_Guest";
        extentReportPath = System.getProperty("user.dir") + "/reports/" + reportName + "/" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Checkout_Guest");
        extent.attachReporter(spark);
    }

    @Test
    @Order(1)
    void startCheckoutGuestTest() throws IOException {
        List<PurchaseTestCase> testCases = jsonReader.readPurchaseTestData("CheckoutGuestTestCaseData.json");

        for (PurchaseTestCase testCase : testCases) {
            purchaseTest = extent.createTest("Search & Add Product to Shopping Cart");
            try {
                if (page.isClosed()) {
                    context = browser.newContext(
                            new Browser.NewContextOptions()
                                    .setViewportSize(null)
                                    .setRecordVideoSize((int) dimension.getWidth(), (int) dimension.getHeight())
                                    .setRecordVideoDir(Paths.get("recording/")));
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
                HandleProductDetailPage.AddProductToCart(page,
                        testCase.getProductTestData().getProductName(),
                        testCase.getProductTestData().getPurchaseQty());

                // Save screenshot into test report
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                purchaseTest.pass(
                        "Product found and added to shopping cart",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
                // Next test section starts
                HandleShoppingCart.goToShoppingCart(page);
                page.waitForTimeout(waitForTimeout);

                // Delivery handling

                // Select Payment

            } catch (Exception e) {
                System.out.println(e.getMessage() + "- excception is found");
                purchaseTest.fail("Unable to found/add to shopping cart");
            } finally {
                String currentRecordingPath = page.video().path().toString();

                extent.flush();

                context.close();
                page.close();

                renameRecording(testCase.getTestCaseId(), currentRecordingPath);
            }
        }
    }

    @Test
    @Order(2)
    void startCheckoutMemberTest() {
        page.navigate("https://www.mannings.com.hk/en/login");
        page.waitForTimeout(10000);
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

        String replaceString = "recording\\\\" + currentTestId + "_" + timestamp + ".webm";
        String newPath = currentPath.replaceAll("recording\\\\[^\\\\]+\\.webm",
                replaceString);

        File oldFile = new File(currentPath);
        File newFile = new File(newPath);

        if (oldFile.renameTo(newFile)) {
            System.out.println("File renamed successfully.");
        } else {
            System.out.println("Failed to rename file.");
        }

    }
}
