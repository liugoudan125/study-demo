package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.domain.InventoryDO;
import com.example.domain.OrderDO;
import com.example.fegin.client.OrderFeginClient;
import com.example.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2023/4/7 18:17
 * @desc InventoryController
 */
@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private OrderFeginClient orderFeginClient;

    @GetMapping("get")
    public OrderDO get() {
        return orderFeginClient.get();
    }

    @Autowired
    private InventoryMapper inventoryMapper;

    @PostMapping("deductionInventory")
    public InventoryDO deductionInventory(@RequestBody Integer productId) {
        InventoryDO inventoryDO = inventoryMapper.selectOne(
                new LambdaQueryWrapper<InventoryDO>()
                        .eq(InventoryDO::getProductId, productId)
        );
        inventoryMapper.update(null,
                new LambdaUpdateWrapper<InventoryDO>()
                        .eq(InventoryDO::getProductId, productId)
                        .eq(InventoryDO::getQuantity, inventoryDO.getQuantity())
                        .set(InventoryDO::getQuantity, inventoryDO.getQuantity() - 1)
        );
        return inventoryMapper.selectOne(
                new LambdaQueryWrapper<InventoryDO>()
                        .eq(InventoryDO::getProductId, productId)
        );
    }
}
