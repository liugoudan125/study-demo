import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * SpiderTest
 */
public class SpiderTest {

    private static ChromeOptions initOptions() {
        ChromeOptions options = new ChromeOptions();
        System.getProperties().setProperty("webdriver.chrome.driver",
                "c:\\chrome\\chromedriver.exe");
        options.setBinary("c:\\chrome\\chrome-headless-shell.exe");
        options.addArguments("headless");
        options.addArguments("disable-gpu");
        options.addArguments("no-sandbox");
        options.addArguments("blink-settings=imageEnabled=false");
        options.addArguments("disable-gpu-program-cache");
        options.addArguments("Connection=keep-alive");
        options.addArguments("User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0");
        return options;
    }

    public static void main(String[] args) {
        ChromeOptions chromeOptions = initOptions();
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {
            driver.get("https://www.baidu.com");
            WebElement element = driver.findElement(By.xpath("/html"));
            String outerHTML = element.getAttribute("outerHTML");
            System.out.println(outerHTML);

            driver.get("https://www.zhihu.com");
            WebElement element1 = driver.findElement(By.xpath("/html"));
            String outerHTML1 = element1.getAttribute("outerHTML");
            System.out.println(outerHTML1);

            System.out.println("完成");
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            driver.quit();
        }
    }

}
