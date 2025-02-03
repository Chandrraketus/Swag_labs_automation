package org.example;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListeners implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        WebDriver driver = ((loginpage) testInstance).getDriver(); // Cast the test class to access WebDriver
        if (driver != null) {
            try {
                captureScreenshot(driver, result.getName());
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    private void captureScreenshot(WebDriver driver, String testName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = "Screenshot_" + testName + "_" + timeStamp + ".png";

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String screenshotsDir = System.getProperty("user.dir") + "/screenshots/";
        Files.createDirectories(Paths.get(screenshotsDir)); // Create the directory if it doesn't exist

        File destination = new File(screenshotsDir + screenshotName);
        Files.copy(source.toPath(), destination.toPath());

        System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test execution started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test execution finished: " + context.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but within success percentage: " + result.getName());
    }
}
