package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 角色表(SysRole)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:18
 */
@Data
public class SysRoleDTO implements Serializable {

    /**
     * 角色ID
     **/
    private Long id;

    /**
     * 角色名称
     **/
    private String name;

    /**
     * 角色名称
     **/
    private String code;

    /**
     * 角色描述
     **/
    private String description;

    /**
     * 是否默认角色：0-否，1-是
     **/
    private Integer isDefault;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

    /**
     * 更新时间
     **/
    private LocalDateTime updatedAt;

}

