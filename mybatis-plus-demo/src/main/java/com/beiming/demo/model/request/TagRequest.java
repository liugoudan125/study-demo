package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章标签表(Tag)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:20
 */
@Data
public class TagRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 标签名称
     **/
    private String name;
    /**
     * 标签颜色
     **/
    private String color;
    /**
     * 标签描述
     **/
    private String description;
    /**
     * 使用该标签的文章数量
     **/
    private Integer articleCount;
    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;
}

