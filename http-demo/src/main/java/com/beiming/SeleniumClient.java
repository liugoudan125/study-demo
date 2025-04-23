package com.beiming;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * SeleniumClient-2024/3/22-13:53
 */
public class SeleniumClient {
    public static void main(String[] args) {
        String driverPath = null;
        String browserPath = null;

        if (args.length > 0) {
            driverPath = args[0];
            if (args.length > 1) {
                browserPath = args[1];
            }
        }

        if (null != driverPath) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
        ChromeDriverService service = new ChromeDriverService.Builder()
                .withLogFile(new File("selenium.log"))
                .withAppendLog(true).build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        if (null != browserPath) {
//            options.setBinary("C:\\Users\\lcl\\Downloads\\chrome-headless-shell-win64\\chrome-headless-shell.exe");
            options.setBinary(browserPath);
        }
//        options.addArguments("-headless");
        ChromeDriver driver = new ChromeDriver(service, options);
        driver.get("https://mm.enterdesk.com/bizhi/64629.html");
//        driver.get("https://www.baidu.com");
        WebElement element = driver.findElement(By.cssSelector("#showDownBtn"));
        element.click();
        String windowHandle = driver.getWindowHandle();
        System.out.println(windowHandle);
        for (String handle : driver.getWindowHandles()) {
            if (!windowHandle.equals(handle)) {
                driver.switchTo().window(handle);
            }
        }
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        driver.close();
//        driver.quit();
        driver.quit();
    }
}
