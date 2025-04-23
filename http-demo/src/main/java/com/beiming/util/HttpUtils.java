package com.beiming.util;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @author lcl
 * @date 2024/3/12 10:49
 */
public class HttpUtils {

    private static final long TIMEOUT = 1000 * 60 * 60;// 1小时
    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true);

    public static OkHttpClient getHttpClient() {
        return builder.build();
    }

}
