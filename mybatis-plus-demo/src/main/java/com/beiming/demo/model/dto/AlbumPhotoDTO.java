package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 相册照片表(AlbumPhoto)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:14
 */
@Data
public class AlbumPhotoDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 相册ID
     **/
    private Long albumId;

    /**
     * 照片标题
     **/
    private String title;

    /**
     * 照片描述
     **/
    private String description;

    /**
     * 图片URL
     **/
    private String imageUrl;

    /**
     * 排序权重
     **/
    private Integer sortOrder;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     **/
    private LocalDateTime updatedAt;

}

