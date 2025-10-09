package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.ArticleViewLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章浏览记录表(ArticleViewLog)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:15
 */
@Mapper
public interface ArticleViewLogDao extends BaseMapper<ArticleViewLog> {

}

