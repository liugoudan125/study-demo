package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.Album;
import org.apache.ibatis.annotations.Mapper;

/**
 * 相册表(Album)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:35:45
 */
@Mapper
public interface AlbumDao extends BaseMapper<Album> {

}

