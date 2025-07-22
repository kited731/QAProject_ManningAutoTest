package Workflow;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.microsoft.playwright.TimeoutError;

import TestingData.RegistrationTestCase;
import Util.HandlePopUp;
import Util.JsonDataReader;
import Util.HandleRegistration;

public class ManningRegistrationTest {
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
    ExtentTest registrationTest;
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
                        .setRecordVideoDir(Paths.get("recording/Registration_Test/")));
        page = context.newPage();
        // Datetime format for path creation
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        timestamp = now.format(formatter);
        // Extent Report Config
        String reportName = "Registration_Test";
        extentReportPath = System.getProperty("user.dir") + "/reports/" + reportName + "/" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Registration");
        extent.attachReporter(spark);
    }

    @Test
    void startRegistrationTest() throws IOException {
        List<RegistrationTestCase> testCases = jsonReader.readRegistrationTestData("RegistrationTestCaseData.json");

        for (RegistrationTestCase testCase : testCases) {
            registrationTest = extent.createTest("Registration and verify OTP");
            try {
                if (page.isClosed()) {
                    context = browser.newContext(
                            new Browser.NewContextOptions()
                                    .setViewportSize(null)
                                    .setRecordVideoSize((int) dimension.getWidth(), (int) dimension.getHeight())
                                    .setRecordVideoDir(Paths.get("recording/Registration_Test")));
                    page = context.newPage();
                }

                // Go to Homepage
                page.navigate("https://www.mannings.com.hk/en");
                page.waitForTimeout(waitForTimeout);
                // Handling Popup windows
                registrationTest.info("Popup Windows Handling");
                HandlePopUp.closePromotionPopUp(page);
                HandlePopUp.closeCookiesPopUp(page);

                // Go to Registration Page
                // Click Create an account button
                HandleRegistration.startRegister(page);

                // Input info
                registrationTest.info("Input Registration form");
                HandleRegistration.selectTitle(page, testCase.getRegistrationTestData().getTitle());
                HandleRegistration.inputFirstName(page, testCase.getRegistrationTestData().getFirstName());
                HandleRegistration.inputLastName(page, testCase.getRegistrationTestData().getLastName());
                HandleRegistration.inputEmailAddress(page, testCase.getRegistrationTestData().getEmail());
                HandleRegistration.selectCountryCode(
                        page,
                        testCase.getRegistrationTestData().getCountryCode(),
                        testCase.getRegistrationTestData().getMobile());
                HandleRegistration.inputPassword(
                        page,
                        testCase.getRegistrationTestData().getPassword(),
                        testCase.getRegistrationTestData().getPasswordConfirm());
                registrationTest.info("Checking Legal Writings");
                // Read T&C
                if (testCase.getRegistrationTestData().isClickTNC()) {
                    HandleRegistration.clickTNC(page);
                }
                // Read IPC
                if (testCase.getRegistrationTestData().isClickPICS()) {
                    HandleRegistration.clickPICS(page);
                }
                // Read Privacy Policy A
                if (testCase.getRegistrationTestData().isClickPrivacyA()) {
                    HandleRegistration.clickPrivacyPolicyA(page);
                }
                // [Optional] Check Direct Marketing purposes
                if (testCase.getRegistrationTestData().isClickDirectMarketing()) {
                    HandleRegistration.directMarketingPurposes(page);
                }
                // [Optional] Read Privacy Policy B
                if (testCase.getRegistrationTestData().isClickPrivacyB()) {
                    HandleRegistration.clickPrivacyPolicyB(page);
                }
                // [Optional] subscribe
                if (testCase.getRegistrationTestData().isClickSubscribe()) {
                    HandleRegistration.subscribe(page);
                }
                registrationTest.info("Hanlding Captcha Manually");
                // Input Captcha (Manual)
                HandleRegistration.waitForCaptcha(page);
                // Click create an account
                HandleRegistration.clickCreate(page);

                registrationTest.info("Hanlding OTP Manually");
                // Input OTP (Manual) & Click Sumbit
                HandleRegistration.waitForOTP(page);
                // Screen Capture for successfully run
                page.waitForTimeout(3000);
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/Registration_Test/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);

                registrationTest.pass(
                        "Register Completed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
                        .assignCategory("Register Successfully");
                page.waitForTimeout(3000);
            } catch (TimeoutError timeout) {
                page.waitForTimeout(3000);
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/Registration_Test/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                registrationTest.fail("Register Incomplete",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
                        .assignCategory("Register Failed");

                System.out.println(timeout.getMessage());
            } catch (Exception e) {
                page.waitForTimeout(3000);
                now = LocalDateTime.now();
                timestamp = now.format(formatter);
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("screenshots/Registration_Test/fullSize_" + timestamp + ".png"))
                        .setFullPage(true));
                String base64Image = Base64.getEncoder().encodeToString(screenshot);
                registrationTest.fail("Register Incomplete",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
                        .assignCategory("Register Failed");

                System.out.println(e.getMessage());
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

    void renameRecording(String currentTestId, String currentPath) {
        now = LocalDateTime.now();
        timestamp = now.format(formatter);

        String replaceString = "recording\\\\Registration_Test\\\\" + currentTestId + "_" + timestamp + ".webm";
        String newPath = currentPath.replaceAll("recording\\\\Registration_Test\\\\[^\\\\]+\\.webm",
                replaceString);

        File oldFile = new File(currentPath);
        File newFile = new File(newPath);

        if (oldFile.renameTo(newFile)) {
            System.out.println("File renamed successfully.");
        } else {
            System.out.println("Failed to rename file.");
        }

    }

    void testRegex() {
        String regex = "[\\W\\d]";
        String textToVerify = "1@";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textToVerify);

        // matches part of the text
        if (matcher.find()) {
            System.out.println("The text [" + textToVerify + "] contains the regex of [" + regex + "]");
        } else {
            System.out.println("The text [" + textToVerify + "] does not contains the regex of [" + regex + "]");
        }

        // matches exactly
        if (matcher.matches()) {
            System.out.println("The text [" + textToVerify + "] matches the regex of [" + regex + "]");
        } else {
            System.out.println("The text [" + textToVerify + "] does not matches the regex of [" + regex + "]");
        }

    }
}
