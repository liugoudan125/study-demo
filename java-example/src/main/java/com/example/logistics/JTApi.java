package com.example.logistics;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.example.util.HttpUtil;
import com.example.util.MapUtil;
import okhttp3.Request;

import java.time.Clock;
import java.util.Base64;
import java.util.Map;


/**
 * @author
 * @date 2023/6/8 10:13
 * @desc Main
 */
public class JTApi {

    //测试
    private static final String apiAccount = "xxx";
    private static final String privateKey = "xxx";
    //生产
//    private static final String apiAccount = "528860677046018077";
//    private static final String privateKey = "9bc149e41b6f44b7a24ecbdbf3a4375b";

    private static final String baseUrl = "https://uat-openapi.jtexpress.com.cn"; //测试
//    private static final String baseUrl = "https://openapi.jtexpress.com.cn"; //生产

    public static void main(String[] args) throws Exception {
        HttpUtil.addIntercepter(chain -> {
            Request request = chain.request();
            System.out.println(request.headers());
            System.out.println(request.body().isDuplex());
            return chain.proceed(request);
        });
        subscribe();
        query();
    }

    /**
     * 物流轨迹订阅
     */
    private static void subscribe() throws Exception {
        String url = baseUrl + "/webopenplatformapi/api/trace/subscribe";
        Map<String, String> params = MapUtil.createBuilder().add("bizContent", "{" +
                "\"id\": \"1001\"," +
                "\"list\": [{" +
                "\"traceNode\": \"1&2&3&4&5&6&7&8&9&10&11\"," +
                "\"waybillCode\": \"JT4000096429493\"" +
                "}]" +
                "}").builder();
        Map<String, String> headers = MapUtil.createBuilder()
                .add("apiAccount", apiAccount)
                .add("digest", Base64.getEncoder().encodeToString(DigestUtil.md5(params.get("bizContent") + privateKey)))
                .add("timestamp", String.valueOf(Clock.systemUTC().millis()))
                .builder();
        String jsonStr = HttpUtil.postForm(url, headers, params);
        System.out.println(JSONUtil.toJsonPrettyStr(JSONUtil.toBean(jsonStr, Map.class)));
    }

    /**
     * 物流轨迹查询
     *
     * @throws Exception
     */
    private static void query() throws Exception {
        String url = baseUrl + "/webopenplatformapi/api/logistics/trace";
        Map<String, String> params = MapUtil.createBuilder()
                .add("bizContent", "{\"billCodes\":\"UT0000352320970,UT0000359748995,UT0000353613157\"}")
                .builder();
        Map<String, String> headers = MapUtil.createBuilder()
                .add("apiAccount", apiAccount)
                .add("digest", Base64.getEncoder().encodeToString(DigestUtil.md5(params.get("bizContent") + privateKey)))
                .add("timestamp", String.valueOf(Clock.systemUTC().millis()))
                .builder();
        String jsonStr = HttpUtil.postForm(url, headers, params);
        Map result = JSONUtil.toBean(jsonStr, Map.class);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }
}
