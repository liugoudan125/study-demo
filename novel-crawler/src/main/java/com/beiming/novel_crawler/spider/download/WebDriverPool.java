package com.beiming.novel_crawler.spider.download;

import com.beiming.novel_crawler.spider.properties.WebDriverProperties;
import lombok.Getter;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 * Time: 下午1:41 <br>
 */
public class WebDriverPool {

    private static final Logger log = LoggerFactory.getLogger(WebDriverPool.class);
    private final WebDriverProperties properties;

    private ChromiumOptions options;

    private volatile int driverCount = 0;

    @Getter
    private volatile boolean status = true;

    /**
     * store webDrivers available
     */
    private final BlockingQueue<WebDriver> driverQueue;

    public WebDriverPool(WebDriverProperties properties) {
        this.properties = properties;
        this.driverQueue = new ArrayBlockingQueue<>(properties.getCapacity());
        if ("chrome".equals(properties.getType())) {
            System.getProperties().setProperty("webdriver.chrome.driver",
                    properties.getDriver());
            options = new ChromeOptions();
        } else if ("edge".equals(properties.getType())) {
            System.getProperties().setProperty("webdriver.edge.driver",
                    properties.getDriver());
            options = new EdgeOptions();
        }
        initOptions();
    }

    private void initOptions() {
        if (properties.getBinary() != null && !properties.getBinary().isBlank()) {
            options.setBinary(properties.getBinary());
        }
        options.setPageLoadTimeout(Duration.ofSeconds(30));
        if (properties.isNoHead()) {
            options.addArguments("headless");
            options.addArguments("disable-gpu");
            options.addArguments("no-sandbox");
            options.addArguments("blink-settings=imageEnabled=false");
            options.addArguments("disable-gpu-program-cache");
        }
        if (properties.getProxy() != null) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(properties.getProxy());
            proxy.setSslProxy(properties.getProxy());
            options.setProxy(proxy);
        }
        options.addArguments("Connection=keep-alive");
        options.addArguments("User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0");
    }


    public WebDriver createDriver() {
        if (options instanceof ChromeOptions chromeOptions) {
            return new ChromeDriver(chromeOptions);
        } else if (options instanceof EdgeOptions edgeOptions) {
            return new EdgeDriver(edgeOptions);
        } else {
            throw new RuntimeException("请选定浏览器");
        }
    }


    public synchronized WebDriver get() throws InterruptedException {
        if (status) {
            if (driverCount < properties.getCapacity()) {
                WebDriver webDriver = createDriver();
                driverQueue.add(webDriver);
                driverCount++;
            }
            return driverQueue.take();
        } else {
            throw new RuntimeException("爬虫已停止");
        }
    }

    private final AtomicLong pre = new AtomicLong(0);

    /**
     * 10分钟释放一次浏览器内存
     */
    private final static long RELEASE_FREE_PERIOD = 1000 * 60 * 10;

    public void returnToPool(WebDriver webDriver) {
        if (webDriver != null) {
            driverQueue.add(webDriver);
            long currentTimestamp = Clock.systemUTC().millis();
            long preT = pre.get();
            if (preT == 0) {
                pre.set(currentTimestamp);
                preT = pre.get();
            }
            if (currentTimestamp - preT > RELEASE_FREE_PERIOD) {
                if (pre.compareAndSet(preT, currentTimestamp)) {
                    try {
                        destroy(true);
                    } catch (Exception e) {
                        log.error("清空driver错误", e);
                    }
                }
            }
        }
    }


    public synchronized void destroy(boolean status) throws InterruptedException {
        log.info("开始清除浏览器内存");
        if (this.status) {
            while (driverCount > 0) {
                WebDriver webDriver = driverQueue.take();
                webDriver.quit();
                driverCount--;
            }
            this.status = status;
        }
    }
}