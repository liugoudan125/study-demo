package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 登录方式表(SysLoginMethod)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:18
 */
@Data
public class SysLoginMethodRequest implements Serializable {

    private Long id;
    /**
     * 用户ID
     **/
    private Long userId;
    /**
     * 登录方式('email','username','github','wechat','gitee'）
     **/
    private String methodType;
    /**
     * 邮箱、用户名、第三方平台用户ID
     **/
    private String identifier;
    /**
     * 密码，access_token
     **/
    private String accessToken;
    /**
     * 如果第三方平台登录，令牌的过期时间
     **/
    private LocalDateTime expiresAt;
}

