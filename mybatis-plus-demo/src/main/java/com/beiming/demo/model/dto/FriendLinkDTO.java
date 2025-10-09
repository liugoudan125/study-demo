package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 友链表(FriendLink)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class FriendLinkDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 友链名称
     **/
    private String name;

    /**
     * 友链URL
     **/
    private String url;

    /**
     * 友链Logo
     **/
    private String logo;

    /**
     * 友链描述
     **/
    private String description;

    /**
     * 友链分类
     **/
    private String category;

    /**
     * 排序权重
     **/
    private Integer sortOrder;

    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     **/
    private LocalDateTime updatedAt;

}

