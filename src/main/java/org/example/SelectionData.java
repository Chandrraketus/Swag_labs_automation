package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectionData implements ITestListener {
    @Test
    public void SelectionData() throws InterruptedException {
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
        for (WebElement element :set){
            String iteamname = element.getText();
            System.out.println(iteamname);


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            if(iteamname.equals("Sauce Labs Bolt T-Shirt")){
                element.click();
                driver.findElement(By.xpath("//button[contains(text() ,'ADD TO CART')]")).click();
                driver.findElement(By.xpath("//div[@class=\"shopping_cart_container\"]")).click();
                driver.findElement(By.xpath("//a[contains(text(),'CHECKOUT')]")).click();
                driver.findElement(By.xpath("//input[@id=\"first-name\"]")).sendKeys("Chandraketu");
                
            }

        }




    }

}
