package com.beiming.demo.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class Article {

    /**
     * 主键ID
     **/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章标题
     **/
    @TableField(value = "title")
    private String title;

    /**
     * 文章内容（Markdown格式）
     **/
    @TableField(value = "content")
    private String content;

    /**
     * 文章摘要
     **/
    @TableField(value = "summary")
    private String summary;

    /**
     * 封面图片URL
     **/
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 作者ID
     **/
    @TableField(value = "author_id")
    private Long authorId;

    /**
     * 分类ID
     **/
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 专栏ID
     **/
    @TableField(value = "series_id")
    private Long seriesId;

    /**
     * 在专栏中的排序
     **/
    @TableField(value = "series_order")
    private Integer seriesOrder;

    /**
     * 浏览次数
     **/
    @TableField(value = "view_count")
    private Long viewCount;

    /**
     * 点赞次数
     **/
    @TableField(value = "like_count")
    private Long likeCount;

    /**
     * 评论次数
     **/
    @TableField(value = "comment_count")
    private Long commentCount;

    /**
     * 状态：draft-草稿，published-已发布，archived-已归档
     **/
    @TableField(value = "status")
    private String status;

    /**
     * 是否置顶：0-否，1-是
     **/
    @TableField(value = "is_top")
    private Integer isTop;

    /**
     * 是否允许评论：0-否，1-是
     **/
    @TableField(value = "allow_comment")
    private Integer allowComment;

    /**
     * 发布时间
     **/
    @TableField(value = "published_at")
    private LocalDateTime publishedAt;

    /**
     * 创建时间
     **/
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     **/
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}

