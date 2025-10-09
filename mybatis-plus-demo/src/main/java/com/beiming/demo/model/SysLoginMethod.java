package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 登录方式表(SysLoginMethod)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:18
 */
@Data
public class SysLoginMethod {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     **/
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 登录方式('email','username','github','wechat','gitee'）
     **/
    @TableField(value = "method_type")
    private String methodType;

    /**
     * 邮箱、用户名、第三方平台用户ID
     **/
    @TableField(value = "identifier")
    private String identifier;

    /**
     * 密码，access_token
     **/
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 如果第三方平台登录，令牌的过期时间
     **/
    @TableField(value = "expires_at")
    private LocalDateTime expiresAt;

    /**
     * 创建时间
     **/
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     **/
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}

