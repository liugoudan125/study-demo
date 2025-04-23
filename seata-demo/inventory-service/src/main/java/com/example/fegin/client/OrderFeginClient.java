package com.example.fegin.client;

import com.example.domain.OrderDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date 2023/4/7 18:16
 * @desc OrderFeginClient
 */
@FeignClient(value = "order-service")
public interface OrderFeginClient {


    @GetMapping("order/get")
    OrderDO get();
}
