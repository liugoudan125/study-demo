package com.beiming.spider.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.http.ClientConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SeleniumManager-2024/3/26-13:16
 */
public class SeleniumManager {

    private final ChromeOptions options;

    private final Set<ChromeDriver> driverPool = new HashSet<>();

    private final BlockingQueue<ChromeDriver> queue = new ArrayBlockingQueue<>(10);

    private static AtomicBoolean isShutdown = new AtomicBoolean(false);

    public SeleniumManager(ChromeOptions options) {
        this.options = options;
    }

    public synchronized ChromeDriver getDriver() {
        ChromeDriver driver = queue.poll();
        if (driver != null) {
            return driver;
        } else {
            if (driverPool.size() == 5) {
                try {
                    return queue.poll(10, TimeUnit.HOURS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                SChromeDriver sChromeDriver = new SChromeDriver(options);
                driverPool.add(sChromeDriver);
                return sChromeDriver;
            }
        }
    }

    public void shutdown() {
        isShutdown.set(true);
        for (ChromeDriver driver : driverPool) {
            driver.quit();
        }
    }


    private class SChromeDriver extends ChromeDriver {

        public SChromeDriver() {
        }

        public SChromeDriver(ChromeDriverService service) {
            super(service);
        }

        public SChromeDriver(ChromeOptions options) {
            super(options);
        }

        public SChromeDriver(ChromeDriverService service, ChromeOptions options) {
            super(service, options);
        }

        public SChromeDriver(ChromeDriverService service, ChromeOptions options, ClientConfig clientConfig) {
            super(service, options, clientConfig);
        }

        @Override
        public void quit() {
            if (isShutdown.get()) {
                super.quit();
            } else {
                queue.offer(this);
            }
        }

        @Override
        public void close() {
            if (this.getWindowHandles().size() > 1) {
                super.close();
            } else if (this.getWindowHandles().size() == 1) {
                if (!isShutdown.get()) {
                    queue.offer(this);
                } else {
                    super.quit();
                }
            }
        }
    }
}
