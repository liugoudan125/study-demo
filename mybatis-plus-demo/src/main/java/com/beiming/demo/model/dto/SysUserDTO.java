package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 用户表(SysUser)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:20
 */
@Data
public class SysUserDTO implements Serializable {

    /**
     * id
     **/
    private Long id;

    /**
     * 昵称（默认账户名）
     **/
    private String nickname;

    /**
     * 头像
     **/
    private String avatar;

    /**
     * 简介
     **/
    private String introduction;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

    /**
     * 更新时间
     **/
    private LocalDateTime updatedAt;

}

