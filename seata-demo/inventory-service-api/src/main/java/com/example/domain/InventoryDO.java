package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/4/7 18:26
 * @desc ${DESC}  */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_inventory")
public class InventoryDO implements Serializable {
    private static final long serialVersionUID = 4490085235038182844L;
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "product_id")
    private Integer productId;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public static final String COL_ID = "id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_QUANTITY = "quantity";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}