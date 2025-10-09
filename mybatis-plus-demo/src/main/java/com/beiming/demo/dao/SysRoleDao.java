package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:18
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

}

