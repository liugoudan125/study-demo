package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class CommentDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 文章ID
     **/
    private Long articleId;

    /**
     * 用户ID（可为空，支持匿名评论）
     **/
    private Long userId;

    /**
     * 评论者昵称
     **/
    private String nickname;

    /**
     * 父评论ID（用于回复功能）
     **/
    private Long parentId;

    /**
     * 评论内容
     **/
    private String content;

    /**
     * IP地址
     **/
    private String ipAddress;

    /**
     * 用户代理
     **/
    private String userAgent;

    /**
     * 点赞次数
     **/
    private Integer likeCount;

    /**
     * 状态：pending-待审核，approved-已通过，rejected-已拒绝
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

