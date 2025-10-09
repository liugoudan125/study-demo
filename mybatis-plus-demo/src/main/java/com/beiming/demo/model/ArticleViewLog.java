package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 文章浏览记录表(ArticleViewLog)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class ArticleViewLog {

    /**
     * 主键ID
     **/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章ID
     **/
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * IP地址
     **/
    @TableField(value = "ip_address")
    private String ipAddress;

    /**
     * 用户代理
     **/
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 来源页面
     **/
    @TableField(value = "referer")
    private String referer;

    /**
     * 浏览时间
     **/
    @TableField(value = "viewed_at")
    private LocalDateTime viewedAt;

}

