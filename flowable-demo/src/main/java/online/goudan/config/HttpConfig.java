package online.goudan.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author
 * @date 2023/5/31 14:35
 * @desc HttpConfig
 */
@Configuration
@ConditionalOnClass(RestTemplateBuilder.class)
@Slf4j
public class HttpConfig {

    private final RestTemplateBuilder restTemplateBuilder;


    public HttpConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean("aaa")
    public RestTemplate restTemplate() {
        return restTemplateBuilder.additionalInterceptors((request, body, execution) -> {
                    log.info("HttpConfig.intercept: post请求contentType: {}", request.getHeaders().getContentType().toString());
                    log.info("HttpConfig.intercept: post请求参数: {}", new String(body, StandardCharsets.UTF_8));
                    return execution.execute(request, body);
                }
        ).build();
    }

}


