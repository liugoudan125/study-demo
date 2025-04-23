package com.example.logistics;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.example.util.HttpUtil;
import com.example.util.MapUtil;
import okhttp3.Request;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2023/6/8 13:45
 * @desc YDApi
 */
public class YDApi {
    //生产
//    private static final String appKey = "002615";
//    private static final String appSecret = "d30effca323044dca0793111bc4b7caf";
//    private static final String baseUrl = "https://openapi.yundaex.com";
    //测试
//    private static final String appKey = "002615";
//    private static final String appSecret = "1b0bf8fbd6d7846dad5773e7ce774821";
//    private static final String baseUrl = "https://u-openapi.yundasys.com";

    //平台测试
    private static final String appKey = "xx";
    private static final String appSecret = "xxx";
    private static final String baseUrl = "https://u-openapi.yundasys.com";

    public static void main(String[] args) throws Exception {
        HttpUtil.addIntercepter(chain -> {
            Request request = chain.request();
            System.out.println(request.headers());
            System.out.println(request.body().isOneShot());
            return chain.proceed(request);
        });
        subscribe();
        query();
    }

    private static void query() throws Exception {
        Map<String, Object> params = MapUtil.createBuilder()
                .add("mailno", "340987657890876")
                .builder();
        Map<String, String> headers = MapUtil.createBuilder().add("app-key", appKey)
                .add("sign", DigestUtil.md5Hex(JSONUtil.toJsonStr(params) + "_" + appSecret))
                .add("req-time", String.valueOf(Clock.systemUTC().millis()))
                .builder();

        System.out.println(JSONUtil.toJsonStr(params));
        String jsonStr = HttpUtil.postJson(baseUrl + "/openapi/outer/logictis/query", headers, params);
        System.out.println(JSONUtil.toJsonPrettyStr(JSONUtil.toBean(jsonStr, Map.class)));
    }

    private static void subscribe() throws Exception {

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(MapUtil.createBuilder().add("orderid", "9987765544332").add("mailno", "3309876541228").builder());
        list.add(MapUtil.createBuilder().add("orderid", "8876544333322").add("mailno", "8876544333322").builder());
        Map<String, Object> params = MapUtil.createBuilder()
                .add("orders", list)
                .builder();
        Map<String, String> headers = MapUtil.createBuilder().add("app-key", appKey)
                .add("sign", DigestUtil.md5Hex(JSONUtil.toJsonStr(params) + "_" + appSecret))
                .add("req-time", String.valueOf(Clock.systemUTC().millis()))
                .builder();
        System.out.println(JSONUtil.toJsonStr(params));
        String jsonStr = HttpUtil.postJson(baseUrl + "/openapi/outer/logictis/subscribe", headers, params);
        System.out.println(JSONUtil.toJsonPrettyStr(JSONUtil.toBean(jsonStr, Map.class)));
    }
}
