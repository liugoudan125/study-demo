package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class ArticleTagRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 文章ID
     **/
    private Long articleId;
    /**
     * 标签ID
     **/
    private Long tagId;
}

