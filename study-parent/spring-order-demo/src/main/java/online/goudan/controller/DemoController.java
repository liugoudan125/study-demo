package online.goudan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.goudan.domain.Child;
import online.goudan.domain.Father;
import online.goudan.domain.TbUserDO;
import online.goudan.mapper.TbUserMapper;
import online.goudan.service.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.desktop.PreferencesHandler;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author lcl
 * @date 2023/8/14 17:27
 * @desc DemoController
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private List<Order> orderList;

    @Autowired
    private TbUserMapper tbUserMapper;

    @GetMapping("getUser")
    public List<TbUserDO> getUser() {
        List<TbUserDO> tbUserDOs = tbUserMapper.selectList(new LambdaQueryWrapper<TbUserDO>().last("limit 10"));
        return tbUserDOs;
    }

    @PostMapping("update")
    public Boolean update(@RequestBody TbUserDO tbUserDO) {
        tbUserMapper.updateById(tbUserDO);
        return true;
    }

    @GetMapping("hello")
    public String hello() {
        for (Order order : orderList) {
            order.say();
        }
        return "hello";
    }

    @GetMapping("getFather")
    public Father getFather() {
        Father father = new Father();
        father.setName("father");
        father.setBrithday(new Date());
        Optional<Child> optionalChild = Optional.of(new Child());
        optionalChild
                .ifPresent(child -> child.setName("child"));
        father.setChild(optionalChild);
        return father;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("accessToken")
    public String accessToken(@RequestParam("code") String code) throws IOException, InterruptedException {
        String clientId = "xxxx";
        String secret = "xxxxx";
        String redirectUri = "http://goudan.com/demo/accessToken";
        HttpClient httpClient = HttpClient.newBuilder().build();
        StringBuilder stringBuilder = new StringBuilder()
                .append("grant_type=").append("authorization_code")
                .append("&code=").append(code)
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUri)
                .append("&client_secret=").append(secret);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://gitee.com/oauth/token"))
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(stringBuilder.toString()))
                .build();
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());
        Map<String, String> map = objectMapper.readValue(send.body(), Map.class);
        String accessToken = map.get("access_token");
        String refreshToken = map.get("refresh_token");
        HttpRequest getUserInfoRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://gitee.com/api/v5/user?access_token=" + accessToken))
                .GET().build();
        HttpResponse<String> response = httpClient.send(getUserInfoRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
