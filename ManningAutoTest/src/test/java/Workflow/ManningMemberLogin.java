package Workflow;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.awt.Dimension;

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
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import TestingData.LoginTestCase;
import Util.HandleCaptcha;
import Util.HandleLogin;
import Util.HandleOTP;
import Util.HandlePopUp;
import Util.HandleRestoreShoppingCart;
import Util.JsonDataReader;

public class ManningMemberLogin {
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
    ExtentTest loginTest;
    String extentReportPath;
    // Timestamp related setup
    LocalDateTime now;
    DateTimeFormatter formatter;
    String timestamp;

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
                        .setRecordVideoSize((int) dimension.getWidth(), (int) dimension.getHeight())
                        .setRecordVideoDir(Paths.get("recording/Login_Test/")));

        page = browserContext.newPage();
        // Datetime format for path creation
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        timestamp = now.format(formatter);
        // Extent Report Config
        String reportName = "Login_Test";
        extentReportPath = System.getProperty("user.dir") + "/reports/" + reportName + "/" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Login");
        extent.attachReporter(spark);
    }

    @Test
    void startMemberLoginTest() throws IOException {
        System.out.println("Test Start");
        List<LoginTestCase> testCases = jsonReader.readLoginTestData("LoginTestCaseData.json");
        System.out.println("Total test cases: " + testCases.size());
        for (LoginTestCase testCase : testCases) {
            try {
                if (page.isClosed()) {
                    browserContext = browserType.launchPersistentContext(path,
                            new BrowserType.LaunchPersistentContextOptions()
                                    .setHeadless(false)
                                    .setArgs(Arrays.asList("--start-maximized"))
                                    .setSlowMo(clickInteval)
                                    .setViewportSize(null)
                                    .setRecordVideoSize((int) dimension.getWidth(), (int) dimension.getHeight())
                                    .setRecordVideoDir(Paths.get("recording/Login_Test/")));

                    page = browserContext.newPage();
                }
                loginTest = extent.createTest("Login Test");
                // Go to Homepage
                page.navigate("https://www.mannings.com.hk/en");
                page.waitForTimeout(waitForTimeout);
                // Handling Popup windows
                loginTest.info("Popup Windows Handling");
                HandlePopUp.closePromotionPopUp(page);
                HandlePopUp.closeCookiesPopUp(page);

                // Login Page
                HandleLogin.goToLoginPage(page);
                if (testCase.getLoginByEmail()) { // true = use email, false = user mobile
                    HandleLogin.inputLoginEmailOrPhone(page, testCase.getEmailAddress());
                } else {
                    HandleLogin.inputLoginEmailOrPhone(page,
                            testCase.getCountryCode() + testCase.getMobileNumber());
                }
                HandleLogin.inputPassword(page, testCase.getPassword());

                HandleCaptcha.clickCaptchaTextbox(page);
                HandleLogin.clickLoginButton(page);
                HandleOTP.clickOTPtextbox(page);
                // We should be logged in
                page.waitForTimeout(3000);
                HandleRestoreShoppingCart.restoreShoppingCart(page);

                // Screen Capture for successfully run
                page.waitForTimeout(3000);
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/Login_Test/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);

                loginTest.pass(
                        "Login Completed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
                        .assignCategory("Login Successfully");
                page.waitForTimeout(3000);
            } catch (Exception e) {
                page.waitForTimeout(3000);
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/Login_Test/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                loginTest.fail("Login Incomplete",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
                        .assignCategory("Login Failed");
                System.out.println(e.getMessage());
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

        String replaceString = "recording\\\\Login_Test\\\\" + currentTestId + "_" + timestamp + ".webm";
        String newPath = currentPath.replaceAll("recording\\\\Login_Test\\\\[^\\\\]+\\.webm",
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
