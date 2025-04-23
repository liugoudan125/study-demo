package com.example.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * @author
 * @date 2023/6/8 11:26
 * @desc HttpUtils
 */
public class HttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    public static void addIntercepter(Interceptor interceptor) {
        OkHttpClient.Builder builder = client.newBuilder().addNetworkInterceptor(interceptor);
        client = builder.build();
    }


    public static String get(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        if (CollectionUtil.isNotEmpty(params)) {
            StringBuilder stringBuilder = new StringBuilder(url + "?");
            for (String key : params.keySet()) {
                stringBuilder.append(key + "=" + params.get(key));
            }
            url = stringBuilder.toString();
        }
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        fillHeader(headers, requestBuilder);
        Request request = requestBuilder
                .build();
        return execute(request);
    }


    public static String postForm(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (CollectionUtil.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                bodyBuilder.addEncoded(key, params.get(key));
            }
        }
        RequestBody requestBody = bodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url).post(requestBody);
        fillHeader(headers, requestBuilder);
        Request request = requestBuilder.build();
        return execute(request);
    }

    public static String postJson(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        String paramsJson = "";
        if (CollectionUtil.isNotEmpty(params)) {
            paramsJson = JSONUtil.toJsonStr(params);
        }
        RequestBody requestBody = RequestBody.create(paramsJson, MediaType.parse("application/json;charset=utf-8"));
        Request.Builder requestBuilder = new Request.Builder()
                .url(url).post(requestBody);
        fillHeader(headers, requestBuilder);
        Request request = requestBuilder.build();
        return execute(request);
    }

    private static void fillHeader(Map<String, String> headers, Request.Builder requestBuilder) {
        if (CollectionUtil.isNotEmpty(headers)) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
    }

    @NotNull
    private static String execute(Request request) throws IOException {
        Call call = client.newCall(request);
        return call.execute().body().string();
    }
}
