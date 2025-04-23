package online.goudan.util;

import online.goudan.domain.VideoInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 刘成龙
 * @date 2021/6/24 11:26
 * @desc WebDriverPaserHtml
 */
@SuppressWarnings("all")
public class WebDriverManager {
    static {
        if (OSinfo.isWindows()) {
<<<<<<< HEAD
            System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
=======
            System.setProperty("webdriver.edge.driver", "classpath:msedgedriver.exe");
>>>>>>> 006c637 (2023-04-05 01:22:21)
        } else if (OSinfo.isMacOS() || OSinfo.isMacOSX()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
    }

    private static List<WebDriver> driverList = new ArrayList<>();
    private WebDriver webDriver;


    private WebDriverManager() {
        webDriver = new EdgeDriver();
        driverList.add(webDriver);
//        webDriver.manage().window().setPosition(new Point(-2000, -1000));
    }

    public static WebDriverManager getInstance() {
        return new WebDriverManager();
    }

    public static void close() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(driverList.size());
        for (WebDriver webDriver : driverList) {
            executorService.execute(() -> {
                try {
                    webDriver.quit();
                    countDownLatch.countDown();
                } catch (Exception e) {

                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public VideoInfo useWebDriverGetVideoInfo(String videoPageUrl) {
        boolean isSuccess = false;
        String videoName = null;
        String m3u8Url = null;
        while (!isSuccess) {
            try {
                webDriver.get(videoPageUrl);
                //获取视频名称
//                videoName = webDriver.findElement(By.xpath("/html/body/div/div[2]/div[2]/span[4]/h1/a")).getText();
                videoName = webDriver.findElement(By.cssSelector("#main > h1 > a")).getText();
                //获取视频的m3u8下载地址
//                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"main_mv\"]/iframe"));
                WebElement element = webDriver.findElement(By.cssSelector("#main_mv > iframe"));
                webDriver.switchTo().frame(element);
//                WebElement source = webDriver.findElement(By.xpath("/html/body/div[1]/div/video/source"));
                WebElement source = webDriver.findElement(By.cssSelector("#hls-video > source"));
                m3u8Url = source.getAttribute("src");
                isSuccess = true;
                webDriver.quit();
            } catch (Exception e) {
//                System.out.println("getVideoInfo fail");
                isSuccess = false;
            }
        }
        return new VideoInfo(videoName, m3u8Url);
    }
}
