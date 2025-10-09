package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 留言表(Message)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class MessageDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 留言板ID
     **/
    private Long boardId;

    /**
     * 用户ID（可为空，支持匿名留言）
     **/
    private Long userId;

    /**
     * 留言者昵称
     **/
    private String nickname;

    /**
     * 留言者邮箱
     **/
    private String email;

    /**
     * 留言者网站
     **/
    private String website;

    /**
     * 留言者头像
     **/
    private String avatar;

    /**
     * 留言内容
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

