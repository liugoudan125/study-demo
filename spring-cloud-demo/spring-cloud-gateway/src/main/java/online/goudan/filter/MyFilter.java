package online.goudan.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author lcl
 * @date 2023/8/21 13:34
 * @desc MyFilter
 */
@Component
public class MyFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("customerId", "11111111111").build();
        exchange.getAttributes().put("user", "aaa");
        System.out.println(request.getURI());
        return chain.filter(exchange.mutate().request(request).build());
    }
}
