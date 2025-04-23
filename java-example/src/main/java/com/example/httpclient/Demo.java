package com.example.httpclient;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcl
 * @date 2023/8/14 18:22
 * @desc Demo
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        int num = 10000;
        int index = 1;
        while (index <= num) {
            Map<String, Object> params = new HashMap<>();
            params.put("taskId", "18293");
            List<Map<String, String>> list = new ArrayList<>();
            params.put("list", list);
            for (int i = 0; i < 10000; i++) {
                Map<String, String> el = new HashMap<>();
                el.put("calledPhone", RandomStringUtils.random(11, false, true));
                el.put("calledName", RandomStringUtils.random(10, true, false));
                RestorableUniformRandomProvider randomProvider = RandomSource.XO_RO_SHI_RO_128_PP.create();
                el.put("taskId", String.valueOf(randomProvider.nextLong()));
                list.add(el);
            }
            HttpClient httpClient = null;
            try {
                httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/taskList/batchInsert"))
                        .POST(HttpRequest.BodyPublishers.ofString(JSONUtil.toJsonStr(list)))
                        .header("Content-Type", "application/json")
                        .build();
                HttpResponse<String> response = null;
                response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            index++;
        }


    }
}
