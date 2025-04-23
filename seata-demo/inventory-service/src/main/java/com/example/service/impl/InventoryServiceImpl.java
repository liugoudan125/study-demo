package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.domain.InventoryDO;
import com.example.mapper.InventoryMapper;
import com.example.service.InventoryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author
 * @date 2023/4/8 17:18
 * @desc InventoryServiceImpl
 */
@DubboService
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public InventoryDO deductionInventory(Integer productId) {
        InventoryDO inventoryDO = inventoryMapper.selectOne(
                new LambdaQueryWrapper<InventoryDO>()
                        .eq(InventoryDO::getProductId, productId)
        );
        if (null != inventoryDO) {
            inventoryMapper.update(null,
                    new LambdaUpdateWrapper<InventoryDO>()
                            .eq(InventoryDO::getProductId, productId)
                            .eq(InventoryDO::getQuantity, inventoryDO.getQuantity())
                            .set(InventoryDO::getQuantity, inventoryDO.getQuantity() - 1)
            );
        }
        return inventoryMapper.selectOne(
                new LambdaQueryWrapper<InventoryDO>()
                        .eq(InventoryDO::getProductId, productId)
        );
    }
}
