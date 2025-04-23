package com.example.logistics;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.example.util.HttpUtil;
import com.example.util.MapUtil;
import okhttp3.Request;

import java.net.URLEncoder;
import java.time.Clock;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * @author
 * @date 2023/6/8 13:45
 * @desc YDApi
 */
public class SFApi {
    //沙箱,测试
    private static final String partnerID = "xx";
    private static final String checkCode = "xx";
    //    private static final String baseUrl = "https://bspgw.sf-express.com/std/service"; //生产环境地址
    private static final String baseUrl = "https://sfapi-sbox.sf-express.com/std/service"; //测试地址

    //生产
//    private static final String partnerID = "528860677046018077";
//    private static final String checkCode = "I9stUwPvI8Nk508eEbIPeewigJFRmJkl"; //需要平台账户所有者重新接收一份
    public static void main(String[] args) throws Exception {
        HttpUtil.addIntercepter(chain -> {
            Request request = chain.request();
            System.out.println(request.url().toString());
            System.out.println(request.headers());
            System.out.println(request.body().isDuplex());
            return chain.proceed(request);
        });
        subscribe();
        query();
    }

    private static void subscribe() throws Exception {

        Map<String, Object> requestBody = MapUtil.createBuilder()
                .add("type", "2") //1-按订单号注册, 2-按运单号注册默认为1
                .add("attributeNo", "441003077850")
                .builder();
        String jsonStr = JSONUtil.toJsonStr(requestBody);
        String timestamp = String.valueOf(Clock.systemUTC().millis());
        Map<String, String> params = MapUtil.createBuilder()
                .add("partnerID", partnerID)
                .add("requestID", UUID.randomUUID().toString().replaceAll("-",""))
                .add("serviceCode", "EXP_RECE_REGISTER_ROUTE")
                .add("timestamp", timestamp)
                .add("msgDigest", Base64.getEncoder().encodeToString(DigestUtil.md5(URLEncoder.encode(jsonStr + timestamp + checkCode, "UTF-8"))))
                .add("msgData", jsonStr)
                .builder();
        String resultJson = HttpUtil.postForm(baseUrl, MapUtil.createBuilder().builder(), params);
        Map bean = JSONUtil.toBean(resultJson, Map.class);
        Object apiResultData = bean.get("apiResultData");
        System.out.println(apiResultData);
        System.out.println(JSONUtil.toJsonPrettyStr(bean));
    }

    private static void query() throws Exception {

        Map<String, Object> requestBody = MapUtil.createBuilder()
                .add("language", "zh-CN")
                .add("trackingType", 1)
                .add("trackingNumber", Arrays.asList("441003077850","444003077898"))
                .add("methodType", 1)
                .builder();
        String jsonStr = JSONUtil.toJsonStr(requestBody);
        String timestamp = String.valueOf(Clock.systemUTC().millis());
        Map<String, String> params = MapUtil.createBuilder()
                .add("partnerID", partnerID)
                .add("requestID", UUID.randomUUID().toString().replaceAll("-",""))
                .add("serviceCode", "EXP_RECE_SEARCH_ROUTES")
                .add("timestamp", timestamp)
                .add("msgDigest", Base64.getEncoder().encodeToString(DigestUtil.md5(URLEncoder.encode(jsonStr + timestamp + checkCode, "UTF-8"))))
                .add("msgData", jsonStr)
                .builder();
        System.out.println(JSONUtil.toJsonPrettyStr(params));
        String resultJson = HttpUtil.postForm(baseUrl, MapUtil.createBuilder().builder(), params);
        Map bean = JSONUtil.toBean(resultJson, Map.class);
        Object apiResultData = bean.get("apiResultData");
        System.out.println(apiResultData);
        System.out.println(JSONUtil.toJsonPrettyStr(bean));
    }
}
