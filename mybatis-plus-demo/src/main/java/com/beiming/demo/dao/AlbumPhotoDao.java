package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.AlbumPhoto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 相册照片表(AlbumPhoto)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:14
 */
@Mapper
public interface AlbumPhotoDao extends BaseMapper<AlbumPhoto> {

}

