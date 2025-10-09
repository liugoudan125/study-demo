package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表(SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:20
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

}

