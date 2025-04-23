package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @author
 * @date 2023/5/8 10:31
 * @desc Main
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lcl\\Downloads\\chrome\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        WebDriver webDriver = new ChromeDriver(options);
        try {
            webDriver.get("https://bz.zzzmh.cn/index");

            WebElement next = webDriver.findElement(By.cssSelector("#app > div.vue_pagination_next.vue_pagination_item"));
            next.click();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriverWait.until(ExpectedConditions.urlContains("index"));
            List<WebElement> elements = webDriver.findElements(By.cssSelector(".img-box"));
            for (WebElement element : elements) {
                WebElement element1 = element.findElement(By.cssSelector(".down-span > a"));
                String imgUrl = element1.getAttribute("href");
                System.out.println(imgUrl);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        } finally {
            webDriver.quit();
        }
    }

}
