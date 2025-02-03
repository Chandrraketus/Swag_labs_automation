package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Listeners({TestListeners.class})
public class loginpage {
    public WebDriver driver;
    @Test(dataProvider = "cred") // Fixed missing parenthesis
    public void login(String username, String password) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/v1/");
        driver.findElement(By.xpath("//input[@id=\"user-name\"]")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();

     



    }

    @DataProvider(name = "cred")
    public Object[][] dataset1(){
        return new Object[][]
                {
                        {"standard_user","secret_sauce"},
                        {"problem_user","secret_sauce"},
                        {"input","passwor"}

                };

    }

    public WebDriver getDriver() {
        return driver;


}

}
