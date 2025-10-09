package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章专栏表(Series)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@Data
public class SeriesRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 专栏名称
     **/
    private String name;
    /**
     * 专栏描述
     **/
    private String description;
    /**
     * 封面图片URL
     **/
    private String coverImage;
    /**
     * 作者ID
     **/
    private Long authorId;
    /**
     * 文章数量
     **/
    private Integer articleCount;
    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;
}

