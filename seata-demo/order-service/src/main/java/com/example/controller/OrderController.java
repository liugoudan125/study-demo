package com.example.controller;

import com.example.domain.*;
import com.example.mapper.OrderMapper;
import com.example.service.InventoryService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author
 * @date 2023/4/7 17:07
 * @desc OrderController
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @DubboReference
    private InventoryService inventoryService;

    @GetMapping("get")
    public OrderDO get() {
        return orderMapper.selectById(1);
    }

    @PostMapping("createOrder")
    public OrderDTO createOrder(@RequestBody OrderReq.CreateDTO createDTO) {
        OrderDO orderDO = OrderDO.builder().userId(createDTO.getUserId())
                .productId(createDTO.getProductId()).build();
        InventoryDO inventoryDO = inventoryService.deductionInventory(createDTO.getProductId());
        orderMapper.insert(orderDO);
        OrderDTO orderDTO = OrderDTO.builder()
                .userId(createDTO.getUserId())
                .productId(createDTO.getProductId())
                .quantity(Optional.ofNullable(inventoryDO).orElse(new InventoryDO()).getQuantity())
                .build();
        return orderDTO;
    }

    @PostMapping("mapstruct/test")
    public Object test(@RequestBody OrderReq.CreateDTO createDTO) {
        OrderDO orderDO = OrderConverter.INSTANCE.toDo(createDTO);
        System.out.println(orderDO);
        return orderDO;
    }
}
