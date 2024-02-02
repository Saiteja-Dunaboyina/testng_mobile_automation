package com.booking.com.base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.App;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.JsonObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class Base {

	private static final Logger logger = LogManager.getLogger(Base.class);
    public static ExtentReports extent;
    public static ExtentTest test;
	public AppiumDriver driver;
	public Properties properties;
	public DesiredCapabilities capabilities;
	public JsonObject jsonData;
	private final JsonDataConvertor jsonDataCoverter = new JsonDataConvertor();

	@BeforeSuite
	public void setUp() {
		try {
			AppiumServer.getServer().start();
			properties = new Properties();
			properties.load(App.class.getClassLoader().getResourceAsStream("./config/booking.com.properties"));
			jsonData = jsonDataCoverter.getJson(properties.getProperty("bookingDataPath"));
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("newCommandTimeout", 30000);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.getProperty("platformName"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("deviceName"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.getProperty("automationName"));
			capabilities.setCapability(MobileCapabilityType.APP,
					System.getProperty("user.dir") + properties.getProperty("appPath"));
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, properties.getProperty("timeout"));
			capabilities.setCapability("appium:autoAcceptAlerts", properties.getProperty("autoAcceptAlerts"));
			capabilities.setCapability("appium:noReset", properties.getProperty("noReset"));
			capabilities.setCapability("appium:appPackage", properties.getProperty("appPackage"));
			capabilities.setCapability("appium:appActivity", properties.getProperty("appActivity"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
					properties.getProperty("platformVersion"));
			driver = new AndroidDriver(new URL(properties.getProperty("appiumServerUrl")), capabilities);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@BeforeTest
    public void startReport() {
        logger.info("Report Setup in each Test");
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Extentreport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("MyReport");
        spark.config().setReportName("Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

	private String captureScreenshot(String testName, String directoryPath) {
        String screenshotPath = null;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedTime = currentTime.format(formatter);
        try {
            TakesScreenshot ts =  (TakesScreenshot) driver;
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            System.out.println(testName + "    Test Name");
            screenshotPath = directoryPath + "/" + testName + formattedTime + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(screenshotPath);
        return screenshotPath;
    }
	

	@AfterMethod
    public void getResult(ITestResult result) {
        logger.info("Report Result in each Test");
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
            String path = captureScreenshot(result.getName(), System.getProperty("user.dir") + properties.getProperty("imagesPath"));
            test.log(Status.FAIL, "Test Case Failed");
            System.out.println(System.getProperty("user.dir") + properties.getProperty("imagesPath"));
            System.out.println(path);
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.fail("Screenshot below: " + test.addScreenCaptureFromPath(path));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed Sucessfully");
        } else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
