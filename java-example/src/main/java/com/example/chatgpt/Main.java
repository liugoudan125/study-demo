package com.example.chatgpt;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * @author lcl
 * @date 2023/11/15 13:43
 * @desc Main
 */
public class Main {
    public static void main(String[] args) {
        // 在OpenAI网站上生成的API密钥
        String apiKey = "xxxx";

        // OpenAI API端点（例如，代码生成端点）
        String apiUrl = "https://api.openai.com/v1/engines/davinci/completions";

        // 输入文本
        String prompt = "使用html生成一个博客首页页面";

        // 构建JSON请求体
        String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";

        // 发送HTTP POST请求
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setEntity(new StringEntity(requestBody));

            HttpResponse response = httpClient.execute(httpPost);

            // 处理响应
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                System.out.println("API Response: " + jsonResponse);
            } else {
                System.out.println("API Request failed: " + response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
