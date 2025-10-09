package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章浏览记录表(ArticleViewLog)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class ArticleViewLogDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 文章ID
     **/
    private Long articleId;

    /**
     * IP地址
     **/
    private String ipAddress;

    /**
     * 用户代理
     **/
    private String userAgent;

    /**
     * 来源页面
     **/
    private String referer;

    /**
     * 浏览时间
     **/
    private LocalDateTime viewedAt;

}

