package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 相册表(Album)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:35:45
 */
@Data
public class AlbumRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 相册名称
     **/
    private String name;
    /**
     * 相册描述
     **/
    private String description;
    /**
     * 封面图片URL
     **/
    private String coverImage;
    /**
     * 创建者ID
     **/
    private Long authorId;
    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;
}

