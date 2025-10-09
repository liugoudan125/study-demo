package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 友链表(FriendLink)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class FriendLink {

    /**
     * 主键ID
     **/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 友链名称
     **/
    @TableField(value = "name")
    private String name;

    /**
     * 友链URL
     **/
    @TableField(value = "url")
    private String url;

    /**
     * 友链Logo
     **/
    @TableField(value = "logo")
    private String logo;

    /**
     * 友链描述
     **/
    @TableField(value = "description")
    private String description;

    /**
     * 友链分类
     **/
    @TableField(value = "category")
    private String category;

    /**
     * 排序权重
     **/
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 状态：active-启用，inactive-禁用
     **/
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     **/
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     **/
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}

