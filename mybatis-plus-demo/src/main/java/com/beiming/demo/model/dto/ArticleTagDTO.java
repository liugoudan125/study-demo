package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class ArticleTagDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 文章ID
     **/
    private Long articleId;

    /**
     * 标签ID
     **/
    private Long tagId;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

}

