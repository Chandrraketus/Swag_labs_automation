package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectionData implements ITestListener {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void reportSetup() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setReportName("Ronit");
        sparkReporter.config().setReportName("Extent Report Demo");
        sparkReporter.config().setTheme(Theme.DARK);

    }

    @Test

    public void SelectionData() throws InterruptedException {
        test = extent.createTest("Selection Data Test");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/");
        driver.findElement(By.xpath("//input[@id =\"user-name\"]")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id =\"password\"]")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@value =\"LOGIN\"]")).click();
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class=\"inventory_item_name\"]"));
        Set<WebElement> set = new HashSet<>(elementList);
        System.out.println(set.size());
        Thread.sleep(100);
        for (WebElement element : set) {
            String iteamname = element.getText();
            System.out.println(iteamname);


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            if (iteamname.equals("Sauce Labs Bolt T-Shirt")) {
                element.click();
                driver.findElement(By.xpath("//button[contains(text() ,'ADD TO CART')]")).click();
                driver.findElement(By.xpath("//div[@class=\"shopping_cart_container\"]")).click();
                driver.findElement(By.xpath("//a[contains(text(),'CHECKOUT')]")).click();
                driver.findElement(By.xpath("//input[@id=\"first-name\"]")).sendKeys("Chandraketu");
                WebElement lastname = driver.findElement(By.xpath("//input[@id=\"last-name\"]"));
                lastname.sendKeys("Sharma");
                WebElement zipcode = driver.findElement(By.xpath("//input[@id=\"postal-code\"]"));
                zipcode.sendKeys("804403");
                WebElement contnue = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
                contnue.click();
                WebElement finish = driver.findElement(By.xpath("//a[contains(text(),'FINISH')]"));
                finish.click();
                WebElement placed = driver.findElement(By.xpath("//h2[contains(text(),'THANK YOU FOR YOUR ORDER')]"));
                placed.getText();

                Assert.assertTrue(placed.isDisplayed());
                test.log(Status.PASS, "Order placed successfully");


                String screenshotPath = takeScreenshot("Order_Confirmation");
                test.addScreenCaptureFromPath(screenshotPath);

            }
        }

    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();  // Ensure the report is saved


    }

    public String takeScreenshot(String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = "test-output/screenshots/" + screenshotName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);

        return filePath;
    }
}


