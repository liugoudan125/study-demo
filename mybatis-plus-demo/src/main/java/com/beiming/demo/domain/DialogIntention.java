package com.beiming.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DialogIntention
 */
/**
 * 对话意图
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dialog_intention")
public class DialogIntention {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID
     */
    @TableField(value = "customer_id")
    private Long customerId;

    /**
     * 类型(基础/客户) 10 基础 20 客户
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 意图名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 关键词
     */
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}