package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 相册表(Album)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:35:45
 */
@Data
public class Album {

    /**
     * 主键ID
     **/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 相册名称
     **/
    @TableField(value = "name")
    private String name;

    /**
     * 相册描述
     **/
    @TableField(value = "description")
    private String description;

    /**
     * 封面图片URL
     **/
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 创建者ID
     **/
    @TableField(value = "author_id")
    private Long authorId;

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

