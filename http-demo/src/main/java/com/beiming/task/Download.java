package com.beiming.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lcl
 * @date 2024/3/12 10:49
 */
@Component
public class Download {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    File dir = new File("C:\\Users\\lcl\\Downloads\\photo");
    int total = 2369;
    String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0";
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Scheduled(cron = "35 41 * * * ?")
    public void download() throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        for (int pageNum = 1; pageNum == 1; pageNum++) {
            JSONArray photos = getPhotos(pageNum);
            if (null == photos) {
                System.err.println(pageNum);
                break;
            }
            for (int index = 0; index < photos.size(); index++) {
                JSONObject photo = photos.getJSONObject(index);
                executorService.execute(() -> {
                    String photoSrc = photo.getString("photoSrc");
                    String photoName = photo.getString("photoName");
                    String height = photo.getString("photoHigh");
                    String width = photo.getString("photoWidth");
                    String imageQuality = photo.getString("imageQuality");
                    try (
                            InputStream inputStream = getHttpClient().newCall(
                                            new Request.Builder()
                                                    .get()
                                                    .url(photoSrc)
                                                    .addHeader("Referer", "https://www.xiaoger.top/")
                                                    .build()
                                    )
                                    .execute()
                                    .body()
                                    .byteStream();
                            FileOutputStream outputStream = new FileOutputStream(new File(dir, photoName))
                    ) {
                        byte[] bytes = new byte[1024 * 10];
                        int len = -1;
                        while ((len = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, len);
                        }
                        jdbcTemplate.update("""
                                        insert into 
                                        photo(
                                            photo_name,
                                            photo_src,
                                            height,
                                            width,
                                            photo_quality
                                        )
                                        values (
                                            ?,
                                            ?,
                                            ?,
                                            ?,
                                            ?
                                        )
                                        """,
                                photoName, photoSrc, height, width, imageQuality);
                        System.out.println(counter.incrementAndGet() + ": \t" + photoName);
                        System.out.println(photoSrc);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                });
            }
        }
    }

    private JSONArray getPhotos(int pageNum) throws IOException {
        System.out.println(pageNum);
        Request request = new Request.Builder()
                .addHeader("User-Agent", ua)
                .post(RequestBody.create(
                                new JSONObject()
                                        .fluentPut("current", pageNum)
                                        .fluentPut("size", total)
                                        .toString()
                                        .getBytes(StandardCharsets.UTF_8),
                                MediaType.parse("application/json")
                        )
                ).url(HttpUrl.get("https://www.xiaoger.top/api/photos")).build();
        try (Response response = getHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                return Optional.ofNullable(JSON.parseObject(response.body().string()))
                        .map(o -> o.getJSONObject("data"))
                        .map(o -> o.getJSONArray("recordList"))
                        .orElse(null);
            } else {
                System.err.println(response.message());
                return null;
            }
        }
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .callTimeout(1000000, TimeUnit.MILLISECONDS)
                .connectTimeout(1000000, TimeUnit.MILLISECONDS)
                .readTimeout(1000000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000000, TimeUnit.MILLISECONDS)
                .build();
    }

}
