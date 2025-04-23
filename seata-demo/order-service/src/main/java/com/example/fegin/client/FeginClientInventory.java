package com.example.fegin.client;

import com.example.domain.InventoryDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author
 * @date 2023/4/7 18:37
 * @desc FeginClientInventory
 */
@FeignClient(value = "inventory-service")
public interface FeginClientInventory {

    @PostMapping("inventory/deductionInventory")
    InventoryDO deductionInventory(@RequestBody Integer productId);
}
