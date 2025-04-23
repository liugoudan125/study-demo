package com.beiming.test;

import com.beiming.spider.SpiderHzApplication;
import com.beiming.spider.dao.AlbumRepository;
import com.beiming.spider.domain.Album;
import com.beiming.spider.selenium.SeleniumManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TestJpa-2024/3/26-11:33
 */
@SpringBootTest(classes = SpiderHzApplication.class)
public class TestJpa {


    @Resource
    private AlbumRepository albumRepository;

    @Test
    public void testInsert() {
        Album album = new Album();
        album.setId(1L);
        album.setName("aa");
        albumRepository.save(album);
        System.out.println(albumRepository.findById(1L));
    }


    @Resource
    private SeleniumManager seleniumManager;

    private ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Test
    public void testSelenium() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> {
                ChromeDriver driver = seleniumManager.getDriver();
                driver.get("https://www.baidu.com/s?ie=utf-8&wd=" + finalI);
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                driver.close();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }
}
