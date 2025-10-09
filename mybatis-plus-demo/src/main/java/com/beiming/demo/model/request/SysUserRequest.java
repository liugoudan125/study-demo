package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 用户表(SysUser)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:19
 */
@Data
public class SysUserRequest implements Serializable {

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
}

