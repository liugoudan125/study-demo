package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Data
public class ArticleRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 文章标题
     **/
    private String title;
    /**
     * 文章内容（Markdown格式）
     **/
    private String content;
    /**
     * 文章摘要
     **/
    private String summary;
    /**
     * 封面图片URL
     **/
    private String coverImage;
    /**
     * 作者ID
     **/
    private Long authorId;
    /**
     * 分类ID
     **/
    private Long categoryId;
    /**
     * 专栏ID
     **/
    private Long seriesId;
    /**
     * 在专栏中的排序
     **/
    private Integer seriesOrder;
    /**
     * 浏览次数
     **/
    private Long viewCount;
    /**
     * 点赞次数
     **/
    private Long likeCount;
    /**
     * 评论次数
     **/
    private Long commentCount;
    /**
     * 状态：draft-草稿，published-已发布，archived-已归档
     **/
    private String status;
    /**
     * 是否置顶：0-否，1-是
     **/
    private Integer isTop;
    /**
     * 是否允许评论：0-否，1-是
     **/
    private Integer allowComment;
    /**
     * 发布时间
     **/
    private LocalDateTime publishedAt;
}

