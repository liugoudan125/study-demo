package com.beiming;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.beiming.util.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UploadImg-2024/3/14-9:58
 */
public class UploadImg {


    private static final String defaultConfigFilePath = "/home/upload-config.properties";
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(30);
    private static final Properties properties = new Properties();

    private static final String PHOTO_DIR_KEY = "photo.dir";
    private static final String ERROR_FILE = "error.file";

    private static final String TOKEN_KEY = "token";

    private static final String URL_KEY = "url";

    private static final String ALBUM_KEY = "album.id";

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            init(args[0]);
        } else {
            init(defaultConfigFilePath);
        }
        File dir = getPhotoDir();
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                executorService.execute(() -> {
                    uploadFile(file);
                });
            }
        }
    }


    private static void init(String configPath) throws Exception {
        properties.load(new FileInputStream(configPath));
    }

    private static File getPhotoDir() {
        String photoDir = properties.getProperty(PHOTO_DIR_KEY);
        return new File(photoDir);
    }

    private static void uploadFile(File file) {

        Request request = new Request.Builder()
                .post(
                        new MultipartBody.Builder()
                                .addFormDataPart("albumId", properties.getProperty(ALBUM_KEY))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("image/*")))
                                .build()
                )
                .url(properties.getProperty(URL_KEY))
                .addHeader("Authorization", properties.getProperty(TOKEN_KEY))
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Content-Length", String.valueOf(file.length()))
                .build();

        HttpUtils.getHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        writeErrLog(file.getName() + "\t" + e.getMessage());
                        System.out.println(counter.incrementAndGet());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = JSON.parseObject(response.body().string());
                            if (jsonObject.getInteger("code") != 200) {
                                writeErrLog(file.getName() + "\t" + jsonObject.getString("errorMsg"));
                            } else {
                                System.out.println(file.getName());
                                file.delete();
                            }
                        } else {
                            writeErrLog(file.getName());
                        }
                        System.out.println(counter.incrementAndGet());
                    }
                });
    }

    private static synchronized void writeErrLog(String message) {
        File errFile = getErrorFile();
        try (FileOutputStream outputStream = new FileOutputStream(errFile, true)) {
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static File getErrorFile() {
        String errorFile = properties.getProperty(ERROR_FILE);
        return new File(errorFile);
    }


}
