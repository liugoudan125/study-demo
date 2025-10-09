package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章分类表(Category)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Data
public class CategoryRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 分类名称
     **/
    private String name;
    /**
     * 分类描述
     **/
    private String description;
    /**
     * 分类图标
     **/
    private String icon;
    /**
     * 排序权重
     **/
    private Integer sortOrder;
    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;
}

