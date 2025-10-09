package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.beiming.demo.model.dto.SysFileDTO;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.SysFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统文件表(SysFile)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@Mapper
public interface SysFileDao extends BaseMapper<SysFile> {

    default List<SysFile> list() {
        return new LambdaQueryChainWrapper<>(this)
                .list();

    }

    default SysFile getById(Long id) {
        return new LambdaQueryChainWrapper<>(this)
                .eq(SysFile::getId, id)
                .one();
    }
}

