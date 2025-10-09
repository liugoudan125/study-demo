package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 留言表(Message)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class Message {

    /**
     * 主键ID
     **/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 留言板ID
     **/
    @TableField(value = "board_id")
    private Long boardId;

    /**
     * 用户ID（可为空，支持匿名留言）
     **/
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 留言者昵称
     **/
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 留言者邮箱
     **/
    @TableField(value = "email")
    private String email;

    /**
     * 留言者网站
     **/
    @TableField(value = "website")
    private String website;

    /**
     * 留言者头像
     **/
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 留言内容
     **/
    @TableField(value = "content")
    private String content;

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
     * 状态：pending-待审核，approved-已通过，rejected-已拒绝
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

