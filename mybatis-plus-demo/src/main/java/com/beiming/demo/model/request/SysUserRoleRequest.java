package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 用户角色关联表(SysUserRole)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:20
 */
@Data
public class SysUserRoleRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 用户ID
     **/
    private Long userId;
    /**
     * 角色ID
     **/
    private Long roleId;
}

