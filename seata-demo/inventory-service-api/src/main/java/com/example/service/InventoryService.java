package com.example.service;

import com.example.domain.InventoryDO;

/**
 * @author
 * @date 2023/4/8 17:16
 * @desc InventoryService
 */
public interface InventoryService {
    InventoryDO deductionInventory(Integer productId);
}
