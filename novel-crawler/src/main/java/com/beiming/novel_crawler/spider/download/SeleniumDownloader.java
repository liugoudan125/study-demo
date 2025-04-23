package com.beiming.novel_crawler.spider.download;

import com.beiming.novel_crawler.spider.properties.WebDriverProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 使用Selenium调用浏览器进行渲染。目前仅支持chrome。<br>
 * 需要下载Selenium driver支持。<br>
 *
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 * Time: 下午1:37 <br>
 */
public class SeleniumDownloader extends AbstractDownloader implements Closeable {

    private final WebDriverProperties properties;

    private WebDriverPool webDriverPool;

    private ExecutorService executorService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public SeleniumDownloader(WebDriverProperties properties) {
        this.properties = properties;
    }

    @Override
    public Page download(Request request, Task task) {
        checkWebDriverPool();
        Page page = Page.fail(request);
        WebDriver webDriver = null;
        try {
            webDriver = webDriverPool.get();
            logger.debug("downloading page {}", request.getUrl());
            webDriver.get(request.getUrl());
            WebDriver.Options manage = webDriver.manage();
            Site site = task.getSite();
            if (site.getCookies() != null) {
                for (Map.Entry<String, String> cookieEntry : site.getCookies()
                        .entrySet()) {
                    Cookie cookie = new Cookie(cookieEntry.getKey(),
                            cookieEntry.getValue());
                    manage.addCookie(cookie);
                }
            }

            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            String content = webElement.getAttribute("outerHTML");
            page.setDownloadSuccess(true);
            page.setRawText(content);
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
            onSuccess(page, task);
        } catch (Exception e) {
            logger.error("下载页面 {} 出错,原因是: {}", request.getUrl(), e.getMessage());
            onError(page, task, e);
        } finally {
            webDriverPool.returnToPool(webDriver);
        }
        return page;
    }

    private void checkWebDriverPool() {
        if (null == webDriverPool || !webDriverPool.isStatus()) {
            synchronized (this) {
                if (null == webDriverPool || !webDriverPool.isStatus()) {
                    webDriverPool = new WebDriverPool(properties);
                }
            }
        }
    }

    @Override
    public void setThread(int threadNum) {
    }


    @Override
    public void close() {
        try {
            webDriverPool.destroy(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onSuccess(Page page, Task task) {
        if (page.getRequest() != null) {
            Request request = page.getRequest();
            request.putExtra("status", "SUCCESS");
            page.addTargetRequest(request);
        }
    }

    @Override
    protected void onError(Page page, Task task, Throwable e) {
        if (page.getRequest() != null) {
            Request request = page.getRequest();
            request.putExtra("status", "FAIL");
            page.addTargetRequest(request);
        }
    }
}